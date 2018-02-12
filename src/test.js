"use strict";
var a = '';
var json = {
    a:'1',
    b:'2',
    c:'3',
    d:'4'
};
for(var key in json) {
    print(key,'----',json[key]);
}
var Article = Java.type("com.ivan.java8.pojo.Article");
print(Article);
var article = new Article("标题3", "作者5", 10);
print(article);
// print(key_1)
function result() {

    return JSON.stringify(json);
}
result();

function add(x,y) {
    return x + y
}

function reduce(x, y) {
    return x - y;
}

var person = {
    eat:function (food) {
        printf("food is %s", food);
    }
};


