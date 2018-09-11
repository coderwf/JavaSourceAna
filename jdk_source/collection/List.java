package jdk_source.collection;

public interface List<E>{
	E get(int index);
	E set(int index,E ele);
	void add(int index,E ele);
	int lastIndexOf(Object e);
    int indexOf(Object e);
    List<E> subList(int fromIndex,int toIndex);//不包括toIndex
}
