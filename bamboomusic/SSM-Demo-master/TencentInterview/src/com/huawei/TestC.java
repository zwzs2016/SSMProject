package com.huawei;


/*

3.
进制转换
写出一个程序，接受一个十六进制的数，输出该数值的十进制表示。

数据范围：保证结果在 1 \le n \le 2^{31}-1 \1≤n≤2
31
 −1
时间限制：C/C++ 1秒，其他语言2秒
空间限制：C/C++ 32M，其他语言64M
输入描述：
输入一个十六进制的数值字符串。

输出描述：
输出该数值的十进制字符串。不同组的测试用例用\n隔开。

示例1
输入例子：
0xAA
输出例子：
170
 */

import java.util.Scanner;

public class TestC {
    public static void question(){

    }
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println(Integer.decode(scanner.next()));
    }
}
