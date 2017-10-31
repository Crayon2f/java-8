package com.ivan.java8;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList("1", "2");
        String[] urls = new String[list.size()];
        urls = list.toArray(urls);
        System.out.println(Arrays.toString(urls));
    }
}
