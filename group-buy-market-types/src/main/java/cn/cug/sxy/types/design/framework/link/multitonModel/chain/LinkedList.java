package cn.cug.sxy.types.design.framework.link.multitonModel.chain;

/**
 * @version 1.0
 * @Date 2025/4/1 16:54
 * @Description 功能链路
 * @Author jerryhotton
 */

public class LinkedList<E> implements ILink<E> {

    /**
     * 责任链名称
     */
    private final String name;

    transient int size = 0;

    transient Node<E> first;

    transient Node<E> last;

    public LinkedList(String name) {
        this.name = name;
    }

    @Override
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    @Override
    public boolean addFirst(E e) {
        linkFirst(e);
        return true;
    }

    @Override
    public boolean addLast(E e) {
        linkLast(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public E get(int index) {
        return node(index).item;
    }

    @Override
    public void printLinkList() {
        if (this.size == 0) {
            System.out.println("链表为空");
        } else {
            Node<E> temp = first;
            StringBuilder sb = new StringBuilder("目前的列表，头节点：")
                    .append(first.item)
                    .append(" 尾节点：")
                    .append(last.item)
                    .append(" 整体：");
            while (temp != null) {
                sb.append(temp.item).append("，");
                temp = temp.next;
            }
            sb.deleteCharAt(sb.length() - 1);  // 去掉最后的逗号
            System.out.println(sb);
        }
    }

    void linkFirst(E e) {
        final Node<E> oldFirst = first;
        Node<E> newNode = new Node<>(null, e, oldFirst);
        first = newNode;
        if (null == oldFirst) {
            last = newNode;
        } else {
            oldFirst.prev = newNode;
        }
        size++;
    }

    void linkLast(E e) {
        final Node<E> oldLast = last;
        Node<E> newNode = new Node<>(oldLast, e, null);
        last = newNode;
        if (null == oldLast) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }
        size++;
    }

    E unlink(Node<E> currNode) {
        final E element = currNode.item;
        final Node<E> preNode = currNode.prev;
        final Node<E> nextNode = currNode.next;
        if (currNode == first) {
            first = nextNode;
        } else {
            preNode.next = nextNode;
        }
        if (currNode == last) {
            last = preNode;
        } else {
            nextNode.prev = preNode;
        }
        currNode.prev = null;
        currNode.next = null;
        currNode.item = null;
        size--;
        return element;
    }

    Node<E> node(int index) {
        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }


    protected static class Node<E> {

        E item;
        Node<E> next;
        Node<E> prev;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

    }

}
