package jdk_source.collection;

//�̳�iterable�ӿ�,ʹ�ӿ��ܹ�ͨ�� forEach��������
public interface Collection<E> {
	//���ϵĴ�С
    int size();
    //�жϼ����Ƿ�Ϊ��
    boolean isEmpty();
    //�жϼ����Ƿ����ĳ��Ԫ��
    boolean contains(Object o);
    //�����ڲ�������,���Է��ʼ����ڲ�Ԫ��
    Iterator<E> getIterator();
    //������ת��Ϊһ������
    Object[] toArray();
    <T> T[] toArray(T []a);
    boolean add(E e);
    boolean remove(E e);
    boolean containsAll(Collection<?> c);
    boolean addAll(Collection<? extends E> c);
    boolean removeAll(Collection<?> c);
    boolean equals(Object o);
    void clear();
    int hashCode();
}
