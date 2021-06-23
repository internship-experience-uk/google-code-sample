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
include(GoogleTest)

if(MSVC)
  add_compile_options(/Wall)
else()
  add_compile_options(
      -Wall
      -Wextra
      -pedantic
      # Below is to avoid unnecessary warnings before code is implemented.
      -Wno-unused-parameter)
endif()

file(COPY src/videos.txt DESTINATION src/)

add_library(youtube_lib
    src/commandparser.cpp
    src/commandparser.h
    src/helper.cpp
    src/helper.h
    src/video.cpp
    src/video.h
    src/videolibrary.cpp
    src/videolibrary.h
    src/videoplayer.cpp
    src/videoplayer.h
    src/videoplaylist.h
    src/videoplaylist.cpp)

add_executable(youtube src/main.cpp)
target_link_libraries(youtube youtube_lib)

enable_testing()

add_executable(part1_test test/part1_test.cpp)
target_link_libraries(part1_test youtube_lib gmock gtest gtest_main)
gtest_discover_tests(part1_test)

add_executable(part2_test test/part2_test.cpp)
target_link_libraries(part2_test youtube_lib gmock gtest gtest_main)
gtest_discover_tests(part2_test)

add_executable(part3_test test/part3_test.cpp)
target_link_libraries(part3_test youtube_lib gmock gtest gtest_main)
gtest_discover_tests(part3_test)

add_executable(part4_test test/part4_test.cpp)
target_link_libraries(part4_test youtube_lib gmock gtest gtest_main)
gtest_discover_tests(part4_test)

add_executable(videolibrary_test test/videolibrary_test.cpp)
target_link_libraries(videolibrary_test youtube_lib gmock gtest gtest_main)
gtest_discover_tests(videolibrary_test)
