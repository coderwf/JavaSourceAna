package jdk_source.collection.test;

public class TestGrow {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
        int Max = Integer.MAX_VALUE;
        int Max_A = Integer.MAX_VALUE - 8;
        int b = (int)(Max / 1.5);
        b = b + b>> 1;
        System.out.println(b);
        System.out.println(b - Max_A);
	}
	
	private static void grow(int start) throws InterruptedException {
		while(true) {
			System.out.println(start);
			//System.out.println(start - (Integer.MAX_VALUE-8));
			System.out.println("+++++++++++++++++++");
			start = start + 1;
			Thread.sleep(200);
		}
	}//
    
	private static int hugeCap(int cap) {
		if(cap<0)
			throw new OutOfMemoryError();
		return cap > Integer.MAX_VALUE -8 ? Integer.MAX_VALUE: Integer.MAX_VALUE -8;
	}
}
