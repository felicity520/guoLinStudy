package com.ryd.gyy.guolinstudy.testjava;

/**
 * 注解的使用
 * 理解：其实就是一种特殊的接口，注解的属性其实就是接口的方法，所以max()后面有括号
 *  * 参考：https://www.jianshu.com/p/9471d6bcf4cf
 * https://blog.csdn.net/heyrian/article/details/80764783
 */
public class AnnoationTest {

    @Main.Test(min = 6, max = 10, description = "用户名长度在6-10个字符之间")
    private String name;

    @Main.Test(min = 6, max = 10, description = "密码长度在6-10个字符之间")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pasdword) {
        this.password = pasdword;
    }
}
