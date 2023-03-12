package com.company;

import java.util.Arrays;

public class Median {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        //creat a array to include them
        int[] myarray = new int[nums1.length+nums2.length];
        int index=0;

        for(int i=0;i<nums1.length;i++){
            myarray[index] = nums1[i];
            index++;
        }
        for(int j=0;j<nums2.length;j++){
            myarray[index]=nums2[j];
            index++;
        }
        return iszhong(myarray);
    }

    public static double iszhong(int[] num){
        int index1,index2;
        int num1,num2;
        Arrays.sort(num);
        if(num.length%2==0){
            index1=num.length/2;
            index2=index1-1;
            num1=num[index1];
            num2=num[index2];
            return (double)(num1+num2)/2;
        }
        else{
            index1=(num.length-1)/2;
            return num[index1];
        }
    }
}
