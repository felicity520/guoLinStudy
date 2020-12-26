package com.ryd.gyy.guolinstudy.testjava;

public class testClass {

    public static void main(String[] args) {
        //代码执行顺序：
        //先初始化父类（静态方法，代码块）：5，1
        //然后初始化子类 （静态方法，代码块）：10，6
        //super()（父类非静态变量，父类的非静态代码块，父类的构造方法）
        //实例化子类（非静态变量，非静态代码块，构造方法）：9,8,7
        //-------------------分割线-------------------
        //(5)
        //(1)
        //(10)
        //(6)
        //(9)
        //(3)
        //(2)
        //(9)
        //(8)
        //(7)
//        Son s1 = new Son();
        System.out.println("gyy----");
//        Son s2 = new Son();


        //下面的是学习拷贝的代码------
        //1、复制不是拷贝----
//        User usera = new User();
//        usera.setAge(20);
//        User userb = usera;
//        //修改usera的年龄
//        usera.setAge(22);
//        //输出userb的年龄，会发现也是22
//        //其实=并不是拷贝了对象，而是直接使用了usera对象的引用地址，也就是说usera和userb其实是一个人，没有拷贝。
//        System.out.println(userb.getAge() + "");

        //2、浅拷贝1--------
//        User2 usera = new User2();
//        usera.setAge(20);
//        User2 userb = (User2) usera.clone();
//        //修改usera的年龄
//        usera.setAge(22);
//        //输出userb的年龄，还是20，证明有拷贝成功
//        System.out.println(userb.getAge() + "");

        //3、浅拷贝2--------
//        User3 usera = new User3();
//        Favorite favorite = new Favorite();
//        favorite.setColor("蓝色");
//        usera.setAge(20);
//        usera.setFavorite(favorite);
//        User3 userb = (User3) usera.clone();
//        //修改usera的年龄和爱好颜色
//        usera.setAge(22);
//        usera.getFavorite().setColor("红色");
//        //输出userb的年龄，还是20 证明拷贝成功
//        System.out.println(userb.getAge() + "");
//        //输出userb的爱好，变成了红色！！证明只拷贝了引用数据类型
//        System.out.println(userb.getFavorite().getColor() + "");

        //3、深拷贝--------
        User4 usera = new User4();
        Favorite2 favorite = new Favorite2();
        favorite.setColor("蓝色");
        usera.setAge(20);
        usera.setFavorite(favorite);
        User4 userb = (User4) usera.clone();
        //修改usera的年龄和爱好颜色
        usera.setAge(22);
        usera.getFavorite().setColor("红色");
        //输出userb的年龄，还是11
        System.out.println(userb.getAge() + "");
        //输出userb的爱好，还是蓝色
        System.out.println(userb.getFavorite().getColor() + "");
    }

    public static class Father {
        private int i = test();
        //静态变量定义在静态代码块的前面，因为两个的执行是会根据代码编写的顺序来决定的。
        //结果就是先执行5执行1
        private static int j = method();

        static {
            System.out.println("(1)");
        }

        // private static int j = method();
        //加入定义放在static{}之后，那就是先执行1再执行5

        Father() {
            System.out.println("(2)");
        }

        {
            System.out.println("(3)");
        }

        public int test() {
            System.out.println("(4)");
            return 1;
        }

        public static int method() {
            System.out.println("(5)");
            return 1;
        }
    }

    public static class Son extends Father {
        private int i = test();
        private static int j = method();

        static {
            System.out.println("(6)");
        }

        Son() {
            System.out.println("(7)");
        }

        {
            System.out.println("(8)");
        }

        //父类非静态变量i=test()，正常应该执行父类test方法，就是打印4，但是子类重写了这个方法，所以会执行到子类的test方法。
        //
        //主要就是子类重写了方法，那么会执行到子类这个方法，如果调用了super()才会又调用父类那个方法
        public int test() {
//            super();
            System.out.println("(9)");
            return 1;
        }

        public static int method() {
            System.out.println("(10)");
            return 1;
        }
    }

}
