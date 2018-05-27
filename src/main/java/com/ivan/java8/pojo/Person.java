package com.ivan.java8.pojo;

public class Person {

    private int age;
    private String name;

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        Person person = (Person) obj;
        return person.name.equals(this.name);
    }

    @Override
    public int hashCode() {
        return this.age;
    }
}
