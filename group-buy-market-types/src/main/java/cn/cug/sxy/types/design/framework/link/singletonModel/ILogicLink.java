package cn.cug.sxy.types.design.framework.link.singletonModel;

/**
 * @version 1.0
 * @Date 2025/4/1 15:46
 * @Description 责任链接口
 * @Author jerryhotton
 */

public interface ILogicLink<T, D, R> extends ILogicLinkArmory<T, D, R> {

    R apply(T requestParameter, D dynamicContext);

}
