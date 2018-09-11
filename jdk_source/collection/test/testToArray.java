package jdk_source.collection.test;

import java.util.ArrayList;
import java.util.List;
public class testToArray {
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(10);
		list.add(11);
		list.add(12);
		list.add(13);
		System.out.println(list.toArray());
	}//main

}//class
