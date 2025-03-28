package cn.cug.sxy.types.design.framework.tree;

/**
 * @version 1.0
 * @Date 2025/3/27 15:44
 * @Description 策略映射器
 * @Author jerryhotton
 */

public interface StrategyMapper<T, D, R> {

    StrategyHandler<T, D, R> get(T requestParameter, D dynamicContext);

}
