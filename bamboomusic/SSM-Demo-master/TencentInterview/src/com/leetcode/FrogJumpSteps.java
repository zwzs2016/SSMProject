package com.leetcode;

public class FrogJumpSteps {

    public static int numWays(int n){
        if (n<3) return n;

        int a=1,b=1,sum=0;

        for (int i=3;i<=n;i++){
            sum=(a+b)%1000000007;
            a=b;
            b=sum;
        }

        return sum;
    }


    //青蛙跳台阶
    public static void main(String[] args) {

    }
}
