package com.ryd.gyy.guolinstudy.Model;

public class JianzhiResult {

    /**
     * name : 寒假实习生200/天
     * salary : 200元/日
     * location : 温州温州
     * company : 点创网络
     */

    private static String mName;
    private static String mSalary;
    private static String mLocation;
    private static String mCompany;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSalary() {
        return mSalary;
    }

    public void setSalary(String salary) {
        mSalary = salary;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String company) {
        mCompany = company;
    }


    @Override
    public String toString() {
        return "JianzhiResult{" +
                ", name='" + mName + '\'' +
                ", salary='" + mSalary + '\'' +
                ", location=" + mLocation +
                ", company=" + mCompany +
                '}';
    }
}
