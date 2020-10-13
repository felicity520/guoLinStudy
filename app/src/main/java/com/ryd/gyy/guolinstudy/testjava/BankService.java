package com.ryd.gyy.guolinstudy.testjava;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * 定义限额注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface BankTransferMoney {
    double maxMoney() default 10000;
}

/**
 * 转账处理业务类
 */
public class BankService {
    /**
     * @param money 转账金额
     */
    @BankTransferMoney(maxMoney = 9000)
    public static void TransferMoney(double money) {
        System.out.println(processAnnotationMoney(money));

    }

    private static String processAnnotationMoney(double money) {
        try {
            Method transferMoney = BankService.class.getDeclaredMethod("TransferMoney", double.class);
            boolean annotationPresent = transferMoney.isAnnotationPresent(BankTransferMoney.class);
            if (annotationPresent) {
                BankTransferMoney annotation = transferMoney.getAnnotation(BankTransferMoney.class);
                double l = annotation.maxMoney();
                if (money > l) {
                    return "转账金额大于限额，转账失败";
                } else {
                    return "转账金额为:" + money + "，转账成功";
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return "转账处理失败";
    }

    public static void main(String[] args) {
        TransferMoney(10000);
    }
}
