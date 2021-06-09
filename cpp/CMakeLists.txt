cmake_minimum_required(VERSION 3.14)

set(CMAKE_CXX_STANDARD 11)

project(youtube)

include(FetchContent)
FetchContent_Declare(
  googletest
  # Specify the commit you depend on and update it regularly.
  URL https://github.com/google/googletest/archive/53495a2a7d6ba7e0691a7f3602e9a5324bba6e45.zip
)

set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)
FetchContent_MakeAvailable(googletest)

add_compile_options(
    -Wall
    -Wextra
    -pedantic
    # Below is to avoid unnecessary warnings before code is implemented.
    -Wno-unused-parameter)

add_executable(youtube
    src/commandparser.cpp
    src/helper.cpp
    src/video.cpp
    src/videoplayer.cpp
    src/videolibrary.cpp
    src/main.cpp)   

enable_testing()

add_executable(
  part1_test
  test/part1_test.cpp
  src/videoplayer.cpp
)

add_executable(
  part2_test
  test/part2_test.cpp
  src/videoplayer.cpp
)

add_executable(
  part3_test
  test/part3_test.cpp
  src/videoplayer.cpp
)

add_executable(
  part4_test
  test/part4_test.cpp
  src/videoplayer.cpp
)

add_executable(
  videolibrary_test
  test/videolibrary_test.cpp
  src/videolibrary.cpp
  src/video.cpp
  src/helper.cpp
)

target_link_libraries(
  part1_test
  gmock
  gtest
  gtest_main
)

target_link_libraries(
  part2_test
  gmock
  gtest
  gtest_main
)

target_link_libraries(
  part3_test
  gmock
  gtest
  gtest_main
)

target_link_libraries(
  part4_test
  gmock
  gtest
  gtest_main
)

target_link_libraries(
  videolibrary_test
  gmock
  gtest
  gtest_main
)

include(GoogleTest)
gtest_discover_tests(part1_test)
gtest_discover_tests(part2_test)
gtest_discover_tests(part3_test)
gtest_discover_tests(part4_test)
gtest_discover_tests(videolibrary_test)
