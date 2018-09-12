package jdk_source.collection;

public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> {
	//abstractList只能由子类调用
	//内部实现一个listIterator
    protected AbstractList() {}
    public void add(int index,E e) {
    	//由子类自己实现
    	throw new UnsupportedOperationException();
    }
    public boolean add(E e) {
    	add(size(),e);
    	return true;
    }
    public abstract E get(int index);
    public E set(int index,E e) {
    	//子类实现
    	throw new UnsupportedOperationException();
    }
    public E remove(int index) {
    	throw new UnsupportedOperationException();
    }
    
    private void rangeCheck(int index) {
    	if(index < 0 || index > size())
    		throw new RuntimeException("index out of bounds");
    }
    public ListIterator<E> getListIterator(){
    	return getListIterator(0);
    }
    public ListIterator<E> getListIterator(final int index){
    	rangeCheck(index);
    	return new ListIter(index);
    }//
    
    private class Itr<E> implements Iterator<E>{

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			return null;
		}
    	
    }
}
