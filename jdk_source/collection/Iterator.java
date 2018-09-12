package jdk_source.collection;

/*集合内部的迭代器可以访问集合内部的数据*/

/*三个方法
 * 1.判断是否还有数据
 * 2.取一个数据
 * 3.移除一个数据*/
public interface Iterator <E>{
    boolean hasNext();
    E       next();
    default void remove() {throw new UnsupportedOperationException("remove");}
}
