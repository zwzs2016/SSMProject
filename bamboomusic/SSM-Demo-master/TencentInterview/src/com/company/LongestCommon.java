package com.company;

public class LongestCommon {
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        String gzzfc = strs[0];
        for (int i = 0; i < strs.length; i++) {
            while (strs[i].indexOf(gzzfc) != 0) {
                gzzfc = gzzfc.substring(0, gzzfc.length() - 1);
//                System.out.println(gzzfc);
                if (gzzfc.length() == 0) {
                    return "";
                }
            }
        }
        return gzzfc;
    }

    public String longestCommonPrefix2(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        if (strs[0].isEmpty()) {
            return "";
        }
        if (strs.length == 1) {
            return strs[0];
        }
        String gzzfc = strs[0].substring(0, 1);
        int i = 0;
        while (true) {
            if (strs[i].indexOf(gzzfc) == 0) {
                if (i < strs.length - 1) {
                    i++;
                    continue;
                } else {
                    try {
                        gzzfc = strs[0].substring(0, gzzfc.length() + 1);
                        i = 0;
                        continue;
                    } catch (StringIndexOutOfBoundsException e) {
                        break;
                    }
                }
            }
            if (gzzfc.equals(strs[0].substring(0,1))){
                gzzfc="";
            }else {
                gzzfc=strs[0].substring(0,gzzfc.length()-1);
            }
            break;
        }
        return gzzfc;
    }

    public static void main(String[] args) {
        String s = new LongestCommon().longestCommonPrefix(new String[]{"flower","flow","flight"});
        String s2 = new LongestCommon().longestCommonPrefix2(new String[]{"dog","racecar","car"});
        System.out.println("方法1:"+s);
        System.out.println("方法2:"+s2);
    }
}
