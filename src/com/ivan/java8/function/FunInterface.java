package com.ivan.java8.function;

/**
 * Created by feiFan.gou on 2018/2/1 14:13.
 */
@FunctionalInterface
public interface FunInterface {

    void print(int x, int y);

    default int caculate(int x, int y){
        return x - y;
    }
}
