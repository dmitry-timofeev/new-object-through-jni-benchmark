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
JniBenchmark.baseline                    thrpt   50  153.299 ± 1.264  ops/us
JniBenchmark.createEntryCached           thrpt   50    4.247 ± 0.024  ops/us
JniBenchmark.createEntrySimple           thrpt   50    1.527 ± 0.012  ops/us
JniBenchmark.createObjectCached          thrpt   50    4.839 ± 0.016  ops/us
JniBenchmark.getArgumentsFromNativeCode  thrpt   50   57.672 ± 0.241  ops/us

```

Average time per operation (the lower the better):
```
Benchmark                   Mode  Cnt    Score    Error   Units
JniBenchmark.baseline                     avgt   50    0.008 ± 0.002   us/op
JniBenchmark.createEntryCached            avgt   50    0.235 ± 0.001   us/op
JniBenchmark.createEntrySimple            avgt   50    0.671 ± 0.024   us/op
JniBenchmark.createObjectCached           avgt   50    0.207 ± 0.001   us/op
JniBenchmark.getArgumentsFromNativeCode   avgt   50    0.018 ± 0.001   us/op

```