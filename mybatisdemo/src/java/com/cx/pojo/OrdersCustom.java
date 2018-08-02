package com.cx.pojo;

public class OrdersCustom extends Orders {

    //继承了Orders，已经有了Orders的所有属性了
    //下面添加用户属性
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
//省略get和set方法


}
