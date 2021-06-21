# YouTube Challenge - C++

The C++ YouTube Challenge uses C++ 11, CMake and [GTest](https://google.github.io/googletest/).

NOTE: **Please do not edit videos.txt as it will cause tests to break. There is no need to modify this file to complete this challenge.**

## Installing C++ and CMake
You need to install:

- [CMake 3.14+](https://cmake.org/install/) and a build tool like GNU make or Ninja.
- A compiler that supports C++ 11 or higher ([clang](https://clang.llvm.org/get_started.html), gcc/g++, MSVC).

On Debian-based Linux distributions you can install the dependencies with this
command:

```shell
sudo apt install cmake make clang
```

On Windows, you can install the [Microsoft Visual Studio Community
Edition](https://visualstudio.microsoft.com/downloads/) for the compiler and
build tools. Make sure to choose to install CMake as part of this.

On a Mac, you need to go to the App Store and download Xcode for free - it is Apple's IDE - Integrated Development Environment. Without Xcode, you will have no compiler (i.e. clang or gcc or g++) and no build tools, (i.e. make).
After you've installed Xcode you need to install command line tools:

```shell
xcode-select --install
```

For CMake: 

- Download the latest CMake Mac binary distribution here: https://cmake.org/download/.

- Double click the downloaded .dmg file to install it. In the window that pops up, drag the CMake icon into the Application folder.

- Add this line to your .bashrc file: PATH="/Applications/CMake.app/Contents/bin":"$PATH"

- Reload your .bashrc file: source ~/.bashrc

- Verify the latest cmake version is installed: cmake --version

> Note: You can use a higher version of C++ (C++ 17 or C++ 20) if you want:
> Just change the `CMAKE_CXX_STANDARD` in `CMakeLists.txt`!

## Setting up

You can write code in the editor or IDE of your choice. However, different
editors have different ways of dealing with C++ code, so in case of doubt we
recommend you run the code and tests from the command line as shown below.

The below commands assume you are located in the `cpp/` folder of the git
repository.

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

To run a subset of the tests, in this example, all tests of part 1 that contain
`showAllVideos` in their name:

```shell script
./build/part1_test --gtest_filter='*showAllVideos*'
```

> NOTE: Don't forget to rebuild your code ater making changes for testing.
