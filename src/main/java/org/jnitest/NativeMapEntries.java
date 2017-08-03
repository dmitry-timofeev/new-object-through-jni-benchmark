package org.jnitest;

class NativeMapEntries {

  static native int getKey(int key);

  static native double getValue(double value);

  /**
   * Creates a new MapEntry through JNI. The native implementation:
   *   - looks up {@code Class<MapEntry>} by the class FQN
   *   - looks up the constructor id by its signature
   *   - creates an object with the given arguments.
   */
  static native MapEntry createEntrySimple(int key, double value);

  /**
   * Looks up classes needed in benchmarks ({@code Class<MapEntry>, Class<Object>})
   * by the class FQN and the corresponding constructor ids by their signatures,
   * and preserves them in global variables.
   */
  static native void initCreateObjects();

  /**
   * Creates a new Object in and returns a reference to it.
   * <p>Same as {@code new Object()}, but in native code.
   */
  static native Object createObjectCached();

  /**
   * Creates a new MapEntry through JNI, using the global reference to the class
   * and the identifier of its constructor.
   */
  static native MapEntry createEntryCached(int key, double value);

  /**
   * Destroys the global references to the classes used in native code.
   */
  static native void teardownCreateObjects();
}
