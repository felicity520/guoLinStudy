package com.ryd.gyy.guolinstudy.testjava;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ArrayList;

import static java.sql.DriverManager.println;

/***
 * 通过反射运行配置文件内容
 * 原型模式：https://www.jianshu.com/p/465c25491eaf
 * 浅拷贝的时候String等基本类型数据是不会改变的，ArrayList<String>这样的引用类型来说，原始的数据也会改变。
 */
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

    public static void main(String[] arg) {
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


        //通过反射获取Class对象
        Class stuClass = null;//"cn.fanshe.Student"
        try {
            stuClass = Class.forName(getValue("className"));
            //2获取show()方法
            Method m = stuClass.getMethod(getValue("methodName"));//show
            //3.调用show()方法
            m.invoke(stuClass.getConstructor().newInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        //通过反射获取返回类型
        try {
            //1、获取Student对象的字节码
            Class clazz = Class.forName("com.ryd.gyy.guolinstudy.testjava.Student");
            //2、获取myShow方法
            Method methodMain = clazz.getMethod("myShow", String.class);//第一个参数：方法名称，第二个参数：方法形参的类型，
            Method myShow2Method = clazz.getMethod("myShow2", Object.class);//第一个参数：方法名称，第二个参数：方法形参的类型，
            //实例化一个Student对象
            Object obj = clazz.getConstructor().newInstance();
            //3、调用myShow方法
            Object my_obj = methodMain.invoke(obj, "12334");//方式一
            Object my_obj_show2 = myShow2Method.invoke(obj, 123456);//方式一
            System.out.println("my_obj-----" + my_obj);
            //获取返回值的类型：getReturnType
            System.out.println("methodMain-----" + methodMain.getReturnType());
            System.out.println("my_obj_show2-----" + my_obj_show2);
            System.out.println("myShow2Method-----" + myShow2Method.getReturnType());

        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
         * 通过反射越过泛型检查
         *
         * 例如：有一个String泛型的集合，怎样能向这个集合中添加一个Integer类型的值？
         */
        try {
            ArrayList<String> strList = new ArrayList<>();
            strList.add("aaa");
            strList.add("bbb");

            //	strList.add(100);
            //获取ArrayList的Class对象，反向的调用add()方法，添加数据
            Class listClass = strList.getClass(); //得到 strList 对象的字节码 对象
            //获取add()方法
            Method m = null;
            m = listClass.getMethod("add", Object.class);
            //调用add()方法
            m.invoke(strList, 100);
            //遍历集合
            for (Object obj : strList) {
                System.out.println(obj);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    //此方法接收一个key，在配置文件中获取相应的value
    public static String getValue(String key) throws IOException {
        Properties pro = new Properties();//获取配置文件的对象
        FileReader in = new FileReader("C:\\Users\\ADMIN\\Desktop\\pro.txt");//获取输入流
        pro.load(in);//将流加载到配置文件对象中
        in.close();
        return pro.getProperty(key);//返回根据key获取的value值
    }


}


