# Youtube Challenge - C++
The C++ Youtube Challenge uses C++11, CMake and GTest.

If you need to install CMake, follow the instructions in [this](https://cmake.org/install/) link.

If you need to install GTest, follow the instructions in [this](https://google.github.io/googletest/quickstart-cmake.html) link.

## Setting up
You can write code in any editor you'd like. However, different editors have 
different ways of dealing with c++ code, so in case of doubt we recommend 
you run the code and tests from the command line as shown  below.

The below commands assume you are located in the root folder.

## Running and Testing from the Command line
To build:
```shell script
cmake -S . -B build
```

Followed by:
```shell script
cmake --build build/
```

To run:
```shell script
./build/youtube
```

To run all the tests:
```shell script
 ctest --test-dir build --output-on-failure
```

To run tests for a single Part (after building):
```shell script
./build/part1_test
./build/part2_test
./build/part3_test
./build/part4_test
```

> NOTE: Make sure you update the CMakeLists.txt if you add any new dependencies and don't forget to rebuild your code ater making changes for testing.
