package cn.cug.sxy.types.design.framework.tree;

/**
 * @version 1.0
 * @Date 2025/3/27 15:45
 * @Description 策略处理器
 * @Author jerryhotton
 */

/*
 * @param <T> 入参类型
 * @param <D> 上下文参数
 * @param <R> 返参类型
 */
public interface StrategyHandler<T, D, R> {

    StrategyHandler DEFAULT = (T, D) -> null;

    R apply(T requestParameter, D dynamicContext) throws Exception;

}
