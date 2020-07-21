package com.company;

import java.util.List;
import java.util.ArrayList;
public class ListTest{
	public List printList(List<Integer> list){
		for (int a : list ) {
			System.out.println(a);
		}
		return list;
	}
	public static void main(String[] args) {
		List<Integer> list = new ArrayList();
		list.add(1);
		list.add(2);
		new ListTest().printList(list);
	}
}