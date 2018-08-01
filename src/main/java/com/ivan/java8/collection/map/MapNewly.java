package com.ivan.java8.collection.map;

import com.ivan.java8.kit.StringKit;
import com.sun.istack.internal.NotNull;
import lombok.NonNull;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by feiFan.gou on 2017/12/9 11:38.
 * 1.8 map新增的方法
 */
class MapNewly {

    private Map<String, String> map = new HashMap<String, String>() {{
        put("a", null);
        put("b", "2");
        put("c", "3");
        put("d", "4");
        put("e", "5");
    }};

    @Test
    void each() {

        map.forEach((key, value) -> {
            System.out.println(key);
            System.out.println(value);
            System.out.println("===================");
        });
    }

    @Test
    void replace() {

        map.replace("a", "3"); //覆盖
        System.out.println(map.get("a"));
        map.replace("f", "6"); //没有对应的key,不报错,也不会新增值
        System.out.println(map);

        map.replace("f", "3", "4"); //替换 存在的value, 如果不存在,则不替换
        System.out.println(map.get("a"));
    }

    /**
     * 替换所有，根据定义的BiFunction函数
     */
    @Test
    void replaceAll() {

        map.replaceAll((key, value) -> {

            System.out.printf("key = %s, value = %s",key, value);
            return key + " === " + value;
        });

        System.out.println();
        System.out.println(map);
    }

    /**
     * 如果值也符合,才remove
     */
    @Test
    void remove() {

        map.remove("a", "1");
        System.out.println(map);
    }

    /**
     * 获取一个不存在的key,但是依然有值,即存入的默认值
     */
    @Test
    void getOrDefault() {

//        map.put("f", null);
        System.out.println(map.getOrDefault("f", "6"));
        System.out.println(map);
    }

    /**
     * 根据key找到旧的value，然后对旧值进行操作
     *  key value 均为非空,如果传null,则会报 NPE
     */
    @Test
    void merge() {

        System.out.println(map.get("a"));
        map.merge("a", null, (oldValue, param) -> oldValue + param);
        map.merge("a", "2", (oldValue, param) -> oldValue + param);
        System.out.println(map.get("a"));
//        map.compute()
    }

    /**
     * 当key对应的value为null或者key不存在时，使用计算的结果作为新的value(根据key计算)
     */
    @Test
    void computeIfAbsent() {

//        map.put("computeIfAbsent", null);
//        map.put("computeIfAbsent", "computeIfAbsent");
        map.computeIfAbsent("computeIfAbsent", key -> String.valueOf(key.length()));
        System.out.println(map);
    }

    /**
     * 当key对应的value存在，并且不是null时，使用计算的结果作为新的value 【******并且会根据key排序】
     */
    @Test
    void computeIfPresent() {

        map.put("computeIfPresent", null);
//        map.put("computeIfPresent", "computeIfPresent");
        map.computeIfPresent("computeIfPresent", (key, value) -> key + " ====== " + value);
        System.out.println(map);

    }

    /**
     * value为null 或者key不存在，都会put进去
     */
    @Test
    void putIfAbsent() {

        map.putIfAbsent("a", "33");
        System.out.println(map);
    }

    @Test
    void sortByEntryComparator() {

        Map<String, String> map = new HashMap<>();
        map.put("1", "4");
        map.put("2", "2");
        map.put("3", "43");
        map.put("5", "65");
        map.put("0", "7");
        map.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(System.out::println);
        System.out.println(StringKit.divide);
        map.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(System.out::println);

    }

    @Test
    void compute() {

        map.compute("a", (key, value) -> {
            System.out.printf("key = %s\r", key);
            System.out.printf("value = %s\r", value);
            return key + " === " + value;
        });

        System.out.println(map);
    }

}