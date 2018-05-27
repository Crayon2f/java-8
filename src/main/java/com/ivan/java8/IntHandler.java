package com.ivan.java8;

@FunctionalInterface
public interface IntHandler {

    void run();

    boolean equals(Object o);

    default void test() {

        run();
    }
}
