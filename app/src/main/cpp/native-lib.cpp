#include <jni.h>
#include <string>
#include <android/log.h>


#define TAG "native-lib" // 这个是自定义的LOG的标识
#define logd(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型

extern "C" JNIEXPORT jstring JNICALL
Java_com_ryd_gyy_guolinstudy_Activity_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

void makeCrash() {
    int num1 = 10;
//rk3399:/data/tombstones # ls
//tombstone_00
//此行报错的log会保存到/data/tombstones下
    logd("num3 = %s", num1);
}

extern "C" JNIEXPORT void JNICALL
Java_com_ryd_gyy_guolinstudy_Activity_MainActivity_crash(
        JNIEnv *env,
        jobject /* this */) {
    logd("gyy%s", "报错了-----------");
    makeCrash();
}
