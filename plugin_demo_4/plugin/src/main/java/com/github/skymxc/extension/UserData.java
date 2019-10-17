package com.github.skymxc.extension;


import org.gradle.api.Action;

/**
 * 为了实践嵌套 DSL 建的
 */
public class UserData {
    private String name;
    private int age;
    public String getName() {
        return name;
    }

    /**
     * 注意此方法 没有 set
     * @param name
     */
    public void name(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void age(int age) {
        this.age = age;
    }

}
