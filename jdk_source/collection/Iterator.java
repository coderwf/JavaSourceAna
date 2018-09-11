package jdk_source.collection;

/*�����ڲ��ĵ�����,ͨ��������������Է��ʼ����ڲ������ݡ�*/
public interface Iterator <E>{
    boolean hasNext();
    E       next();
    default void remove() {throw new UnsupportedOperationException("remove");}
}
