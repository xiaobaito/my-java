package com.zx.ioc.bean;

import org.springframework.beans.factory.annotation.Value;

public class Person {

    /**
     * 使用@Value赋值；
     * 1、基本的数值
     * 2、可以写SpEL表达式
     * 3、可以写${}；去除配置文件(*.properties)中的值(会通过@propertySource配置读取到运行的环境中)
     *
     */
    @Value(value = "李四")
    private String name;
    @Value(value = "#{20-2}")
    private Integer age;

    @Value("${person.nickName}")
    private String nickName;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", nickName='" + nickName + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
