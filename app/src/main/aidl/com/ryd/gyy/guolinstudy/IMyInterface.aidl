// IMyInterface.aidl
package com.ryd.gyy.guolinstudy;

// Declare any non-default types here with import statements

//https://www.jianshu.com/p/4e38cdc016c9
//学习aidl的文章，很不错
//https://www.jianshu.com/p/af8991c83fcb
//Messenger的使用和理解
interface IMyInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    String getInfor(String s);
}


