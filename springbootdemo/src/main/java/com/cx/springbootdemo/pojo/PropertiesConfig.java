package com.cx.springbootdemo.pojo;

import org.springframework.stereotype.Component;

@Component
public class PropertiesConfig {

//    @Value("${test.user.username}")
    private String username;

//    @Value("${test.user.age}")
    private String age;

//    @Value("${test.user.toString}")
    private String toString;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getToString() {
        return toString;
    }

    public void setToString(String toString) {
        this.toString = toString;
    }
}
