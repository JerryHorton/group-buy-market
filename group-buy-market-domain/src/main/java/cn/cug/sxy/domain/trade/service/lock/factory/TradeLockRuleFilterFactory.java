package cn.cug.sxy.domain.trade.service.lock.factory;

import cn.cug.sxy.domain.trade.model.entity.GroupBuyActivityEntity;
import cn.cug.sxy.domain.trade.model.entity.TradeLockRuleCommandEntity;
import cn.cug.sxy.domain.trade.model.entity.TradeLockRuleFilterBackEntity;
import cn.cug.sxy.domain.trade.service.lock.filter.ActivityUsabilityFilter;
import cn.cug.sxy.domain.trade.service.lock.filter.TeamStockOccupyRuleFilter;
import cn.cug.sxy.domain.trade.service.lock.filter.UserTakeLimitRuleFilter;
import cn.cug.sxy.types.common.Constants;
import cn.cug.sxy.types.design.framework.link.multitonModel.LinkArmory;
import cn.cug.sxy.types.design.framework.link.multitonModel.chain.BusinessLinkedList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/4/1 23:24
 * @Description 交易规则过滤器工厂
 * @Author jerryhotton
 */

@Service
public class TradeLockRuleFilterFactory {

    @Resource
    private ActivityUsabilityFilter activityUsabilityFilter;

    @Resource
    private UserTakeLimitRuleFilter userTakeLimitRuleFilter;

    @Resource
    private TeamStockOccupyRuleFilter teamStockOccupyRuleFilter;

    public BusinessLinkedList<TradeLockRuleCommandEntity, DynamicContext, TradeLockRuleFilterBackEntity> openTradeLockRuleFilter(String linkName) {
        return new LinkArmory<TradeLockRuleCommandEntity, DynamicContext, TradeLockRuleFilterBackEntity>(linkName)
                .addHandler(activityUsabilityFilter)
                .addHandler(userTakeLimitRuleFilter)
                .addHandler(teamStockOccupyRuleFilter)
                .getLogicLink();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {

        private GroupBuyActivityEntity groupBuyActivityEntity;

        private Integer participationCount;

        public String teamStockKey(String teamId) {
            if (StringUtils.isBlank(teamId)) return null;
            return "teamStockKey" + Constants.UNDERLINE + teamId;
        }

        public String recoveryTeamStockKey(String teamId) {
            if (StringUtils.isBlank(teamId)) return null;
            return "recoveryTeamStockKey" + Constants.UNDERLINE + teamId;
        }

    }

}
