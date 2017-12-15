#include <jni.h>
#include <string>

extern "C"
jstring
Java_cobb_www_lib_seeta_compare_HomeActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
