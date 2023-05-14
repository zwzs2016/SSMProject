package com.huawei;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestA {

    public static void question(int num){
        //输入的空瓶数量num
        //后续还要加剩下的瓶数
        int sum=0;
        int n=0;
        while (num>1){
            if (num==2) {
                //当瓶数为2时可以借一瓶喝
                sum++;
                break;
            }

            n=num/3;//继续计算出剩余的空瓶子兑换的汽水
            num=n+num%3;//继续计算出剩余的瓶数
            sum+=n;

        }
        System.out.println(sum);

    }
    public static void main(String[] args) {
//        List inlist=new ArrayList();

        Scanner scanner=new Scanner(System.in);
//        int num;
//        while ((num=scanner.nextInt())!=0){
//            inlist.add(num);
//        }
//        inlist.forEach(e->question((Integer) e));
        int num;
        while (scanner.hasNext()){
            num=scanner.nextInt();
            if (num==0) break;
            question(num);
        }
    }
}
