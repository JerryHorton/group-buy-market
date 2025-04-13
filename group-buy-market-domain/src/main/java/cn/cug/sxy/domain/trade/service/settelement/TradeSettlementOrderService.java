package cn.cug.sxy.domain.trade.service.settelement;

import cn.cug.sxy.domain.trade.adaptor.port.ISmallPayMallPort;
import cn.cug.sxy.domain.trade.model.aggregate.TradePaySettlementAggregate;
import cn.cug.sxy.domain.trade.model.entity.*;
import cn.cug.sxy.domain.trade.adaptor.repository.ITradeRepository;
import cn.cug.sxy.domain.trade.service.ITradeSettlementOrderService;
import cn.cug.sxy.domain.trade.service.settelement.factory.TradeSettlementRuleFilterFactory;
import cn.cug.sxy.types.design.framework.link.multitonModel.chain.BusinessLinkedList;
import cn.cug.sxy.types.enums.NotifyTaskStatus;
import cn.cug.sxy.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @version 1.0
 * @Date 2025/4/3 11:43
 * @Description 拼团交易结算 服务实现
 * @Author Sxy
 */

@Slf4j
@Service
public class TradeSettlementOrderService implements ITradeSettlementOrderService {

    @Resource
    private ITradeRepository repository;

    @Resource
    private ISmallPayMallPort port;

    @Resource
    private TradeSettlementRuleFilterFactory tradeSettlementRuleFilterFactory;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public TradePaySettlementEntity settlementMarketPayOrder(TradePaySuccessEntity tradePaySuccessEntity) throws Exception {
        log.info("拼团交易-结算营销拼团订单 userId:{}, outTradeNo:{}", tradePaySuccessEntity.getUserId(), tradePaySuccessEntity.getOutTradeNo());

        // 1. 交易结算责任链过滤
        BusinessLinkedList<TradeSettlementRuleCommandEntity, TradeSettlementRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity> tradeRuleFilter = tradeSettlementRuleFilterFactory.openTradeSettlementRuleFilter("交易结算责任链");
        TradeSettlementRuleFilterBackEntity tradeSettlementRuleFilterBackEntity = tradeRuleFilter.apply(TradeSettlementRuleCommandEntity.builder()
                        .userId(tradePaySuccessEntity.getUserId())
                        .source(tradePaySuccessEntity.getSource())
                        .channel(tradePaySuccessEntity.getChannel())
                        .outTradeNo(tradePaySuccessEntity.getOutTradeNo())
                        .outTradeTime(tradePaySuccessEntity.getOutTradeTime())
                        .build(),
                new TradeSettlementRuleFilterFactory.DynamicContext());

        MarketPayOrderEntity marketPayOrderEntity = repository.queryUnpaidMarketPayOrderByOutTradeNo(tradePaySuccessEntity.getOutTradeNo());
        if (null == marketPayOrderEntity) {
            log.error("拼团交易-结算营销拼团订单失败, 未查询到拼团订单信息, userId:{} outTradeNo:{}", tradePaySuccessEntity.getUserId(), tradePaySuccessEntity.getOutTradeNo());
            return null;
        }

        // 2. 查询拼团信息
        GroupBuyOrderEntity groupBuyOrderEntity = GroupBuyOrderEntity.builder()
                .teamId(tradeSettlementRuleFilterBackEntity.getTeamId())
                .activityId(tradeSettlementRuleFilterBackEntity.getActivityId())
                .targetCount(tradeSettlementRuleFilterBackEntity.getTargetCount())
                .completeCount(tradeSettlementRuleFilterBackEntity.getCompleteCount())
                .lockCount(tradeSettlementRuleFilterBackEntity.getLockCount())
                .status(tradeSettlementRuleFilterBackEntity.getStatus())
                .validStartTime(tradeSettlementRuleFilterBackEntity.getValidStartTime())
                .validEndTime(tradeSettlementRuleFilterBackEntity.getValidEndTime())
                .notifyType(tradeSettlementRuleFilterBackEntity.getNotifyType())
                .notifyTarget(tradeSettlementRuleFilterBackEntity.getNotifyTarget())
                .build();

        // 3. 构建聚合对象
        TradePaySettlementAggregate tradePaySettlementAggregate = TradePaySettlementAggregate.builder()
                .userEntity(UserEntity.builder().userId(tradePaySuccessEntity.getUserId()).build())
                .tradePaySuccessEntity(tradePaySuccessEntity)
                .groupBuyOrderEntity(groupBuyOrderEntity)
                .build();

        // 4. 结算拼团订单
        NotifyTaskEntity notifyTaskEntity = repository.settlementMarketPayOrder(tradePaySettlementAggregate);

        // 5. 拼团回调处理 - 处理失败也会有定时任务补偿，通过这样的方式，可以减轻任务调度，提高时效性
        threadPoolExecutor.execute(() -> {
            Map<String, Integer> notifyResultMap = null;
            try {
                notifyResultMap = execSettlementNotifyJob(notifyTaskEntity);
                log.info("回调通知拼团完结成功 result:{}", JSON.toJSONString(notifyResultMap));
            } catch (Exception e) {
                log.error("回调通知拼团完结失败 result:{}", JSON.toJSONString(notifyResultMap));
                throw new AppException(e.getMessage(), e);
            }
        });

        // 6. 返回结算信息
        return TradePaySettlementEntity.builder()
                .source(tradePaySuccessEntity.getSource())
                .channel(tradePaySuccessEntity.getChannel())
                .userId(tradePaySuccessEntity.getUserId())
                .teamId(marketPayOrderEntity.getTeamId())
                .activityId(groupBuyOrderEntity.getActivityId())
                .outTradeNo(tradePaySuccessEntity.getOutTradeNo())
                .build();
    }

