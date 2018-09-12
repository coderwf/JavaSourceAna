package jdk_source.collection;
import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractCollection<E> implements Collection<E>{
	//虚拟机再数组头中保存了一些header-words,为了避免溢出所以 - 8
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE -8 ;
    protected AbstractCollection() {}
    @Override
	public abstract int size();
    @Override
	public abstract Iterator<E> getIterator();
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size() == 0;
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
	    Iterator<E> iterator = getIterator();
		if(o == null) {
			while(iterator.hasNext())
				if(iterator.next() == null)
					return true;
		}//if
		else {
			while(iterator.hasNext())
				if(iterator.next().equals(o))
					return true;
		}//else
		return false;
	}//contains

	
	@Override
	public Object[] toArray() {
		//首先实例化一个数组
		Object [] array = new Object [size()];
		//将集合中元素放入数组中
		Iterator<E > iterator = getIterator();
		for(int i=0;i<array.length;++i) {
			if(iterator.hasNext() == false) {
				//如果集合中的元素没有了,直接用copy返回这个数组的复制版本
				return Arrays.copyOf(array,i);
			}//if
			//否则将元素放入array数组中
			array[i] = iterator.next();
		}//for
		//for循环结束,可能集合中有多余的元素没放入数组中
		return iterator.hasNext() ? finishToArray(array,iterator):array;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(E e) {throw new UnsupportedOperationException();}

	@Override
	public boolean remove(E e) {
		Iterator<E > iterator = getIterator();
		if(e == null) {
			while(iterator.hasNext()) {
				if(iterator.next() == null) {
					iterator.remove();
					return true;
				}//if
			}//while
		}//if
		else {
			while(iterator.hasNext()) {
				if(iterator.next().equals(e)) {
					iterator.remove();
					return true;
				}//if
			}//while
		}//else
		return false;
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
	    Iterator<?> cIterator = c.getIterator();
	    while(cIterator.hasNext()) {
	    	if(!contains(cIterator.next()))
	    		return false;
	    }//while
	    return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean modified = false;
		Iterator<? extends E> cIterator = c.getIterator();
		while(cIterator.hasNext()) {
			if(add(cIterator.next()))
				modified = true;
		}//while
		return modified;
	}

	@Override
	public void clear() {
		Iterator<E > iterator = getIterator();
		while(iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}//while
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		 Objects.requireNonNull(c);
		 boolean modified = false;
		 Iterator<E> iterator = getIterator();
		 while(iterator.hasNext()) {
			 if(c.contains(iterator.next())) {
				 iterator.remove();
				 modified = true;
			 }//if
		 }//while
		 return true;
	}//
	
	//将iterator中剩余元素放入array中,arrya需要扩容
	//扩容方法是每次将容量扩大为现在的1.5倍
	//由于每次扩容都需要重新复制所有值所以容量的选择很重要,合适的容量可以提高性能
    private static <T> T[] finishToArray(T[] array,Iterator<?> iterator) {
    	int start = array.length;
    	while(iterator.hasNext()) {
    		int cap = array.length;
    		//start已经等于当前容量，需要继续扩容
    		if(start == cap) {
    			int newCap = ( cap >> 1 ) + cap + 1;//容量为当前容量1.5倍+1
    			//newCap - MAX_ARRAY_SIZE > 0
                //如果式子大于0 那么newCap可能溢出了 如果式子小于0 那么newCap肯定没溢出
    			//换句话说如果newCap溢出那么这个式子值必定大于 0 所以此时不能采用newCap的值
    			//只有溢出大于Integer.Max_Value - 8的时候这个式子才 < 0但是不可能溢出这么多
    			//容量先以1.5倍速度增长然后以+1的速度增长
    			//
    			if(newCap - MAX_ARRAY_SIZE > 0)
    				newCap = maxCapacity(cap+1);
    			array = Arrays.copyOf(array, newCap);
    		}//if 如果
    		array[start++] = (T)iterator.next();
    	}//while
    	//此时可能容量扩的比较大所以需要将多分配的容量减掉
    	return array.length == start? array:Arrays.copyOf(array, start);
    }
    
    //一般情况会保留8个防止溢出,如果需要用这个8个int的空间那么还是可以使用
    private static int maxCapacity(int capacity) {
    	//如果cap溢出则直接抛出异常
    	//cap以1增加最后溢出为最大int 负数
        if(capacity < 0)
        	throw new OutOfMemoryError("capacity less than 0 or overflow");
        //如果需要的空间小于MAX_ARRAY_SIZE则还是使用这个，如果真的大于则将最后8个int空间也给他
        return capacity > MAX_ARRAY_SIZE ? Integer.MAX_VALUE:MAX_ARRAY_SIZE;
    }
}
