package com.huawei;

import java.util.*;

public class TestB {
    public static void question(int[] array){
        if (array==null){
            throw new NullPointerException();
        }
        if (array.length<2){
            return;
        }
        int temp=0;
        for (int i=0;i<array.length-1;i++){
            for (int j = 0; j < array.length-i-1 ; j++) {
                if (array[j]>array[j+1]){
                    temp=array[j];
                    array[j]=array[j+1];
                    array[j+1]=temp;
                }
            }
        }
    }

    public static int[] duplicate(List<Integer> list){
        Set<Integer> set=new HashSet<>(list);
        Integer[] array = set.toArray(new Integer[0]);
        int[] res = Arrays.stream(array).mapToInt(Integer::valueOf).toArray();
        return res;
    }
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        List inlist=new ArrayList();

        int num=scanner.nextInt();

        for (int i=0;i<num;i++){
            inlist.add(scanner.nextInt());
        }

        int[] arry = duplicate(inlist);
        question(arry);
        for (int n: arry) {
            System.out.println(n);
        }


    }
}
