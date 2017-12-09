package com.ivan.java8.enums;

import lombok.Getter;

/**
 * Created by feiFan.gou on 2017/12/9 14:45.
 */
@Getter
public enum Gender {

    male("男"), female("女"),;


    private final String name;

    Gender(String name) {

        this.name = name;
    }
}
