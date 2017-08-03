#include <jni.h>

#include "org_jnitest_NativeMapEntries.h"

extern "C" {

/*
 * Class:     org_jnitest_NativeMapEntries
 * Method:    getKey
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_jnitest_NativeMapEntries_getKey
  (JNIEnv *, jclass, jint key) {
  return key;
}

/*
 * Class:     org_jnitest_NativeMapEntries
 * Method:    getValue
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_org_jnitest_NativeMapEntries_getValue
  (JNIEnv *, jclass, jdouble value) {
  return value;
}
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
static jclass globalObjectClassRef = nullptr;
static jmethodID entryCtorId = nullptr;
static jmethodID objectCtorId = nullptr;

/*
 * Class:     org_jnitest_NativeMapEntries
 * Method:    initCreateEntry
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_jnitest_NativeMapEntries_initCreateObjects
  (JNIEnv *env, jclass) {
  jclass entryClass = env->FindClass("org/jnitest/MapEntry");
  globalEntryClassRef = static_cast<jclass>(env->NewGlobalRef(entryClass));
  entryCtorId = env->GetMethodID(entryClass, "<init>", "(ID)V");

  jclass objectClass = env->FindClass("java/lang/Object");
  globalObjectClassRef = static_cast<jclass>(env->NewGlobalRef(objectClass));
  objectCtorId = env->GetMethodID(objectClass, "<init>", "()V");
}

/*
 * Class:     org_jnitest_NativeMapEntries
 * Method:    createObjectCached
 * Signature: ()Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_org_jnitest_NativeMapEntries_createObjectCached
  (JNIEnv *env, jclass) {
  return env->NewObject(globalObjectClassRef, objectCtorId);
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
JNIEXPORT void JNICALL Java_org_jnitest_NativeMapEntries_teardownCreateObjects
  (JNIEnv *env, jclass) {
  env->DeleteGlobalRef(globalEntryClassRef);
  globalEntryClassRef = nullptr;
  entryCtorId = nullptr;
}

}
