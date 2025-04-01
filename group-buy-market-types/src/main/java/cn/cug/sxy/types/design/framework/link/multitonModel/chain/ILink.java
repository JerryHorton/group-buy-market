package cn.cug.sxy.types.design.framework.link.multitonModel.chain;

/**
 * @version 1.0
 * @Date 2025/4/1 16:50
 * @Description 链路 接口
 * @Author jerryhotton
 */

public interface ILink<E> {

    boolean add(E e);

    boolean addFirst(E e);

    boolean addLast(E e);

    boolean remove(Object o);

    E get(int index);

    void printLinkList();

}
