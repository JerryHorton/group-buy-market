package cn.cug.sxy.types.design.framework.tree;

/**
 * @version 1.0
 * @Date 2025/3/28 16:09
 * @Description 异步资源加载策略路由抽象类
 * @Author jerryhotton
 */

public abstract class AbstractMultiThreadStrategyRouter<T, D, R> extends AbstractStrategyRouter<T, D, R> {

    @Override
    public R apply(T requestParameter, D dynamicContext) throws Exception {
        multiThread(requestParameter, dynamicContext);
        return doApply(requestParameter, dynamicContext);
    }

    protected abstract void multiThread(T requestParameter, D dynamicContext) throws Exception;

    protected abstract R doApply(T requestParameter, D dynamicContext) throws Exception;

}
