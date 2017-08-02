# JNI test
A simple benchmark that tells how much time it takes to create a Java object
when you (a) have to look up the class object and the constructor id;
(b) already have them.

## How to build
```$bash
$ mvn package  # Build the benchmark and Java code under test
$ cd src/main/cpp
$ make  # Build the definitions of native methods
$ cd -
```

## How to run
```$bash
$ java -Djava.library.path=src/main/cpp/ -jar target/benchmarks.jar -wi 5 -i 10 -f 4
```

## Results from my laptop
Throughput (the higher the better):
```
Benchmark                   Mode  Cnt    Score    Error   Units
JniBenchmark.baseline      thrpt   40  148.235 ±  2.301  ops/us
JniBenchmark.createCached  thrpt   40    4.236 ±  0.021  ops/us
JniBenchmark.createSimple  thrpt   40    1.497 ±  0.011  ops/us
```

Average time per operation (the lower the better):
```
Benchmark                   Mode  Cnt    Score    Error   Units
JniBenchmark.baseline       avgt   40    0.007 ±  0.001   us/op
JniBenchmark.createCached   avgt   40    0.244 ±  0.010   us/op
JniBenchmark.createSimple   avgt   40    0.668 ±  0.007   us/op
```