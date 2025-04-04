package cn.cug.sxy.domain.trade.service.lock.filter;

import cn.cug.sxy.domain.trade.model.entity.GroupBuyActivityEntity;
import cn.cug.sxy.domain.trade.model.entity.TradeLockRuleCommandEntity;
import cn.cug.sxy.domain.trade.model.entity.TradeLockRuleFilterBackEntity;
import cn.cug.sxy.domain.trade.model.valobj.GroupBuyActivityStatusVO;
import cn.cug.sxy.domain.trade.repository.ITradeRepository;
import cn.cug.sxy.domain.trade.service.lock.factory.TradeLockRuleFilterFactory;
import cn.cug.sxy.types.design.framework.link.multitonModel.handler.ILogicHandler;
import cn.cug.sxy.types.enums.ResponseCode;
import cn.cug.sxy.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @version 1.0
 * @Date 2025/4/1 23:28
 * @Description 活动可使用性规则过滤【状态、有效期】
 * @Author jerryhotton
 */

@Slf4j
@Service
public class ActivityUsabilityFilter implements ILogicHandler<TradeLockRuleCommandEntity, TradeLockRuleFilterFactory.DynamicContext, TradeLockRuleFilterBackEntity> {

    @Resource
    private ITradeRepository repository;

    @Override
    public TradeLockRuleFilterBackEntity apply(TradeLockRuleCommandEntity requestParameter, TradeLockRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("交易规则过滤-活动的可用性校验{} activityId:{}", requestParameter.getUserId(), requestParameter.getActivityId());

        GroupBuyActivityEntity groupBuyActivityEntity = repository.queryGroupBuyActivityEntity(requestParameter.getActivityId());

        if (!GroupBuyActivityStatusVO.ACTIVE.equals(groupBuyActivityEntity.getStatus())) {
            log.info("活动的可用性校验，非生效状态 activityId:{}", requestParameter.getActivityId());
            throw new AppException(ResponseCode.E0101);
        }

        Date currentDate = new Date();
        if ( currentDate.before(groupBuyActivityEntity.getStartTime()) || currentDate.after(groupBuyActivityEntity.getEndTime())) {
            log.info("活动的可用性校验，非可参与时间范围 activityId:{}", requestParameter.getActivityId());
            throw new AppException(ResponseCode.E0102);
        }

        dynamicContext.setGroupBuyActivityEntity(groupBuyActivityEntity);

        return next(requestParameter, dynamicContext);
    }

}
