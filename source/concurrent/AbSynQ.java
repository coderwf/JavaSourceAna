package source.concurrent;

/*用一个FIFO队列提供一个能够实现同步锁的框架
 * <pre>
     *      +------+  prev +-----+       +-----+
     * head |      | <---- |     | <---- |     |  tail
     *      +------+       +-----+       +-----+
     * </pre>
     *
     *能够持有锁的线程要么在队列之外要么在队列头部
     *
     *线程使用acquire()获取锁,这个方法是不可覆盖的final方法,由框架提供实现,
     *acquire()首先调用tryAcquire()方法,这个方法具体的实现由应用场景决定,交给子类自己实现
     *当tryAcquire()失败.将线程装入队列节点Node中然后放入队列.
     *
     *总体思想:线程醒过来有两种方式 1.被unpark()唤醒  2.被中断
     *acquireQueued()是一个不断尝试获取锁的函数(for;;).并返回线程的中断信息
     *只有队列头部的线程才是当前持有锁的线程,所以新加入的节点,只有当自己的前驱节点是头部节点
     *的时候自己才有获取锁的资格,但不一定能获取成功.如果获取成功则直接返货false
     *如果获取失败,1.有获取的资格但是获取失败 2.没有获取的资格
     *首先将线程park,线程进入阻塞状态被挂起,如果线程醒过来继续获取锁,那么线程可能是因为中断被唤醒而不是
     *被前驱节点唤醒,所以此时应该返回中断信息,并恢复线程的中断状态
     *线程在进入park之前仍然有一次获取的机会,如果获取成功可以节省线程状态切换的开销
     *如果获取成功,则将头部设置为
     *如果获取失败,在将线程park掉.
     *
 */
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
	
	protected final boolean compareAndSetHead(Node expect,Node node) {
		synchronized (this) {
			if (this.head == expect) {
				this.head = node;
				return true;
			}//if
			return false;
		}//syn
	}
	
	protected final boolean compareAndSetTail(Node expect,Node node) {
		synchronized (this) {
			if(this.tail == expect) {
				this.tail = node;
				return true;
			}//if
			return false;
		}
	}//
	
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
