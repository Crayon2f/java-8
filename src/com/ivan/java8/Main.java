package com.ivan.java8;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;

public class Main {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Map<String, String> map = Maps.newHashMap();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.put("d", "4");
        map.put("e", "5");
        map.put("f", "6");
        map.put("g", "7");
        map.put("h", "8");
        map.put("i", "9");
        map.put("j", "10");

        String a = map.computeIfAbsent("a", String::trim);
        System.out.println(a);

        List<String> stringList = Lists.newArrayList();
        stringList.add("sss");
        stringList.add("dd");
        stringList.add("dd");
        stringList.add("cc");
        stringList.add("ee");
        System.out.println(stringList.removeIf(String::isEmpty));
        stringList.replaceAll(String::toUpperCase);
        System.out.println(stringList);
        boolean b = stringList.stream().anyMatch(string -> string.length() > 2);
        System.out.println(b);
        Spliterator spliterator = Spliterators.spliterator(stringList, stringList.size());
        spliterator.forEachRemaining(System.out::println);
    }

    public String toSBC(String input) { //半角转全角：
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127) c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /// /// 转半角的函数(DBC case) ///
///任意字符串
/// 半角字符串 ///
///全角空格为12288，半角空格为32
///其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248 ///
    //1.人员需求:java开发至少2(API+后台PC,不包括运维),安卓1,苹果2,设计(UI)1，产品1，测试1。
    //2.服务器：至少两台(开发,测试,生产环境,如果是苹果,还需要审核环境)
    //3.支付：支付宝支付（需要申请阿里账号，需要准备一系列公司（个人）材料）；微信支付（需要注册微信商户，也需要公司材料，审核。）
    //4.登录：手机登录（注册发短信的第三方账号）；第三方登录：微信，QQ需要注册，并审核通过，且前提是APP注册相关的已经完成。
    //5.域名：需要注册。
    //6.如果app需要推送，还需调用第三方推送（如极光）
    //7.上线审核：苹果审核：每一次版本上线,都需要审核,时长至少一周。安卓审核：品牌众多，各个市场也不一样，比较麻烦，需要各个市场都审核。
                                    //--> 客户端准备UI
    //8.完成流程：需求确定-->原型图-->   //--> 后台PC开发    --> 客户端,服务端接口调试 -->测试 -->改bug -->审核 -->上线
                                    //--> 服务端准备接口
    //暂时想到的就这么多,可能还会根据具体需求,衍生出新的。
    public String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    @Test
    void test() {

        IntStream.range(72,96).forEachOrdered(i -> {
            if (i % 3 == 0) {
                System.out.println();
                System.out.print(i+2);
            } else {
                System.out.print(",");
                System.out.print(i+2);
            }
        });
    }

}
