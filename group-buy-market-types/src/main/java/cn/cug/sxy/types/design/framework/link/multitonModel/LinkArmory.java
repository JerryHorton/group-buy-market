package cn.cug.sxy.types.design.framework.link.multitonModel;

import cn.cug.sxy.types.design.framework.link.multitonModel.chain.BusinessLinkedList;
import cn.cug.sxy.types.design.framework.link.multitonModel.handler.ILogicHandler;
import lombok.Getter;

/**
 * @version 1.0
 * @Date 2025/4/1 16:48
 * @Description 链路装配
 * @Author jerryhotton
 */

@Getter
public class LinkArmory<T, D, R> {

    private final BusinessLinkedList<T, D, R> logicLink;

    public LinkArmory(String linkName) {
        this.logicLink = new BusinessLinkedList<>(linkName);
    }

    public LinkArmory<T, D, R> addHandler(ILogicHandler<T, D, R> handler) {
        logicLink.add(handler);
        return this;
    }

}
