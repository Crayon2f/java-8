package com.crayon2f.java8.kit;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by feiFan.gou on 2017/10/30 15:32.
 */
public class StringKit {

    public static final String empty = "";

    public static final String divide = " ========================================== divide line =============================================== ";

    public static final String half_divide = " ======================================== ";

    public static final Function<String, String> divide_with_content = content -> String.format(half_divide + " %s " + half_divide, content);

    public static boolean isEmpty(String string) {

        return Optional.ofNullable(string).orElse(empty).equals(empty);
    }

    public static boolean isNotEmpty(String string) {

        return !isEmpty(string);
    }

    public static String trim(String string) {

        return Optional.ofNullable(string).map(String::trim).orElse(empty);
    }
}
