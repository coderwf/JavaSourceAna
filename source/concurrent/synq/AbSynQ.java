package source.concurrent.synq;

public abstract class AbSynQ {
    
	/*���ȶ�������еĽڵ�,�ڵ��൱�ڶ�ĳ���ȴ��̵߳�����
	 ���а������̵߳�ĳЩ��Ϣ*/
	
	static final class Node{
		//�̶߳���ռ�õ�״̬������:��ռ������
		static final Node SHARED      = new Node();
		static final Node EXCLUSIVE   = null ;
		
		//�̵߳ĵȴ�״̬ cancelled 1 
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
	
	/*�������Լ�ʵ�ֵ�tryAcquire()
	 * �� tryRelease()
	 * �������û��ʵ������׳�һ������ʱ�쳣*/
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
	/*�����Լ�ʵ��Ϊ�Ƿ��Ƕ�ռ��״̬*/
	protected boolean isExclusive() {
		throw new UnsupportedOperationException();
	}//
	
	//�жϵ�ǰ���߳�
	static void selfInterrupt() {
		Thread.currentThread().interrupt();
	}//
	
	
}