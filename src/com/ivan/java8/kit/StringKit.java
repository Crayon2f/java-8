package com.ivan.java8.kit;

import java.util.Optional;

/**
 * Created by feiFan.gou on 2017/10/30 15:32.
 */
public class StringKit {

    public static final String empty = "";

    public static final String divide = " ========================================== divide line =============================================== ";

    public static final String half_divide = " ======================================== ";

    public static boolean isEmpty(String string) {

        return Optional.ofNullable(string).orElse(empty).equals(empty);
    }

    public static boolean isNotEmpty(String string) {

        return !isEmpty(string);
    }

    public static String join(String sign, final String... params) {

        StringBuilder sb = new StringBuilder();
        for (String s : params)
            sb.append(sign).append(s);
        return sb.substring(1);
    }

    public static String trim(String string) {

        return Optional.ofNullable(string).map(String::trim).orElse(empty);
    }
}
