package cn.cug.sxy.types.design.framework.link.singletonModel;

/**
 * @version 1.0
 * @Date 2025/4/1 15:48
 * @Description 责任链装配 接口
 * @Author jerryhotton
 */

public interface ILogicLinkArmory<T, D, R> {

    ILogicLink<T, D, R> next();

    ILogicLink<T, D, R> appendNext(ILogicLink<T, D, R> next);

}
