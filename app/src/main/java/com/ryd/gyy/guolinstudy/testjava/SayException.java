package com.ryd.gyy.guolinstudy.testjava;

public class SayException implements ISay {
    @Override
    public String saySomething() {
        return "something wrong here!";
    }
}
