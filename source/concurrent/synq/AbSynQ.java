package source.concurrent.synq;

public abstract class AbSynQ {
    
	/*首先定义队列中的节点,节点相当于对某个等待线程的容器
	 其中包含了线程的某些信息*/
	
	static final class Node{
		//线程对锁占用的状态有两种:独占、共享
		static final Node SHARED      = new Node();
		static final Node EXCLUSIVE   = null ;
		
		//线程的等待状态 cancelled 1 
		static final int CANCELLED    = 1;
		static final int SIGNAL       = -1;
		
		volatile int waitstatus;
		volatile Node next;
		volatile Node prev;
		volatile Thread thread;
		
		Node nextWaiter;
		final boolean isShared() {
			return nextWaiter == SHARED;
		}
		final Node getPrev() throws NullPointerException{
			Node p = prev;
			if(p == null)
				throw new NullPointerException();
			else
				return p;
		}
		
		Node (){}
		Node(Thread thread , int waitstatus){
			this.thread     =   thread;
			this.waitstatus =   waitstatus;
		}
		Node(Thread thread,Node mode){
			this.thread = thread;
			this.nextWaiter = mode;
		}
	}//Node in queue
	
	private volatile Node head;
	private volatile Node tail;
	private volatile int  state;
	
	protected final int getState() {
		return state;
	}
	protected final void setState(int newState) {
		this.state = newState;
	}
	protected final boolean compareAndSetState(int expected,int update) {
		synchronized (this) {
			if(this.state == expected) {
				this.state = update;
				return true;
			}//if
			return false;
		}//syn
	}//compareAndSetState
	
	static final long spinForTimeout = 1000L;
	private void setHead(Node node) {
		this.head     = node;
		node.thread   = null;
		node.prev     = null;
    }
	
	/*给子类自己实现的tryAcquire()
	 * 和 tryRelease()
	 * 如果子类没有实现则会抛出一个运行时异常*/
	protected boolean tryAcquire(int arg) {
		throw new UnsupportedOperationException();
	}//
	protected boolean tryRelease(int arg) {
		throw new UnsupportedOperationException();
	}//
	protected boolean tryAquireShared(int arg) {
		throw new UnsupportedOperationException();
	}//
	protected boolean tryReleaseShared(int arg) {
		throw new UnsupportedOperationException();
	}//
	/*子类自己实现为是否是独占锁状态*/
	protected boolean isExclusive() {
		throw new UnsupportedOperationException();
	}//
	
	//中断当前的线程
	static void selfInterrupt() {
		Thread.currentThread().interrupt();
	}//
	
	
}
