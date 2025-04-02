package cn.cug.sxy.domain.activity.service.trial.impl;

import cn.cug.sxy.domain.activity.model.entity.MarketProductEntity;
import cn.cug.sxy.domain.activity.model.entity.TrialBalanceEntity;
import cn.cug.sxy.domain.activity.service.trial.IIndexGroupBuyMarketService;
import cn.cug.sxy.domain.activity.service.trial.factory.ActivityStrategyFactory;
import cn.cug.sxy.types.design.framework.tree.StrategyHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/3/27 17:01
 * @Description 首页营销服务 实现
 * @Author jerryhotton
 */

@Service
public class IndexGroupBuyMarketService implements IIndexGroupBuyMarketService {

    @Resource
    private ActivityStrategyFactory activityStrategyFactory;

    @Override
    public TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception {
        StrategyHandler<MarketProductEntity, ActivityStrategyFactory.DynamicContext, TrialBalanceEntity> strategyHandler = activityStrategyFactory.strategyHandler();
        return strategyHandler.apply(marketProductEntity, new ActivityStrategyFactory.DynamicContext());
    }

}
