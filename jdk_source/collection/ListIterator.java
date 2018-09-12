package jdk_source.collection;

//增加向前访问集合数据的功能
public interface ListIterator<E> extends Iterator<E>{
    boolean hasPrevious();
    E       previous();
    void    set(E e);
    void    add(E e);
    int     nextIndex();
    int     previousIndex();
}
