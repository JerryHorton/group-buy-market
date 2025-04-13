package cn.cug.sxy.domain.trade.service.settelement.factory;

import cn.cug.sxy.domain.trade.model.entity.GroupBuyOrderEntity;
import cn.cug.sxy.domain.trade.model.entity.MarketPayOrderEntity;
import cn.cug.sxy.domain.trade.model.entity.TradeSettlementRuleCommandEntity;
import cn.cug.sxy.domain.trade.model.entity.TradeSettlementRuleFilterBackEntity;
import cn.cug.sxy.domain.trade.service.settelement.filter.EndRuleFilter;
import cn.cug.sxy.domain.trade.service.settelement.filter.OutTradeNoRuleFilter;
import cn.cug.sxy.domain.trade.service.settelement.filter.SCRuleFilter;
import cn.cug.sxy.domain.trade.service.settelement.filter.SettableRuleFilter;
import cn.cug.sxy.types.design.framework.link.multitonModel.LinkArmory;
import cn.cug.sxy.types.design.framework.link.multitonModel.chain.BusinessLinkedList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/4/4 14:31
 * @Description 交易结算规则过滤器工厂
 * @Author Sxy
 */

@Service
public class TradeSettlementRuleFilterFactory {

    @Resource
    private SCRuleFilter scRuleFilter;

    @Resource
    private OutTradeNoRuleFilter outTradeNoRuleFilter;

    @Resource
    private SettableRuleFilter settableRuleFilter;

    @Resource
    private EndRuleFilter endRuleFilter;

    public BusinessLinkedList<TradeSettlementRuleCommandEntity, DynamicContext, TradeSettlementRuleFilterBackEntity> openTradeSettlementRuleFilter(String linkName) {
        return new LinkArmory<TradeSettlementRuleCommandEntity, DynamicContext, TradeSettlementRuleFilterBackEntity>(linkName)
                .addHandler(scRuleFilter)
                .addHandler(outTradeNoRuleFilter)
                .addHandler(settableRuleFilter)
                .addHandler(endRuleFilter)
                .getLogicLink();

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {

        private MarketPayOrderEntity marketPayOrderEntity;

        private GroupBuyOrderEntity groupBuyOrderEntity;

    }

}
