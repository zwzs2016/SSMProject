package com.uwan.common.util;

import java.util.regex.Pattern;

public class FileUtils {
    /**
     * 精确计算base64字符串文件大小（单位：B）
     *
     * @param base64String
     * @return double 字节大小
     *
     */
    public static double base64FileSize(String base64String) {
        /**检测是否含有base64,文件头)*/
        if (base64String.lastIndexOf(",") > -1) {
            base64String = base64String.substring(base64String.lastIndexOf(",") + 1);
        }
        /** 获取base64字符串长度(不含data:audio/wav;base64,文件头) */
        int size0 = base64String.length();
        if (size0 > 10) {
            /** 获取字符串的尾巴的最后10个字符，用于判断尾巴是否有等号，正常生成的base64文件'等号'不会超过4个 */
            String tail = base64String.substring(size0 - 10);
            /** 找到等号，把等号也去掉,(等号其实是空的意思,不能算在文件大小里面) */
            int equalIndex = tail.indexOf("=");
            if (equalIndex > 0) {
                size0 = size0 - (10 - equalIndex);
            }
        }
        /** 计算后得到的文件流大小，单位为字节 */
        return size0 - ((double) size0 / 8) * 2;
    }

    /**
     * @param :base64:上传的base64
     * @param size:限制大小
     * @param unit:限制单位（B,K,M,G)
     * @return boolean:是否大于
     *
     * @dateTime 2020-10-21 14:42:17
     * <p>
     * 判断文件大小
     */
    public static boolean checkBase64Size(String base64, int size, String unit) {
        // 上传文件的大小, 单位为字节.
        double len = base64FileSize(base64);
        // 准备接收换算后文件大小的容器
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        // 如果上传文件大于限定的容量
        if (fileSize > size) {
            return false;
        }
        return true;
    }

    /**
     * base64格式校验
     *
     * @param str
     * @return
     */
    public static boolean isBase64(String str) {
        // 字符串只可能包含A-Z，a-z，0-9，+，/，=字符
        // 字符串长度是4的倍数
        // 只会出现在字符串最后，可能没有或者一个等号或者两个等号
        // 接收的str是否无换行符
        String string = str.replaceAll("[\\s*\t\n\r]", "");
        String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        return Pattern.matches(base64Pattern, string);
    }
}
