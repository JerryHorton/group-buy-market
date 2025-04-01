package cn.cug.sxy.types.design.framework.link.multitonModel.chain;

import cn.cug.sxy.types.design.framework.link.multitonModel.handler.ILogicHandler;

/**
 * @version 1.0
 * @Date 2025/4/1 20:04
 * @Description 业务链路
 * @Author jerryhotton
 */

public class BusinessLinkedList<T, D, R> extends LinkedList<ILogicHandler<T, D, R>> {

    public BusinessLinkedList(String name) {
        super(name);
    }

    public R apply(T requestParameter, D dynamicContext) throws Exception {
        Node<ILogicHandler<T, D, R>> currentNode = this.first;
        do {
            ILogicHandler<T, D, R> logicHandler = currentNode.item;
            R apply = logicHandler.apply(requestParameter, dynamicContext);
            if (null != apply) return apply;

            currentNode = currentNode.next;
        } while (null != currentNode);

        return defaultApply(requestParameter, dynamicContext);
    }

    private R defaultApply(T requestParameter, D dynamicContext) throws Exception {
        return null;
    }

}
