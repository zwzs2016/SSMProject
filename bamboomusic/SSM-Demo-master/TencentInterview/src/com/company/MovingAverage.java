package com.company;

public class MovingAverage {
    int size,curr,sum,cnt;
    int[] arry;

    /** Initialize your data structure here. */
    public MovingAverage(int size) {
        this.size=size;
        arry=new int[size];
    }

    public double next(int val) {
        sum+=(val-arry[curr%size]);
        arry[curr++%size]=val;
        return (double)sum/Math.min(curr, size);
    }
}
