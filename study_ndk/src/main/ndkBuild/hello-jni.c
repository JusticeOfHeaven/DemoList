#include <jni.h>

int test(){
    return 123;
}

//com.study.study_ndk.NdkMainActivity  将点换成 _ ，如果之前存在下划线的，则换成 _1
jint Java_com_study_study_1ndk_NdkMainActivity_nativeTest(){
    return test();
}