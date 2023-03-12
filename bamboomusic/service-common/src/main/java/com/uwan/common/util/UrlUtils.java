package com.uwan.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtils {
    public static String getParamByUrl(String url, String name) {
        url += "&";
        String pattern = "(\\?|&){1}#{0,1}" + name + "=[a-zA-Z0-9]*(&{1})";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(url);
        if (matcher.find()) {
            return matcher.group(0).split("=")[1].replace("&", "");
        } else {
            return "";
        }
    }
}
