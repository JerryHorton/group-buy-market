package cn.cug.sxy.domain.trade.service.lock.filter;

import cn.cug.sxy.domain.trade.adaptor.repository.ITradeRepository;
import cn.cug.sxy.domain.trade.model.entity.GroupBuyActivityEntity;
import cn.cug.sxy.domain.trade.model.entity.TradeLockRuleCommandEntity;
import cn.cug.sxy.domain.trade.model.entity.TradeLockRuleFilterBackEntity;
import cn.cug.sxy.domain.trade.service.lock.factory.TradeLockRuleFilterFactory;
import cn.cug.sxy.types.design.framework.link.multitonModel.handler.ILogicHandler;
import cn.cug.sxy.types.enums.ResponseCode;
import cn.cug.sxy.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/4/16 20:16
 * @Description 组队库存占用规则过滤
 * @Author jerryhotton
 */

@Slf4j
@Component
public class TeamStockOccupyRuleFilter implements ILogicHandler<TradeLockRuleCommandEntity, TradeLockRuleFilterFactory.DynamicContext, TradeLockRuleFilterBackEntity> {

    @Resource
    private ITradeRepository repository;

    @Override
    public TradeLockRuleFilterBackEntity apply(TradeLockRuleCommandEntity requestParameter, TradeLockRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("交易规则过滤-组队库存校验 userId{} activityId:{}", requestParameter.getUserId(), requestParameter.getActivityId());
        // 1. teamId 为空，则为首次开团，不做拼团组队目标量库存限制
        String teamId = requestParameter.getTeamId();
        if (StringUtils.isBlank(teamId)) {
            return TradeLockRuleFilterBackEntity.builder()
                    .userParticipationCount(dynamicContext.getParticipationCount())
                    .build();
        }
        // 2. 抢占库存；通过抢占 Redis 缓存库存，来降低对数据库的操作压力。
        GroupBuyActivityEntity groupBuyActivityEntity = dynamicContext.getGroupBuyActivityEntity();
        Integer targetCount = groupBuyActivityEntity.getTargetCount();
        Integer validTime = groupBuyActivityEntity.getValidTime();
        String teamStockKey = dynamicContext.teamStockKey(teamId);
        String recoveryTeamStockKey = dynamicContext.recoveryTeamStockKey(teamId);
        boolean status = repository.occupyTeamStock(teamStockKey, recoveryTeamStockKey, targetCount, validTime);
        if (!status) {
            log.warn("交易规则过滤-组队库存校验 userId:{} activityId:{} 抢占失败:{}", requestParameter.getUserId(), requestParameter.getActivityId(), teamStockKey);
            throw new AppException(ResponseCode.E0007);
        }
        return TradeLockRuleFilterBackEntity.builder()
                .userParticipationCount(dynamicContext.getParticipationCount())
                .recoveryTeamStockKey(recoveryTeamStockKey)
                .build();
    }

}
