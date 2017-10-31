package com.ivan.java8.kit;

/**
 * Created by feiFan.gou on 2017/10/30 15:32.
 */
public class StringKit {

    public static final String empty = "";

    public static boolean isEmpty(final String s) {
        return s == null || "".equals(s) || s.trim().length() == 0;
    }

    public static boolean isNotEmpty(String string) {

        return null != string && !"".equals(string.trim());
    }

    public static String join(String sign,final String... params) {

        StringBuilder sb = new StringBuilder();
        for (String s : params)
            sb.append(sign).append(s);
        return sb.substring(1);
    }

    public static String trim(String string) {

        if (isEmpty(string)) {
            return empty;
        }
        return string.trim();
    }
}
