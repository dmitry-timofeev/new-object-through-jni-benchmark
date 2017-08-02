CPP=g++
TARGET ?= libctorbench.so
SRCS := $(shell find . -name *.cc -or -name *.c -or -name *.cpp -or -name *.s)
OBJS := $(addsuffix .o,$(basename $(SRCS)))
INCLUDE_FLAGS := -I. -I$(JDK_HOME)/include -I$(JDK_HOME)/include/linux
CPPFLAGS ?= $(INCLUDE_FLAGS) --std=c++11 -Wall -Werror -fpic -O3

all: $(TARGET)

$(TARGET): $(OBJS)
	$(CPP) -shared -o $(TARGET) $^

%.o: %.cc
	$(CPP) -c -o $@ $< $(CPPFLAGS)

.PHONY: clean
clean:
	rm -f *.o

.PHONY: dist-clean
dist-clean: clean
	rm -f $(TARGET)
