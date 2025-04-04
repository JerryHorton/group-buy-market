package cn.cug.sxy.domain.trade.service.settelement;

import cn.cug.sxy.domain.trade.model.aggregate.TradePaySettlementAggregate;
import cn.cug.sxy.domain.trade.model.entity.*;
import cn.cug.sxy.domain.trade.repository.ITradeRepository;
import cn.cug.sxy.domain.trade.service.ITradeSettlementOrderService;
import cn.cug.sxy.domain.trade.service.settelement.factory.TradeSettlementRuleFilterFactory;
import cn.cug.sxy.types.design.framework.link.multitonModel.chain.BusinessLinkedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    private TradeSettlementRuleFilterFactory tradeSettlementRuleFilterFactory;

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
        GroupBuyTeamEntity groupBuyTeamEntity = GroupBuyTeamEntity.builder()
                .teamId(tradeSettlementRuleFilterBackEntity.getTeamId())
                .activityId(tradeSettlementRuleFilterBackEntity.getActivityId())
                .targetCount(tradeSettlementRuleFilterBackEntity.getTargetCount())
                .completeCount(tradeSettlementRuleFilterBackEntity.getCompleteCount())
                .lockCount(tradeSettlementRuleFilterBackEntity.getLockCount())
                .status(tradeSettlementRuleFilterBackEntity.getStatus())
                .validStartTime(tradeSettlementRuleFilterBackEntity.getValidStartTime())
                .validEndTime(tradeSettlementRuleFilterBackEntity.getValidEndTime())
                .build();

        // 3. 构建聚合对象
        TradePaySettlementAggregate tradePaySettlementAggregate = TradePaySettlementAggregate.builder()
                .userEntity(UserEntity.builder().userId(tradePaySuccessEntity.getUserId()).build())
                .tradePaySuccessEntity(tradePaySuccessEntity)
                .groupBuyTeamEntity(groupBuyTeamEntity)
                .build();

        // 4. 结算拼团订单
        repository.settlementMarketPayOrder(tradePaySettlementAggregate);

        // 5. 返回结算信息
        return TradePaySettlementEntity.builder()
                .source(tradePaySuccessEntity.getSource())
                .channel(tradePaySuccessEntity.getChannel())
                .userId(tradePaySuccessEntity.getUserId())
                .teamId(marketPayOrderEntity.getTeamId())
                .activityId(groupBuyTeamEntity.getActivityId())
                .outTradeNo(tradePaySuccessEntity.getOutTradeNo())
                .build();
    }

}
