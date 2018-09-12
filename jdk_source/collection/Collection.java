package jdk_source.collection;

//集合类的接口
//如果继承了iterable接口则集合可以通过iterator进行 for-each迭代

public interface Collection<E> {
	//返回集合的大小
    int size();
    //判断集合是否为空
    boolean isEmpty();
    //判断集合是否包含某个元素
    boolean contains(Object o);
    //返回内部迭代器
    Iterator<E> getIterator();
    //将集合转化为数组输出
    Object[] toArray();
    <T> T[] toArray(T []a);
    //增加一个元素
    boolean add(E e);
    //删除某个元素
    boolean remove(E e);
    //是否包含集合c中所有元素
    boolean containsAll(Collection<?> c);
    //将集合c中的元素全部加入
    boolean addAll(Collection<? extends E> c);
    //将所有在集合c中的元素从本集合中删除
    boolean removeAll(Collection<?> c);
    //判断两个集合是否相等
    boolean equals(Object o);
    //将集合清空
    void clear();
    int hashCode();
}
