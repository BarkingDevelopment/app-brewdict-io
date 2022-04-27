#include <jni.h>
#include "predict.h"

extern "C"
JNIEXPORT jfloat JNICALL
Java_io_brewdict_application_android_Brewdict_predict(
        JNIEnv *env,
        jobject thiz,
        jfloat og,
        jfloat start_temp,
        jfloat time,
        jfloat sg,
        jfloat current_temp,
        jfloat mean_temp
) {
    float data[6] = { og, start_temp, time, sg, current_temp, mean_temp };

    return predict(reinterpret_cast<const double *>(data));
}