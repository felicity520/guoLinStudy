package com.ryd.gyy.guolinstudy.testjava;

import android.provider.Settings;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

//学习泛型：泛型信息不会进入到运行时阶段。Java中的泛型，只在编译阶段有效
        List<String> stringArrayList = new ArrayList<String>();
        List<Integer> integerArrayList = new ArrayList<Integer>();

        Class classStringArrayList = stringArrayList.getClass();
        Class classIntegerArrayList = integerArrayList.getClass();

        if (classStringArrayList.equals(classIntegerArrayList)) {
            //这里会打印输出
            System.out.println("泛型测试-" + "类型相同");
        }


        //泛型的类型参数只能是类类型（包括自定义类），不能是简单类型,即不能是int应该是Integer
        //传入的实参类型需与泛型的类型参数类型相同，即为Integer.
        Generic<Integer> genericInteger = new Generic<Integer>(123456);

        //如果有写<>那必须指定泛型的类型参数，传入的实参类型可以不写
        Generic<Integer> generic1 = new Generic<>(4444);
        Generic<String> generic2 = new Generic<>("nvsnbi");
        System.out.println("泛型测试Key is " + generic1.getKey());
        //通过提示信息我们可以看到Generic<Integer>不能被看作为`Generic<Number>的子类。
        // 由此可以看出:同一种泛型可以对应多个版本（因为参数类型是不确定的），不同版本的泛型类实例是不兼容的。
        //这句会报错
//        showKeyValue(generic1);


        //同时表示Generic<Integer>和Generic<Number>父类的引用类型。由此类型通配符应运而生。
        showKeyValue1(generic2);

        try {
            Object obj = genericMethod(testMain.class);
            System.out.println("obj is " + obj.toString());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        printMsg("111", 222, "aaaa", "2323.4", 55.55);


        Generic<Float> generic3 = new Generic<Float>(2.4f);
        Generic<Double> generic4 = new Generic<Double>(2.56);
        Generic<String> generic5 = new Generic<String>("11111");

//这一行代码编译器会提示错误，因为String类型并不是Number类型的子类
//        showKeyValue3(generic5);


        //直接用equals来比较就可以，不要用 ==
        String a = new String("ab"); // a 为一个引用
        String b = new String("ab"); // b 为另一个引用,对象的内容一样
        String aa = "ab"; // 放在常量池中
        String bb = "ab"; // 从常量池中查找
        if (aa == bb) // true
            System.out.println("aa==bb");
        if (a.equals(b)) // false，非同一对象
            System.out.println("a==b");
        if (a.equals(b)) // true
            System.out.println("aEQb");
        if (42 == 42.0) { // true
            System.out.println("true");
        }

        //学习java类加载
        System.out.println("java AppClassLoader ---------"+ "\n");
        System.out.println(System.getProperty("java.class.path"));
        System.out.println("java ExtClassLoader  ---------"+ "\n");
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println("java BootstrapClassLoader   ---------"+ "\n");
        System.out.println(System.getProperty("sun.boot.class.path") + "\n");


        ClassLoader C1 = Student.class.getClassLoader();
        System.out.println("C1 is " + C1);
        assert C1 != null;
        ClassLoader parent1 = C1.getParent();
        System.out.println("parent1 is " + parent1);
        ClassLoader parent2 = parent1.getParent();
        System.out.println("parent2 is " + parent2);


    }


    //在泛型方法中添加上下边界限制的时候，必须在权限声明与返回值之间的<T>上添加上下边界，即在泛型声明的时候添加
