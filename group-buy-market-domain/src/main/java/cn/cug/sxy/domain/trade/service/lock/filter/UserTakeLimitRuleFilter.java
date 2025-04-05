package cn.cug.sxy.domain.trade.service.lock.filter;

import cn.cug.sxy.domain.trade.model.entity.GroupBuyActivityEntity;
import cn.cug.sxy.domain.trade.model.entity.TradeLockRuleCommandEntity;
import cn.cug.sxy.domain.trade.model.entity.TradeLockRuleFilterBackEntity;
import cn.cug.sxy.domain.trade.adaptor.repository.ITradeRepository;
import cn.cug.sxy.domain.trade.service.lock.factory.TradeLockRuleFilterFactory;
import cn.cug.sxy.types.design.framework.link.multitonModel.handler.ILogicHandler;
import cn.cug.sxy.types.enums.ResponseCode;
import cn.cug.sxy.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/4/1 23:18
 * @Description 拼团参与次数限制 过滤器
 * @Author jerryhotton
 */

@Slf4j
@Service
public class UserTakeLimitRuleFilter implements ILogicHandler<TradeLockRuleCommandEntity, TradeLockRuleFilterFactory.DynamicContext, TradeLockRuleFilterBackEntity> {

    @Resource
    private ITradeRepository repository;

    @Override
    public TradeLockRuleFilterBackEntity apply(TradeLockRuleCommandEntity requestParameter, TradeLockRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("交易规则过滤-用户参与次数校验{} activityId:{}", requestParameter.getUserId(), requestParameter.getActivityId());
        GroupBuyActivityEntity groupBuyActivityEntity = dynamicContext.getGroupBuyActivityEntity();

        Integer participationCount = repository.queryActivityParticipationCount(requestParameter.getUserId(), requestParameter.getActivityId());
        if (null != participationCount && participationCount >= groupBuyActivityEntity.getTakeLimitCount()) {
            log.info("用户参与次数校验，已达可参与上限 activityId:{}", requestParameter.getActivityId());
            throw new AppException(ResponseCode.E0103);
        }

        return TradeLockRuleFilterBackEntity.builder()
                .userParticipationCount(participationCount)
                .build();
    }

}
