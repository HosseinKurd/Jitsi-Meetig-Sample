#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_modularization_libraries_dataSource_repository_remote_baseClasses_BaseAPI_getClientId(
        JNIEnv *env,
        jobject /* this*/) {
    std::string clientId = "sampleClient";

    return env->NewStringUTF(clientId.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_modularization_libraries_dataSource_repository_remote_baseClasses_BaseAPI_getSecretId(
        JNIEnv *env,
        jobject /* this*/) {
    std::string secretId = "sampleSecret";

    return env->NewStringUTF(secretId.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_modularization_libraries_dataSource_repository_remote_baseClasses_BaseAPI_getUserName(
        JNIEnv *env,
        jobject /* this*/) {
    std::string userName = "vahid";

    return env->NewStringUTF(userName.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_modularization_libraries_dataSource_repository_remote_baseClasses_BaseAPI_getPassword(
        JNIEnv *env,
        jobject /* this*/) {
    std::string password = "vahid12345";

    return env->NewStringUTF(password.c_str());
}


extern "C"
JNIEXPORT jstring JNICALL
Java_modularization_libraries_dataSource_repository_remote_baseClasses_BaseAPI_getAuthName(
        JNIEnv *env,
        jobject /* this*/) {
    std::string authName = "ARIO_AUTH";

    return env->NewStringUTF(authName.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_modularization_libraries_dataSource_repository_remote_baseClasses_BaseAPI_getBaseUrl(
        JNIEnv *env,
        jobject /* this*/) {
    std::string baseUrl = "https://ario.vaslapp.com/";

    return env->NewStringUTF(baseUrl.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_modularization_libraries_dataSource_repository_remote_baseClasses_BaseAPI_getLanguage(
        JNIEnv *env,
        jobject /* this*/) {
    std::string language = "fa";

    return env->NewStringUTF(language.c_str());
}