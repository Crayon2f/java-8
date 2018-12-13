package com.crayon2f.java8.stream;

import com.crayon2f.common.pojo.Data;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by feiFan.gou on 2018/2/9 10:16.
 * stream 的生成
 */
class Creator {

    @Test
    void creator() {

        // 1.Create Streams from Values
        Stream.of("a", "b", "c").forEach(System.out::println);
        // 2.Stream Builder
        Stream.<String>builder().add("a").add("b").add("c").build().forEach(System.out::println);
        // 3.IntStream from range
        IntStream.range(1, 3).forEachOrdered(System.out::println);
        // 4.Empty Streams
        Stream empty = Stream.empty();
        // 5.Stream.iterate() seed:种子,起源意思
        // for example: 取出12个奇数,从1开始
        Stream<Integer> iterate = Stream.iterate(1, i -> i + 2).limit(12);
        iterate.forEach(System.out::println);
        // 6.Stream.generate() 传入无参的函数
        System.out.println("============== math.random ==============");
        Stream.generate(Math::random).limit(5).forEach(System.out::println);
        System.out.println("============== random.nextInt ==============");
        Stream.generate(new Random()::nextInt).limit(5).forEach(System.out::println);
        System.out.println("============== random.ints ==============");
        new Random().ints(10).forEach(System.out::println);
        // 范围(0,10) 0~9 遵循要头不要尾
        System.out.println("============== random.ints has range ==============");
        new Random().ints(10, 1, 10).forEach(System.out::println);
        System.out.println("============== random.ints has range ==============");
//        new Random().ints(0,20).filter(ths -> ths == 20).limit(100).forEach(System.out::println);

        // 7.Streams from Arrays
        Arrays.stream(new String[]{"a", "b"}).forEach(System.out::println);
        // 8.Streams from Collections
        Lists.newArrayList("cc","d").stream().filter(ths -> ths.length() > 0).forEach(System.out::println);
        // 9.Streams from Char Sequence
        System.out.println("============== stream from string ==============");
        String str = "5 123,123,qwe,1,123, 25";
        str.chars().filter(ths -> Character.isDigit((char)ths) || Character.isWhitespace(((char) ths))).forEach(ths -> System.out.println(((char) ths)));
        // 10.Streams from Regex
        System.out.println("============== stream from regex ==============");
        String splitStr = "html|javascript|css|python";
        Pattern.compile("[|]").splitAsStream(splitStr).forEach(System.out::println);
        // 11.Stream From Files
        System.out.println("============== stream from Files.lines ==============");
        Path path = Paths.get("C:\\Users\\feifan.gou\\Desktop\\path.txt");
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("============== stream from Files.walk ==============");
        Path dir = Paths.get("D:\\images");
        try (Stream<Path> pathStream = Files.walk(dir)) {
            Files.list(Paths.get("d:\\")).forEach(System.out::println);
            pathStream.forEach(p -> {
                System.out.println(p.getFileName());
                System.out.println(p.getNameCount());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test() {

        IntStream.range(1, 9).forEach(System.out::println);

        System.out.println(Data.INTEGER_LIST);
        System.out.println(Data.STRING_LIST);
    }
}
