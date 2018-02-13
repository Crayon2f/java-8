/**
 * Created by gou_feifan on 2018/2/13 11:19.
 */
load("nashorn:mozilla_compat.js");
importPackage(java.util);
// importClass(java.util.HashMap)

var map = new HashMap();




map.put("5", "www");
print(map);

var list = new Packages.java.util.ArrayList();
//创建一个JFrame
var frame = new javax.swing.JFrame("TEST");

print(list);
print(frame);

var ArrayList = Java.type("java.util.ArrayList");

var myList = new ArrayList();
myList.add('888');
myList.add('884');
myList.add('886');
myList.add('887');
print(myList);


