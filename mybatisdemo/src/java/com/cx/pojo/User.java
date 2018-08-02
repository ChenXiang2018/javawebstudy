package com.cx.pojo;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{

    private static final long serialVersionUID=121321312312321L;

    private int id;
    private String name;
    private int age;
    private List<Orders> ordersList;
    public List<Orders> getOrdersList() {
        return ordersList;
    }


    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
