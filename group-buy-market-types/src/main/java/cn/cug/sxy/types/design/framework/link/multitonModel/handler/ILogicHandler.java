package cn.cug.sxy.types.design.framework.link.multitonModel.handler;

/**
 * @version 1.0
 * @Date 2025/4/1 19:54
 * @Description 逻辑处理器
 * @Author jerryhotton
 */

public interface ILogicHandler<T, D, R> {

    default R next(T requestParameter, D dynamicContext) {
        return null;
    }

    R apply(T requestParameter, D dynamicContext) throws Exception;

}
