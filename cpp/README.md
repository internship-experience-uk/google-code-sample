# Youtube Challenge - C++
The C++ Youtube Challenge uses C++11, CMake and GTest.

If you need to install CMake, follow the instructions in [this](https://cmake.org/install/) link.

If you need to install GTest, follow the instructions in [this](https://google.github.io/googletest/quickstart-cmake.html) link.

## Setting up
You can write code in any editor you'd like. However, different editors have 
different ways of dealing with c++ code, so in case of doubt we recommend 
you run the code and tests from the command line as shown  below.

The below commands assume you are located in the root folder.

## Running and Testing from the Commandline
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
cd build && ctest --output-on-failure
```