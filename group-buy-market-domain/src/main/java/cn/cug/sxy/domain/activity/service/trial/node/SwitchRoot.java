package cn.cug.sxy.domain.activity.service.trial.node;

import cn.cug.sxy.domain.activity.model.entity.MarketProductEntity;
import cn.cug.sxy.domain.activity.model.entity.TrialBalanceEntity;
import cn.cug.sxy.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import cn.cug.sxy.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import cn.cug.sxy.types.design.framework.tree.StrategyHandler;
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
public class SwitchRoot extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private TagNode tagNode;

    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return tagNode;
    }

}