    @Override
    public Map<String, Integer> execSettlementNotifyJob() throws Exception {
        List<NotifyTaskEntity> notifyTaskEntityList = repository.queryUnExecuteSuccessNotifyTaskList();
        return execSettlementNotifyJob(notifyTaskEntityList);
    }

    @Override
    public Map<String, Integer> execSettlementNotifyJob(String teamId) throws Exception {
        List<NotifyTaskEntity> notifyTaskEntityList = repository.queryUnExecuteSuccessNotifyTaskList(teamId);
        return execSettlementNotifyJob(notifyTaskEntityList);
    }

    private Map<String, Integer> execSettlementNotifyJob(NotifyTaskEntity notifyTaskEntity) throws Exception {
        List<NotifyTaskEntity> notifyTaskEntityList = null != notifyTaskEntity ? Collections.singletonList(notifyTaskEntity) : Collections.emptyList();
        return execSettlementNotifyJob(notifyTaskEntityList);
    }

    private Map<String, Integer> execSettlementNotifyJob(List<NotifyTaskEntity> notifyTaskEntityList) throws Exception {
        int successCount = 0, errorCount = 0, retryCount = 0;
        for (NotifyTaskEntity notifyTaskEntity : notifyTaskEntityList) {
            String response = port.groupBuyNotify(notifyTaskEntity);
            if (NotifyTaskStatus.SUCCESS.getCode().equals(response)) {
                int updateCount = repository.updateNotifyTaskStatusSuccess(notifyTaskEntity.getTeamId());
                if (1 == updateCount) {
                    successCount++;
                }
            } else if (NotifyTaskStatus.ERROR.getCode().equals(response)) {
                if (notifyTaskEntity.getNotifyCount() < 5) {
                    int updateCount = repository.updateNotifyTaskStatusRetry(notifyTaskEntity.getTeamId());
                    if (1 == updateCount) {
                        retryCount++;
                    }
                } else {
                    int updateCount = repository.updateNotifyTaskStatusError(notifyTaskEntity.getTeamId());
                    if (1 == updateCount) {
                        errorCount++;
                    }
                }
            }
        }
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("waitCount", notifyTaskEntityList.size() - successCount);
        resultMap.put("successCount", successCount);
        resultMap.put("errorCount", errorCount);
        resultMap.put("retryCount", retryCount);

        return resultMap;
    }

}
