#include <gmock/gmock.h>
#include <gtest/gtest.h>

#include "../src/videoplayer.h"

using ::testing::HasSubstr;
using ::testing::MatchesRegex;

TEST(Part3, searchVideosWithNoAnswer) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  std::streambuf* orig = std::cin.rdbuf();
  std::istringstream input("No");
  std::cin.rdbuf(input.rdbuf());
  videoPlayer.searchVideos("cat");
  std::cin.rdbuf(orig);
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Here are the results for cat:"));
  EXPECT_THAT(output, HasSubstr("1) Amazing Cats (amazing_cats_video_id)"));
  EXPECT_THAT(output, HasSubstr("2) Another Cat Video (another_cat_video_id)"));
  EXPECT_THAT(output, HasSubstr("Would you like to play any of the above? If "
                                "yes, specify the number of the video."));
  EXPECT_THAT(
      output,
      HasSubstr(
          "If your answer is not a valid number, we will assume it's a no."));
  EXPECT_THAT(output, Not(HasSubstr("Playing video")));
}

TEST(Part3, searchVideosAndPlayAnswer) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  std::streambuf* orig = std::cin.rdbuf();
  std::istringstream input("2");
  std::cin.rdbuf(input.rdbuf());
  videoPlayer.searchVideos("cat");
  std::cin.rdbuf(orig);
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Here are the results for cat:"));
  EXPECT_THAT(output, HasSubstr("1) Amazing Cats (amazing_cats_video_id)"));
  EXPECT_THAT(output, HasSubstr("2) Another Cat Video (another_cat_video_id)"));
  EXPECT_THAT(output, HasSubstr("Would you like to play any of the above? If "
                                "yes, specify the number of the video."));
  EXPECT_THAT(
      output,
      HasSubstr(
          "If your answer is not a valid number, we will assume it's a no."));
  EXPECT_THAT(output, HasSubstr("Playing video: Another Cat Video"));
}

TEST(Part3, searchVideosAnswerOutOfBounds) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  std::streambuf* orig = std::cin.rdbuf();
  std::istringstream input("5");
  std::cin.rdbuf(input.rdbuf());
  videoPlayer.searchVideos("cat");
  std::cin.rdbuf(orig);
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Here are the results for cat:"));
  EXPECT_THAT(output, HasSubstr("1) Amazing Cats (amazing_cats_video_id)"));
  EXPECT_THAT(output, HasSubstr("2) Another Cat Video (another_cat_video_id)"));
  EXPECT_THAT(output, HasSubstr("Would you like to play any of the above? If "
                                "yes, specify the number of the video."));
  EXPECT_THAT(
      output,
      HasSubstr(
          "If your answer is not a valid number, we will assume it's a no."));
  EXPECT_THAT(output, Not(HasSubstr("Playing video")));
}

TEST(Part3, searchVideosInvalidNumber) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  std::streambuf* orig = std::cin.rdbuf();
  std::istringstream input("ab3g");
  std::cin.rdbuf(input.rdbuf());
  videoPlayer.searchVideos("cat");
  std::cin.rdbuf(orig);
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Here are the results for cat:"));
  EXPECT_THAT(output, HasSubstr("1) Amazing Cats (amazing_cats_video_id)"));
  EXPECT_THAT(output, HasSubstr("2) Another Cat Video (another_cat_video_id)"));
  EXPECT_THAT(output, HasSubstr("Would you like to play any of the above? If "
                                "yes, specify the number of the video."));
  EXPECT_THAT(
      output,
      HasSubstr(
          "If your answer is not a valid number, we will assume it's a no."));
  EXPECT_THAT(output, Not(HasSubstr("Playing video")));
}

TEST(Part3, searchVideosNoResults) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.searchVideos("blah");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("No search results for blah"));
}

TEST(Part3, searchVideosWithTagNoAnswer) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  std::streambuf* orig = std::cin.rdbuf();
  std::istringstream input("no");
  std::cin.rdbuf(input.rdbuf());
  videoPlayer.searchVideosWithTag("#cat");
  std::cin.rdbuf(orig);
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Here are the results for #cat:"));
  EXPECT_THAT(output, HasSubstr("1) Amazing Cats (amazing_cats_video_id)"));
  EXPECT_THAT(output, HasSubstr("2) Another Cat Video (another_cat_video_id)"));
  EXPECT_THAT(output, HasSubstr("Would you like to play any of the above? If "
                                "yes, specify the number of the video."));
  EXPECT_THAT(
      output,
      HasSubstr(
          "If your answer is not a valid number, we will assume it's a no."));
  EXPECT_THAT(output, Not(HasSubstr("Playing video")));
}

TEST(Part3, searchVideosWithTagPlayAnswer) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  std::streambuf* orig = std::cin.rdbuf();
  std::istringstream input("1");
  std::cin.rdbuf(input.rdbuf());
  videoPlayer.searchVideosWithTag("#cat");
  std::cin.rdbuf(orig);
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Here are the results for #cat:"));
  EXPECT_THAT(output, HasSubstr("1) Amazing Cats (amazing_cats_video_id)"));
  EXPECT_THAT(output, HasSubstr("2) Another Cat Video (another_cat_video_id)"));
  EXPECT_THAT(output, HasSubstr("Would you like to play any of the above? If "
                                "yes, specify the number of the video."));
  EXPECT_THAT(
      output,
      HasSubstr(
          "If your answer is not a valid number, we will assume it's a no."));
  EXPECT_THAT(output, HasSubstr("Playing video: Amazing Cats"));
}

TEST(Part3, searchVideosWithTagAnswerOutOfBounds) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  std::streambuf* orig = std::cin.rdbuf();
  std::istringstream input("5");
  std::cin.rdbuf(input.rdbuf());
  videoPlayer.searchVideosWithTag("#cat");
  std::cin.rdbuf(orig);
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Here are the results for #cat:"));
  EXPECT_THAT(output, HasSubstr("1) Amazing Cats (amazing_cats_video_id)"));
  EXPECT_THAT(output, HasSubstr("2) Another Cat Video (another_cat_video_id)"));
  EXPECT_THAT(output, HasSubstr("Would you like to play any of the above? If "
                                "yes, specify the number of the video."));
  EXPECT_THAT(
      output,
      HasSubstr(
          "If your answer is not a valid number, we will assume it's a no."));
  EXPECT_THAT(output, Not(HasSubstr("Playing video")));
}

TEST(Part3, searchVideosWithTagNoResults) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.searchVideosWithTag("#blah");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("No search results for #blah"));
}