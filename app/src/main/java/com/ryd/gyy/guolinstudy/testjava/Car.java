package com.ryd.gyy.guolinstudy.testjava;

import java.util.ArrayList;

import static java.sql.DriverManager.println;

public class Car implements Cloneable {

    private String brand;
    private String modelNum;
    //增加一个引用类型
    private ArrayList<String> wheelList = new ArrayList<>();

    public Car(String brand, String modelNum) {
        this.brand = brand;
        this.modelNum = modelNum;
        wheelList.add("前轮");
        wheelList.add("后轮");
    }

    public ArrayList<String> getWheelList() {
        return wheelList;
    }

    public Car cloneCar() {
        try {
            return (Car) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String arg[]) {
        Car car = new Car("大众", "辉腾");
        Car cloneCar = car.cloneCar();
        //显示两个对象的 hashCode 和 wheelList 的大小;
        println("car_____:" + car.hashCode() + "__集合大小:" + car.wheelList.size());
        println("cloneCar_____:" + cloneCar.hashCode() + "__集合大小:" + cloneCar.wheelList.size());
        println("----------------------------------分割线----------------------------------");
        //使原对象的 wheelList 增加一个 item
        car.wheelList.add("第三个轮子");
        //再打印一遍
        println("car_____:" + car.hashCode() + "__集合大小:" + car.wheelList.size());
        println("cloneCar_____:" + cloneCar.hashCode() + "__集合大小:" + cloneCar.wheelList.size());

    }


}


