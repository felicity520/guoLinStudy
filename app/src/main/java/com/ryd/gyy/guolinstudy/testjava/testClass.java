package com.ryd.gyy.guolinstudy.testjava;

public class testClass {

    public static void main(String[] args) {
        Son s1 = new Son();
        System.out.println("gyy----");
        Son s2 = new Son();
    }

    public static class Father {
        private int i = test();
        private static int j = method();

        static {
            System.out.println("(1)");
        }

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

        public int test() {
            System.out.println("(9)");
            return 1;
        }

        public static int method() {
            System.out.println("(10)");
            return 1;
        }
    }

}
