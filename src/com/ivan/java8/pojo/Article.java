package com.ivan.java8.pojo;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.ToString;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by feiFan.gou on 2017/8/21 17:53.
 */
@Data
@ToString
public class Article {

    private String title;

    private String author;

    private Integer count;

    public static List<Article> data;

    static {
        data = Lists.newArrayList(
                new Article("标题4", "作者1", 12),
                new Article("标题7", "作者4", 1),
                new Article("标题3", "作者5", 10),
                new Article("标题1", "作者3", 11),
                new Article("标题10", "作者5", 3),
                new Article("标题6", "作者5", 4),
                new Article("标题2", "作者4", 7),
                new Article("标题8", "作者2", 5),
                new Article("标题9", "作者6", 2),
                new Article("标题5", "作者6", 6),
                new Article("标题11", "作者11", 9),
                new Article("标题12", "作者12", 8)
        );
    }

    public Article(String title, String author, Integer count) {
        this.title = title;
        this.author = author;
        this.count = count;
    }

    public Article(String title) {
        this.title = title;
    }
}
