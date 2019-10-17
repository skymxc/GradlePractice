package com.github.skymxc.extension;

/**
 * 必须有一个 name 属性，并且有一个 name 参数的构造函数
 */
public class Fruit {

    private String name;
    private String color;

    public Fruit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void color(String color){
        setColor(color);
    }
}
