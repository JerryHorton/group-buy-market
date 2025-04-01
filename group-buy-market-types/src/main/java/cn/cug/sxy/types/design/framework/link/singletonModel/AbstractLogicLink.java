package cn.cug.sxy.types.design.framework.link.singletonModel;

/**
 * @version 1.0
 * @Date 2025/4/1 15:50
 * @Description 责任链抽象类
 * @Author jerryhotton
 */

public abstract class AbstractLogicLink<T, D, R> implements ILogicLink<T, D, R> {

    private ILogicLink<T, D, R> next;

    @Override
    public ILogicLink<T, D, R> next() {
        return next;
    }

    @Override
    public ILogicLink<T, D, R> appendNext(ILogicLink<T, D, R> next) {
        this.next = next;
        return next;
    }

    protected R next(T requestParameter, D dynamicContext) {
        return next.apply(requestParameter, dynamicContext);
    }

}
