#include <gmock/gmock.h>
#include <gtest/gtest.h>

#include "../src/videoplayer.h"

using ::testing::ContainsRegex;
using ::testing::HasSubstr;

TEST(Part1, numberOfVideos) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.numberOfVideos();
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("5 videos in the library"));
}

TEST(Part1, showAllVideos) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.showAllVideos();
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Here's a list of all available videos:"));
  EXPECT_THAT(output,
              HasSubstr("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
  EXPECT_THAT(
      output,
      HasSubstr("Another Cat Video (another_cat_video_id) [#cat #animal]"));
  EXPECT_THAT(output,
              HasSubstr("Funny Dogs (funny_dogs_video_id) [#dog #animal]"));
  EXPECT_THAT(
      output,
      HasSubstr("Life at Google (life_at_google_video_id) [#google #career]"));
  EXPECT_THAT(output, HasSubstr("Video about nothing (nothing_video_id) []"));
}

TEST(Part1, playVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Playing video: Amazing Cats"));
}

TEST(Part1, playVideoNonExistent) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("some_other_video_that_doesnt_exist");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Cannot play video: Video does not exist"));
}

TEST(Part1, playVideoStopPrevious) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.playVideo("funny_dogs_video_id");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Stopping video: Amazing Cats"));
  EXPECT_THAT(output, HasSubstr("Playing video: Funny Dogs"));
}

TEST(Part1, playVideoDontStopPreviousIfNonExistent) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.playVideo("some_other_video");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, Not(HasSubstr("Stopping video: Amazing Cats")));
  EXPECT_THAT(output, HasSubstr("Cannot play video: Video does not exist"));
}

TEST(Part1, stopVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.stopVideo();
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Playing video: Amazing Cats"));
  EXPECT_THAT(output, HasSubstr("Stopping video: Amazing Cats"));
}

TEST(Part1, stopVideoTwice) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.stopVideo();
  videoPlayer.stopVideo();
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Playing video: Amazing Cats"));
  EXPECT_THAT(output, HasSubstr("Stopping video: Amazing Cats"));
  EXPECT_THAT(output,
              HasSubstr("Cannot stop video: No video is currently playing"));
}

TEST(Part1, stopVideoNothingPlaying) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.stopVideo();
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output,
              HasSubstr("Cannot stop video: No video is currently playing"));
}

TEST(Part1, playRandomVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playRandomVideo();
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(
      output,
      ContainsRegex("Playing video: (Amazing Cats|Another Cat Video|Funny "
                    "Dogs|Life at Google|Video about nothing)"));
}

TEST(Part1, playRandomVideoStopsPreviousVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.playRandomVideo();
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Stopping video: Amazing Cats"));
  EXPECT_THAT(
      output,
      ContainsRegex("Playing video: (Amazing Cats|Another Cat Video|Funny "
                    "Dogs|Life at Google|Video about nothing)"));
}

TEST(Part1, showPlaying) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.showPlaying();
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Playing video: Amazing Cats"));
  EXPECT_THAT(output, HasSubstr("Currently playing: Amazing Cats "
                                "(amazing_cats_video_id) [#cat #animal]"));
}

TEST(Part1, showNothingPlaying) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.showPlaying();
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("No video is currently playing"));
}

TEST(Part1, pauseVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.pauseVideo();
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Playing video: Amazing Cats"));
  EXPECT_THAT(output, HasSubstr("Pausing video: Amazing Cats"));
}

TEST(Part1, pauseVideoShowVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.pauseVideo();
  videoPlayer.showPlaying();
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Playing video: Amazing Cats"));
  EXPECT_THAT(output, HasSubstr("Pausing video: Amazing Cats"));
  EXPECT_THAT(output,
              HasSubstr("Currently playing: Amazing Cats "
                        "(amazing_cats_video_id) [#cat #animal] - PAUSED"));
}

TEST(Part1, pauseVideoPlayVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.pauseVideo();
  videoPlayer.playVideo("amazing_cats_video_id")
  videoPlayer.showPlaying();
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Playing video: Amazing Cats"));
  EXPECT_THAT(output, HasSubstr("Pausing video: Amazing Cats"));
  EXPECT_THAT(output, Not(HasSubstr("PAUSED")));
}

TEST(Part1, pauseVideoNothingPlaying) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.pauseVideo();
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output,
              HasSubstr("Cannot pause video: No video is currently playing"));
}

TEST(Part1, continueVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.pauseVideo();
  videoPlayer.continueVideo();
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Playing video: Amazing Cats"));
  EXPECT_THAT(output, HasSubstr("Pausing video: Amazing Cats"));
  EXPECT_THAT(output, HasSubstr("Continuing video: Amazing Cats"));
}

TEST(Part1, continueVideoNotPaused) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.continueVideo();
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Cannot continue video: Video is not paused"));
}

TEST(Part1, continueVideoNothingPlaying) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.continueVideo();
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(
      output,
      HasSubstr("Cannot continue video: No video is currently playing"));
}
