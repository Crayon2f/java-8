package com.ivan.java8;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ivan.java8.kit.StringKit;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    @Test
    public void test() throws IOException, ParseException {

//        System.out.println(Stream.of("a", "b", "c").peek(System.out::println).peek(System.out::println).collect(Collectors.toList()));

//        URL url = new URL("");

//        String string = "8,9,2,3,4,9,2,7,0,5,9,2,3,8,4,7,5,6,2,8,3,4,5,6,7,8";
//
//        StringBuilder builder = new StringBuilder("");
//        List<Integer> collect = Lists.newArrayList(string.split(",")).parallelStream().peek(s -> builder.append(",").append(s))
//                .map(Integer::valueOf).collect(Collectors.toList());
//        System.out.println(builder);
//        System.out.println(collect);

//        System.out.println(StringKit.isEmpty(StringKit.empty));
//        System.out.println(StringKit.isNotEmpty(null));
//        System.out.println(StringKit.trim(null).length());


//        URL url = new URL("http://small-bronze.oss-cn-shanghai.aliyuncs.com/video/client/2018/1/3/BDD63518B4FB44B8A5D3ABFDA18B635B.mp4");
//        InputStream inputStream = url.openStream();
//        File file = new File("/" + UUID.randomUUID().toString() + ".mp4");
//        System.out.println(file.getAbsolutePath());
//        if (!file.exists() && file.createNewFile()) {
//            FileOutputStream fos = new FileOutputStream(file);
//            byte[] buffer = new byte[1024];
//            int temp;
//            while (-1 != (temp = inputStream.read(buffer))) {
//                fos.write(buffer, 0, temp);
//            }
//            inputStream.close();
//            fos.close();
//        }

//        FileInputStream fis = new FileInputStream("D:\\downloads\\剧本1.lrc");
//        byte[] buffer = new byte[1024];
//        int temp;
//        while (-1 != (temp = fis.read(buffer))) {
//            fis.read()
//        }
        BufferedReader reader = new BufferedReader(new FileReader("D:\\downloads\\剧本1.lrc"));
        while (null != reader.readLine()) {
            if (null == reader.readLine()) {
                break;
            }
            System.out.println(null == reader.readLine());
            int to = reader.readLine().indexOf("]");
            System.out.println(reader.readLine().substring(1, to));
        }
        reader.close();

        System.out.println(Arrays.toString("asdjf.dlsfk".split("\\.")));
    }

}
