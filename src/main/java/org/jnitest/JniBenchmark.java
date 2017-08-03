/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.jnitest;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

/**
 * A benchmark of various approaches to repeatedly creating Java objects from native code:
 * <ul>
 *   <li>baseline #1: Create a MapEntry in Java</li>
 *   <li>baseline #2: Create an Object from native code,
 *       using a global reference to its class object.</li>
 *   <li>Create a MapEntry in Java, but get the constructor arguments from native code</li>
 *   <li>Create a MapEntry in native code, look up the class object
 *       and the constructor id each time.</li>
 *   <li>Create a MapEntry in native code, using a global reference to its class object.</li>
 * </ul>
 */
@State(Scope.Thread)
@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class JniBenchmark {

  static {
    System.loadLibrary("ctorbench");
  }

  private int key = 5;

  private double value = 10.0;

  @Setup(Level.Iteration)
  public void lookupConstructor() {
    NativeMapEntries.initCreateObjects();
  }

  @TearDown(Level.Iteration)
  public void destoryConstructorReference() {
    NativeMapEntries.teardownCreateObjects();
  }

  @Benchmark
  public MapEntry baseline() {
    return new MapEntry(key, value);
  }

  @Benchmark
  public MapEntry createEntryArgumentsFromNativeCode() {
    return new MapEntry(NativeMapEntries.getKey(key),
        NativeMapEntries.getValue(value));
  }

  @Benchmark
  public MapEntry createEntrySimple() {
    MapEntry e = NativeMapEntries.createEntrySimple(key, value);

    assert e.key == key;
    assert e.value == value;
    return e;
  }

  @Benchmark
  public Object createObjectCached() {
    Object o = NativeMapEntries.createObjectCached();
    assert o != null;

    return o;
  }

  @Benchmark
  public MapEntry createEntryCached() {
    MapEntry e = NativeMapEntries.createEntryCached(key, value);

    assert e.key == key;
    assert e.value == value;
    return e;
  }
}
