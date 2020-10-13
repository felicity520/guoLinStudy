package com.ryd.gyy.guolinstudy.testjava;

public class testMain {

    public static void main(String[] args) {
//        A a1 = new A();
//        A a2 = new B();
//        B b = new B();
//        C c = new C();
//        D d = new D();

        //所以多态机制遵循的原则概括为：当超类对象引用变量引用子类对象时，被引用对象的类型而不是引用变量的类型决定了调用谁的成员方法，
        // 但是这个被调用的方法必须是在超类中定义过的，也就是说被子类覆盖的方法，但是它仍然要根据继承链中方法调用的优先级来确认方法，
        // 该优先级为：this.show(O)、super.show(O)、this.show((super)O)、super.show((super)O)。
//        System.out.println("1----" + a1.show(b));//A and A
//        System.out.println("2----" + a1.show(c));//A and A
//        System.out.println("3----" + a1.show(d));

        //a2是引用变量，为A类型，它引用的是B对象
        //this代表A  super代表超类，A没有超类  所以跳转到第三个 A.show
//        System.out.println("4----" + a2.show(b));
//        System.out.println("5----" + a2.show(c));
//        System.out.println("6----" + a2.show(d));
//        System.out.println("7----" + b.show(b));
//        System.out.println("8----" + b.show(c));
//        System.out.println("9----" + b.show(d));


//        构造函数：
//        1 如果一个类没有定义任何构造函数，那么该类会自动生成1个默认的构造函数。默认构造函数没有参数。
//        2 如果一个类定义了构造函数，但这些构造函数都有参数，那么不会生成默认构造函数，也就是说此时类没有无参的构造函数。
//        Person p = new Person();    // 此行有错误，没有不含参数的构造函数
//        p.talk();


        //学习super
//        a x1 = new a(1);
//        b x2 = new b(1, 2);
//        System.out.println(x1.getA());
//        System.out.println(x2.getA() + " " + x2.getB());

//学习final
//        d x1 = new d();
//        x1.showInformation();


        //学习abstract
//        不能被实例化
//        Man man = new Man();

    }

    static class A {
        public String show(D obj) {
            return ("A and D");
        }

        public String show(A obj) {
            return ("A and A");
        }
    }

    static class B extends A {
        public String show(B obj) {
            return ("B and B");
        }

        public String show(A obj) {
            return ("B and A");
        }
    }


    static class C extends B {

    }

    static class D extends B {

    }

    /**
     * 构造函数示例
     */
    static class Person {
        private String name;
        private int age;

        public Person(int age) {
            name = "Yuhong";
            this.age = age;
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void talk() {
            System.out.println("我叫：" + name + "  我今年：" + age + "岁");
        }
    }

    /**
     * 学习super
     */
    static class a {
        private final int a;

        public a(int aA) {
            a = aA;
            System.out.println("我叫--：" + aA);
        }

        public int getA() {
            return a;
        }
    }

    /**
     * 学习super
     */
    static class b extends a {

        private final int b;

        public b(int aA, int aB) {
            super(aA);
            //super(aA)先调用父类的构造方法
            System.out.println("我叫：" + aA);
            b = aB;
        }

        public int getB() {
            return b;
        }
    }

    /**
     * 学习final
     */
    static class c {
        public final void showInformation() {
            System.out.println("可能是A");
        }
    }

    /**
     * 学习final
     * final放在类声明前---防止其他类继承该类。
     * <p>
     * final放在函数声明前---防止该类子类重定义此方法。
     * 不能继承但是可以new
     */
    static final class d extends c {

    }

    //会报错：final不能被继承
//    class e extends d {
//
//    }


    /**
     * 修饰词	本类	同一个包的类	继承类	其他类
     * private	    √	×	×	×
     * 无（默认）	√	√	×	×
     * protected	√	√	√	×
     * public	    √	√	√	√
     * protected相比public就是对其他类不可见
     */


    /**
     * 学习abstract
     */
//    public abstract static class Person {
//        String name;
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String aName) {
//            name = aName;
//        }
//
//        public abstract String getDescription1();
//
//        public abstract String getDescription2();
//    }

    /**
     * 扩展一个抽象类有两种选择，一种扩展后定义部分超类抽象类中没有的定义的方法，这样子类依旧是抽象类，
     * 但是抽象类不可以被实例化
     */
//    abstract static class Man extends Person {
//        public String getDescription1() {
//            return "A simple man";
//        }
//    }

    /**
     * 另一种就是全部定义超类抽象类的方法，这样子类就不再是抽象类了。
     */
//    class Woman extends Person {
//        public String getDescription1() {
//            return "A simple woman";
//        }
//
//        public String getDescription2() {
//            return "Other description";
//        }
//    }


}
