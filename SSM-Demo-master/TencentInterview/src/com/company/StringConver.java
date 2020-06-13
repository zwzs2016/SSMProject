package com.company;

public class StringConver {
    public int myAtoi(String str) {
        int flag=1;
        int Intnum;
        StringBuilder intstr=new StringBuilder();
        str=str.trim();
        if (str.isEmpty()){
            return 0;
        }
        if (str.charAt(0) == '-') {
            flag = -1;
            str = str.substring(1, str.length());  // 跳过符号位，可不写第二参数
        } else if (str.charAt(0) == '+') {
            str = str.substring(1, str.length());  // 跳过符号位，可不写第二参数
        } else if (!(str.charAt(0) >= '0' && str.charAt(0) <= '9')) {  // 如果开始非空字符不为符号或数字，则直接返回 0
            return 0;
        }
        if (str.isEmpty()){
            return 0;
        }
        if (str.charAt(0)<'0' || str.charAt(0)>'9'){
            return 0;
        }
        for (int i=0;i<str.length();i++){
            if (str.charAt(i)>='0' && str.charAt(i)<='9'){
                intstr.append(str.charAt(i));
            }else{
                break;
            }
        }
        String ins=""+intstr;

        try {
            Intnum = flag*Integer.parseInt(ins);
        }catch (NumberFormatException e){
            if (flag==1){
                return Integer.MAX_VALUE;
            }else {
                return Integer.MIN_VALUE;
            }
        }
        return Intnum;
    }

    public static void main(String[] args) {
        System.out.println(new StringConver().myAtoi("4193 with words"));
    }
}
