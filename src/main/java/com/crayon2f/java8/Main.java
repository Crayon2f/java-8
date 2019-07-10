package com.crayon2f.java8;

import com.crayon2f.java8.kit.CollectorKit;
import com.crayon2f.java8.kit.StringKit;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.crayon2f.java8.function.FunInterface;
import com.crayon2f.common.pojo.Article;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;

class Main {

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

    @Test
    void test() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("D:\\downloads\\剧本1.lrc"));
        String line;
        while (null != (line = reader.readLine())) {
            System.out.println("===================");
            Optional.of(line).filter(StringKit::isNotEmpty).ifPresent(System.out::println);
        }
        reader.close();

        FunInterface funInterface = ((x, y) -> {
            System.out.println(x + y);
            System.out.println(x + y);
        });


        Float f = 0.345456f;
        System.out.println(String.format("%.2f", f * 100));

        System.out.println(Character.isDigit('a'));
        System.out.println(Character.isWhitespace(' '));
        System.out.println(Charset.forName("UTF-8").toString());

        "abc".chars().forEach(c -> System.out.println((char) c));

    }

    @Test
    @SuppressWarnings("unchecked")
    void test2() throws IOException {

//        Path path = Paths.get("C:\\Users\\feifan.gou\\Desktop\\download\\increase_copper_cache.backup");
//
//        String strJson = new String(Files.readAllBytes(path));
//        Map<Integer,List<L ong>> map = JSONObject.parseObject(strJson, Map.class);
//
//        map.forEach((key, value) -> System.out.println(key + "==>" + value.size()));

        LocalDate date = LocalDate.now().minusDays(30);
        System.out.println(date);

//        Collectors.groupingBy()
        Map<Boolean, String> map = Article.data.stream().collect(Collectors.partitioningBy(article -> article.getCount() == 0,
                Collectors.mapping(Article::getTitle, Collectors.joining())));

    }


    @Test
    @SneakyThrows
    void jse() {

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        Object eval = engine.eval("(12+34.0+0.98-34)");
        System.out.println(eval);


    }

    @Test
    void testToMap() {

        ArrayList<Article> list = Lists.newArrayList(new Article("标题4", "作者1", 12),
                new Article("标题4", "作者3", 10),
                new Article("标题4", "作者4", 11, 3.4),
                new Article("标题4", null, 1),
                new Article("标题10", "作者5", 3, 3d),
                new Article("标题 6", "作者6", 4, 7d),
                new Article("标题2", "作者7", 7),
                new Article("标题8", "作者8", 5),
                new Article("标题9", "作者9", 2, 8.3),
                new Article("标题5", "作者10", 6),
                new Article("标题11", "作者11", 9),
                new Article("标题12", "作者12", 8, 9.0d));
//        Map<String, String> map = list.stream().collect(Collectors.toMap(Article::getTitle, Article::getAuthor));
        Map<String, String> map = list.stream().collect(CollectorKit.toMap(Article::getTitle, Article::getAuthor));
        System.out.println(map);
    }

}
