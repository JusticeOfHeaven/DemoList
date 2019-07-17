#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
//com.hello.study_ffmpeg.FFmpegMainActivity
Java_com_hello_study_1ffmpeg_FFmpegMainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
