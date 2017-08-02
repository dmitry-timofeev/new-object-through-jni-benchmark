#include <jni.h>

#include "org_jnitest_NativeMapEntries.h"

extern "C" {

/*
 * Class:     org_jnitest_NativeMapEntries
 * Method:    createEntrySimple
 * Signature: (ID)Lorg/jnitest/MapEntry;
 */
JNIEXPORT jobject JNICALL Java_org_jnitest_NativeMapEntries_createEntrySimple
  (JNIEnv *env, jclass c, jint key, jdouble value) {
  jclass entryClass = env->FindClass("org/jnitest/MapEntry");
  jmethodID ctorId = env->GetMethodID(entryClass, "<init>", "(ID)V");
  return env->NewObject(entryClass, ctorId, key, value);
}

static jclass globalEntryClassRef = nullptr;
static jmethodID entryCtorId = nullptr;

/*
 * Class:     org_jnitest_NativeMapEntries
 * Method:    initCreateEntry
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_jnitest_NativeMapEntries_initCreateEntry
  (JNIEnv *env, jclass) {
  jclass entryClass = env->FindClass("org/jnitest/MapEntry");
  jmethodID ctorId = env->GetMethodID(entryClass, "<init>", "(ID)V");
  globalEntryClassRef = static_cast<jclass>(env->NewGlobalRef(entryClass));
  entryCtorId = ctorId;
}

/*
 * Class:     org_jnitest_NativeMapEntries
 * Method:    createEntryCached
 * Signature: (ID)Lorg/jnitest/MapEntry;
 */
JNIEXPORT jobject JNICALL Java_org_jnitest_NativeMapEntries_createEntryCached
  (JNIEnv *env, jclass, jint key, jdouble value) {
  return env->NewObject(globalEntryClassRef, entryCtorId, key, value);
}

/*
 * Class:     org_jnitest_NativeMapEntries
 * Method:    teardownCreateEntry
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_jnitest_NativeMapEntries_teardownCreateEntry
  (JNIEnv *env, jclass) {
  env->DeleteGlobalRef(globalEntryClassRef);
  globalEntryClassRef = nullptr;
  entryCtorId = nullptr;
}

}
