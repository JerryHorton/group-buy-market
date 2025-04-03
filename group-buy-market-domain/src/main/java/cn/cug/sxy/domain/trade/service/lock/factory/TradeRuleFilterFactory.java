package cn.cug.sxy.domain.trade.service.lock.factory;

import cn.cug.sxy.domain.trade.model.entity.GroupBuyActivityEntity;
import cn.cug.sxy.domain.trade.model.entity.TradeRuleCommandEntity;
import cn.cug.sxy.domain.trade.model.entity.TradeRuleFilterBackEntity;
import cn.cug.sxy.domain.trade.service.lock.filter.ActivityUsabilityFilter;
import cn.cug.sxy.domain.trade.service.lock.filter.UserTakeLimitRuleFilter;
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
 * @Date 2025/4/1 23:24
 * @Description 交易规则过滤器工厂
 * @Author jerryhotton
 */

@Service
public class TradeRuleFilterFactory {

    @Resource
    private ActivityUsabilityFilter activityUsabilityFilter;

    @Resource
    private UserTakeLimitRuleFilter userTakeLimitRuleFilter;

    public BusinessLinkedList<TradeRuleCommandEntity, DynamicContext, TradeRuleFilterBackEntity> openTradeRuleFilter(String linkName) {
        return new LinkArmory<TradeRuleCommandEntity, DynamicContext, TradeRuleFilterBackEntity>(linkName)
                .addHandler(activityUsabilityFilter)
                .addHandler(userTakeLimitRuleFilter)
                .getLogicLink();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {

        private GroupBuyActivityEntity groupBuyActivityEntity;

    }

}
