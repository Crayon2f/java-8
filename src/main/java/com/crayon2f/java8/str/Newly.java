package com.crayon2f.java8.str;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.StringJoiner;

/**
 * Created by feifan.gou@gmail.com on 2019/4/28 15:39.
 */
class Newly {

    @Test
    void stringJoiner() {
        //构造方法(分隔符,前缀,后缀)
        StringJoiner joiner = new StringJoiner(",", "[", "]");

        //setEmptyValue 当没值时,返回此数据
        joiner.setEmptyValue("0");

        //追加项
        joiner.add("1").add("2").add("3").add("4");
        System.out.println(joiner.toString());


        StringJoiner joiner2 = new StringJoiner("-", "(", ")");
        joiner2.add("5").add("6");

        //合并两个joiner 规则:前后缀,取调用merge方法的对象 分隔符,各自部分的取各自的分隔符, 两者连接的部分使用调用者的分隔符
        //例如:
        // [1,2,3,4] marge (5-6), 结果为: [1,2,3,4,5-6]
        // (5-6) marge [1,2,3,4], 结果为: (5-6-1,2,3,4)

        System.out.println(joiner.merge(joiner2).toString());
        System.out.println(joiner2.merge(joiner).toString());

        //其中 add() merge() setEmptyValue() 等方法均为链式函数,使用的是builder模式, 返回this
    }

}
