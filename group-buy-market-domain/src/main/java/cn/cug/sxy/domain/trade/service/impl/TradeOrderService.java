package cn.cug.sxy.domain.trade.service.impl;

import cn.cug.sxy.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import cn.cug.sxy.domain.trade.model.entity.*;
import cn.cug.sxy.domain.trade.model.valobj.GroupBuyProgressVO;
import cn.cug.sxy.domain.trade.repository.ITradeRepository;
import cn.cug.sxy.domain.trade.service.ITradeOrderService;
import cn.cug.sxy.domain.trade.service.factory.TradeRuleFilterFactory;
import cn.cug.sxy.types.design.framework.link.multitonModel.chain.BusinessLinkedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/3/31 20:18
 * @Description 拼团订单 服务实现
 * @Author jerryhotton
 */

@Slf4j
@Service
public class TradeOrderService implements ITradeOrderService {

    @Resource
    private ITradeRepository tradeRepository;

    @Resource
    private TradeRuleFilterFactory tradeRuleFilterFactory;

    @Override
    public MarketPayOrderEntity queryUnpaidMarketPayOrderByOutTradeNo(String userId, String outTradeNo) {
        log.info("拼团交易-查询未支付营销订单:{} outTradeNo:{}", userId, outTradeNo);
        return tradeRepository.queryUnpaidMarketPayOrderByOutTradeNo(userId, outTradeNo);
    }

    @Override
    public GroupBuyProgressVO queryGroupBuyProgress(String teamId) {
        log.info("拼团交易-查询拼单进度:{}", teamId);
        return tradeRepository.queryGroupBuyProgress(teamId);
    }

    @Override
    public MarketPayOrderEntity lockMarketPayOrder(UserEntity userEntity, PayActivityEntity payActivityEntity, PayDiscountEntity payDiscountEntity) throws Exception {
        log.info("拼团交易-锁定营销优惠支付订单:{} activityId:{} goodsId:{}", userEntity.getUserId(), payActivityEntity.getActivityId(), payDiscountEntity.getGoodsId());

        // 交易规则过滤
        BusinessLinkedList<TradeRuleCommandEntity, TradeRuleFilterFactory.DynamicContext, TradeRuleFilterBackEntity> tradeRuleFilter = tradeRuleFilterFactory.openTradeRuleFilter("拼团交易锁单- 交易规则过滤");
        TradeRuleFilterBackEntity tradeRuleFilterBackEntity = tradeRuleFilter.apply(TradeRuleCommandEntity.builder()
                .userId(userEntity.getUserId())
                .activityId(payActivityEntity.getActivityId())
                .build(), new TradeRuleFilterFactory.DynamicContext());

        // 已参与拼团量 - 用于构建数据库唯一索引使用，确保用户只能在一个活动上参与固定的次数
        Integer userParticipationCount = tradeRuleFilterBackEntity.getUserParticipationCount();

        // 构建聚合对象
        GroupBuyOrderAggregate groupBuyOrderAggregate = GroupBuyOrderAggregate.builder()
                .userEntity(userEntity)
                .payActivityEntity(payActivityEntity)
                .payDiscountEntity(payDiscountEntity)
                .userParticipationCount(userParticipationCount)
                .build();

        return tradeRepository.lockMarketPayOrder(groupBuyOrderAggregate);
    }

}
