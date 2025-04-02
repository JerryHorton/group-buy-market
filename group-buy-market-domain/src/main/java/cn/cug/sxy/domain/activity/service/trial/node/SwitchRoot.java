package cn.cug.sxy.domain.activity.service.trial.node;

import cn.cug.sxy.domain.activity.model.entity.MarketProductEntity;
import cn.cug.sxy.domain.activity.model.entity.TrialBalanceEntity;
import cn.cug.sxy.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import cn.cug.sxy.domain.activity.service.trial.factory.ActivityStrategyFactory;
import cn.cug.sxy.types.design.framework.tree.StrategyHandler;
import cn.cug.sxy.types.enums.ResponseCode;
import cn.cug.sxy.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/3/27 16:42
 * @Description 开关节点
 * @Author jerryhotton
 */

@Slf4j
@Service
public class SwitchRoot extends AbstractGroupBuyMarketSupport<MarketProductEntity, ActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private TagNode tagNode;

    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParameter, ActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        String userId = requestParameter.getUserId();
        if (activityRepository.degradeSwitch()) {
            throw new AppException(ResponseCode.E0003.getCode(), ResponseCode.E0003.getInfo());
        }
        if (!activityRepository.cutRange(userId)) {
            throw new AppException(ResponseCode.E0004.getCode(), ResponseCode.E0004.getInfo());
        }
        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, ActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, ActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return tagNode;
    }

}
