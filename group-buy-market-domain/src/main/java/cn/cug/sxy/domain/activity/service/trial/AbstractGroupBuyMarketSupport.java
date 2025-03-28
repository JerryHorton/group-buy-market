package cn.cug.sxy.domain.activity.service.trial;

import cn.cug.sxy.domain.activity.repository.IActivityRepository;
import cn.cug.sxy.types.design.framework.tree.AbstractMultiThreadStrategyRouter;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/3/27 16:15
 * @Description 拼团营销抽象支持类
 * @Author jerryhotton
 */

public abstract class AbstractGroupBuyMarketSupport<T, D, R> extends AbstractMultiThreadStrategyRouter<T, D, R> {

    protected Long timeout = 500L;

    @Resource
    protected IActivityRepository activityRepository;

    @Override
    protected void multiThread(T requestParameter, D dynamicContext) throws Exception {
        // 缺省方法
    }
}
