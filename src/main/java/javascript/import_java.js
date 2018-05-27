/**
 * Created by gou_feifan on 2018/2/13 11:34.
 */
var javaLangAndUtilPkg = JavaImporter(java.util, java.lang);
with (javaLangAndUtilPkg) {
    var list = new LinkedList();
    list.add('a');
    list.add('c');
    list.add('b');

    print(list);
}

var LocalDate = Java.type("java.time.LocalDate");

var date = LocalDate.now();

print(date['year']);
print(date['month']);
print(date.getDayOfMonth());

var ArrayList = Java.type("java.util.ArrayList");
var arrayList = new ArrayList();
arrayList.add("9");
arrayList.add("5");
arrayList.add("3");
arrayList.add("1");


//脚本语言的解释器根据参数的运行时类型来解析重载的方法。
var Article = Java.type("com.ivan.java8.pojo.Article");

var article = new Article("标题12", "作者12", 8);

article.read(article.getCount());
article.read(article.getTitle());