//public <T> T showKeyName(Generic<T extends Number> container)，编译器会报错："Unexpected bound"
    public <T extends Number> T showKeyName(Generic<T> container) {
        System.out.println("container key :" + container.getKey());
        T test = container.getKey();
        return test;
    }

    public static void showKeyValue3(Generic<? extends Number> obj) {
        System.out.println("泛型测试 ///  key value is " + obj.getKey());
    }

    static public <T> void printMsg(T... args) {
        for (T t : args) {
            System.out.println("泛型测试__t is " + t);
        }
    }

    /**
     * 泛型
     *
     * @param <T>
     */
    static class GenerateTest<T> {
        public void show_1(T t) {
            System.out.println(t.toString());
        }

        //在泛型类中声明了一个泛型方法，使用泛型E，这种泛型E可以为任意类型。可以类型与T相同，也可以不同。
        //由于泛型方法在声明的时候会声明泛型<E>，因此即使在泛型类中并未声明泛型，编译器也能够正确识别泛型方法中识别的泛型。
        public <E> void show_3(E t) {
            System.out.println(t.toString());
        }

        //在泛型类中声明了一个泛型方法，使用泛型T，注意这个T是一种全新的类型，可以与泛型类中声明的T不是同一种类型。
        public <T> void show_2(T t) {
            System.out.println(t.toString());
        }

        //如果静态方法要使用泛型的话，必须将静态方法也定义成泛型方法 。
        public static <T> void show(T t) {

        }

    }


    /**
     * 这个类是个泛型类，在上面已经介绍过
     */
    public class Generic123<T> {
        private T key;

        public Generic123(T key) {
            this.key = key;
        }

        //我想说的其实是这个，虽然在方法中使用了泛型，但是这并不是一个泛型方法。
        //这只是类中一个普通的成员方法，只不过他的返回值是在声明泛型类已经声明过的泛型。
        //所以在这个方法中才可以继续使用 T 这个泛型。
        public T getKey() {
            return key;
        }

        /**
         * 这个方法显然是有问题的，在编译器会给我们提示这样的错误信息"cannot reslove symbol E"
         * 因为在类的声明中并未声明泛型E，所以在使用E做形参和返回值类型时，编译器会无法识别。
         public E setKey(E key){
         this.key = key;
         }
         */


        /**
         * 这才是一个真正的泛型方法。
         * 首先在public与返回值之间的<T>必不可少，这表明这是一个泛型方法，并且声明了一个泛型T
         * 这个T可以出现在这个泛型方法的任意位置.
         * 泛型的数量也可以为任意多个
         * 如：public <T,K> K showKeyName(Generic<T> container){
         * ...
         * }
         */
        public <T> T showKeyName(Generic<T> container) {
            System.out.println("container key :" + container.getKey());
            //当然这个例子举的不太合适，只是为了说明泛型方法的特性。
            T test = container.getKey();
            return test;
        }

        //这也不是一个泛型方法，这就是一个普通的方法，只是使用了Generic<Number>这个泛型类做形参而已。
        public void showKeyValue1(Generic<Number> obj) {
            System.out.println("泛型测试 key value is " + obj.getKey());
        }

        //这也不是一个泛型方法，这也是一个普通的方法，只不过使用了泛型通配符?
        //同时这也印证了泛型通配符章节所描述的，?是一种类型实参，可以看做为Number等所有类的父类
        public void showKeyValue2(Generic<?> obj) {
            System.out.println("泛型测试  key value is " + obj.getKey());
        }

        /**
         * 这个方法是有问题的，编译器会为我们提示错误信息："UnKnown class 'E' "
         * 虽然我们声明了<T>,也表明了这是一个可以处理泛型的类型的泛型方法。
         * 但是只声明了泛型类型T，并未声明泛型类型E，因此编译器并不知道该如何处理E这个类型。
         public <T> T showKeyName(Generic<E> container){
         ...
         }
         */

    }


    /**
     * 泛型方法的基本介绍
     *
     * @param tClass 传入的泛型实参
     * @return T 返回值为T类型
     * 说明：
     * 1）public 与 返回值中间<T>非常重要，可以理解为声明此方法为泛型方法。
     * 2）只有声明了<T>的方法才是泛型方法，泛型类中的使用了泛型的成员方法并不是泛型方法。
     * 3）<T>表明该方法将使用泛型类型T，此时才可以在方法中使用泛型类型T。
     * 4）与泛型类的定义一样，此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型。
     */
    public static <T> T genericMethod(Class<T> tClass) throws InstantiationException,
            IllegalAccessException {
        T instance = tClass.newInstance();
        return instance;
    }

    /**
     * 测试代码，执订传入类型实参是通配符 ?
     *
     * @param obj
     */
    public static void showKeyValue1(Generic<?> obj) {
        System.out.println("泛型测试  key value is " + obj.getKey());
    }

    /**
     * 测试代码，执订传入类型实参是Number
     *
     * @param obj
     */
    public static void showKeyValue(Generic<Number> obj) {
        System.out.println("泛型测试 key value is " + obj.getKey());
    }

    /**
     * 传入泛型实参时：
     * 定义一个生产器实现这个接口,虽然我们只创建了一个泛型接口Generator<T>
     * 但是我们可以为T传入无数个实参，形成无数种类型的Generator接口。
     * 在实现类实现泛型接口时，如已将泛型类型传入实参类型，则所有使用泛型的地方都要替换成传入的实参类型
     * 即：Generator<T>，public T next();中的的T都要替换成传入的String类型。
     */
    public class FruitGenerator implements Generator<String> {

        private String[] fruits = new String[]{"Apple", "Banana", "Pear"};

        @Override
        public String next() {
            Random rand = new Random();
            return fruits[rand.nextInt(3)];
        }
    }


    /**
     * 未传入泛型实参时，与泛型类的定义相同，在声明类的时候，需将泛型的声明也一起加到类中
     * 即：class FruitGenerator<T> implements Generator<T>{
     * 如果不声明泛型，如：class FruitGenerator implements Generator<T>，编译器会报错："Unknown class"
     */
    class FruitGenerator1<T> implements Generator<T> {
        @Override
        public T next() {
            return null;
        }
    }

    /**
     * 定义一个泛型接口
     */
    public interface Generator<T> {
        public T next();
    }

    /**
     * 此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型
     * 在实例化泛型类时，必须指定T的具体类型
     */
    public static class Generic<T> {
        //key这个成员变量的类型为T,T的类型由外部指定
        private T key;

        public Generic(T key) { //泛型构造方法形参key的类型也为T，T的类型由外部指定
            this.key = key;
        }

        public T getKey() { //泛型方法getKey的返回值类型为T，T的类型由外部指定
            return key;
        }
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
