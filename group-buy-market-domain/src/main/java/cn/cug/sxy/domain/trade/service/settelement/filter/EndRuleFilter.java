package cn.cug.sxy.domain.trade.service.settelement.filter;

import cn.cug.sxy.domain.trade.model.entity.GroupBuyOrderEntity;
import cn.cug.sxy.domain.trade.model.entity.TradeSettlementRuleCommandEntity;
import cn.cug.sxy.domain.trade.model.entity.TradeSettlementRuleFilterBackEntity;
import cn.cug.sxy.domain.trade.service.settelement.factory.TradeSettlementRuleFilterFactory;
import cn.cug.sxy.types.design.framework.link.multitonModel.handler.ILogicHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @Date 2025/4/4 14:34
 * @Description 结束节点放行
 * @Author Sxy
 */

@Slf4j
@Service
public class EndRuleFilter implements ILogicHandler<TradeSettlementRuleCommandEntity, TradeSettlementRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity> {

    @Override
    public TradeSettlementRuleFilterBackEntity apply(TradeSettlementRuleCommandEntity requestParameter, TradeSettlementRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("结算规则过滤-结束放行, userId:{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());

        GroupBuyOrderEntity groupBuyOrderEntity = dynamicContext.getGroupBuyOrderEntity();

        return TradeSettlementRuleFilterBackEntity.builder()
                .teamId(groupBuyOrderEntity.getTeamId())
                .activityId(groupBuyOrderEntity.getActivityId())
                .targetCount(groupBuyOrderEntity.getTargetCount())
                .completeCount(groupBuyOrderEntity.getCompleteCount())
                .lockCount(groupBuyOrderEntity.getLockCount())
                .status(groupBuyOrderEntity.getStatus())
                .validStartTime(groupBuyOrderEntity.getValidStartTime())
                .validEndTime(groupBuyOrderEntity.getValidEndTime())
                .notifyType(groupBuyOrderEntity.getNotifyType())
                .notifyTarget(groupBuyOrderEntity.getNotifyTarget())
                .build();
    }

}
