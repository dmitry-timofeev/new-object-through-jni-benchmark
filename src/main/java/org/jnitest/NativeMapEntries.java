package org.jnitest;

class NativeMapEntries {
  /**
   * Creates a new MapEntry through JNI. The native implementation:
   *   - looks up {@code Class<MapEntry>} by the class FQN
   *   - looks up the constructor id by its signature
   *   - creates an object with the given arguments.
   */
  static native MapEntry createEntrySimple(int key, double value);

  /**
   * Looks up {@code Class<MapEntry>} by the class FQN and the constructor id by its signature,
   * and preserves them in a global variable.
   */
  static native void initCreateEntry();

  /**
   * Creates a new MapEntry through JNI, using the global reference to the class
   * and the identifier of its constructor.
   */
  static native MapEntry createEntryCached(int key, double value);

  /**
   * Destroys the global reference to {@code Class<MapEntry>}.
   */
  static native void teardownCreateEntry();
}
