package com.ryd.gyy.guolinstudy.Model;

public class JianzhiResult {

    /**
     * name : 寒假实习生200/天
     * salary : 200元/日
     * location : 温州温州
     * company : 点创网络
     */

    private static String name;
    private static String salary;
    private static String location;
    private static String company;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


    public String toString() {
        return "JianzhiResult{" +
                ", name='" + name + '\'' +
                ", salary='" + salary + '\'' +
                ", location=" + location +
                ", company=" + company +
                '}';
    }
}
