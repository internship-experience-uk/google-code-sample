#include <gmock/gmock.h>
#include <gtest/gtest.h>

#include "../src/videoplayer.h"
#include "../src/helper.h"

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
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 6);
  EXPECT_THAT(commandOutput[0], HasSubstr("Here's a list of all available videos:"));
  EXPECT_THAT(commandOutput[1],
              HasSubstr("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
  EXPECT_THAT(
      commandOutput[2],
      HasSubstr("Another Cat Video (another_cat_video_id) [#cat #animal]"));
  EXPECT_THAT(commandOutput[3],
              HasSubstr("Funny Dogs (funny_dogs_video_id) [#dog #animal]"));
  EXPECT_THAT(
      commandOutput[4],
      HasSubstr("Life at Google (life_at_google_video_id) [#google #career]"));
  EXPECT_THAT(commandOutput[5], HasSubstr("Video about nothing (nothing_video_id) []"));
}

TEST(Part1, playVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 1);
  EXPECT_THAT(commandOutput[0], HasSubstr("Playing video: Amazing Cats"));
}

TEST(Part1, playVideoNonExistent) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("some_other_video_that_doesnt_exist");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 1);
  EXPECT_THAT(commandOutput[0], HasSubstr("Cannot play video: Video does not exist"));
}

TEST(Part1, playVideoStopPrevious) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.playVideo("funny_dogs_video_id");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 3);
  EXPECT_THAT(commandOutput[0], HasSubstr("Playing video: Amazing Cats"));
  EXPECT_THAT(commandOutput[1], HasSubstr("Stopping video: Amazing Cats"));
  EXPECT_THAT(commandOutput[2], HasSubstr("Playing video: Funny Dogs"));
}

TEST(Part1, playVideoDontStopPreviousIfNonExistent) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.playVideo("some_other_video");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 2);
  EXPECT_THAT(commandOutput[0], Not(HasSubstr("Stopping video: Amazing Cats")));
  EXPECT_THAT(commandOutput[1], HasSubstr("Cannot play video: Video does not exist"));
}

TEST(Part1, stopVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.stopVideo();
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 2);
  EXPECT_THAT(commandOutput[0], HasSubstr("Playing video: Amazing Cats"));
  EXPECT_THAT(commandOutput[1], HasSubstr("Stopping video: Amazing Cats"));
}

TEST(Part1, stopVideoTwice) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.stopVideo();
  videoPlayer.stopVideo();
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 3);
  EXPECT_THAT(commandOutput[0], HasSubstr("Playing video: Amazing Cats"));
  EXPECT_THAT(commandOutput[1], HasSubstr("Stopping video: Amazing Cats"));
  EXPECT_THAT(commandOutput[2],
              HasSubstr("Cannot stop video: No video is currently playing"));
}

TEST(Part1, stopVideoNothingPlaying) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.stopVideo();
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 1);
  EXPECT_THAT(commandOutput[0],
              HasSubstr("Cannot stop video: No video is currently playing"));
}

TEST(Part1, playRandomVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playRandomVideo();
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 1);
  EXPECT_THAT(
      commandOutput[0],
      ContainsRegex("Playing video: (Amazing Cats|Another Cat Video|Funny "
                    "Dogs|Life at Google|Video about nothing)"));
}

TEST(Part1, playRandomVideoStopsPreviousVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.playRandomVideo();
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 3);
  EXPECT_THAT(commandOutput[0], HasSubstr("Playing video: Amazing Cats"));
  EXPECT_THAT(commandOutput[1], HasSubstr("Stopping video: Amazing Cats"));
  EXPECT_THAT(
      commandOutput[2],
      ContainsRegex("Playing video: (Amazing Cats|Another Cat Video|Funny "
                    "Dogs|Life at Google|Video about nothing)"));
}

TEST(Part1, showPlaying) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.showPlaying();
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 2);
  EXPECT_THAT(commandOutput[0], HasSubstr("Playing video: Amazing Cats"));
  EXPECT_THAT(commandOutput[1], HasSubstr("Currently playing: Amazing Cats "
                                "(amazing_cats_video_id) [#cat #animal]"));
}

TEST(Part1, showNothingPlaying) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.showPlaying();
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 1);
  EXPECT_THAT(commandOutput[0], HasSubstr("No video is currently playing"));
}

TEST(Part1, pauseVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.pauseVideo();
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 2);
  EXPECT_THAT(commandOutput[0], HasSubstr("Playing video: Amazing Cats"));
  EXPECT_THAT(commandOutput[1], HasSubstr("Pausing video: Amazing Cats"));
}

TEST(Part1, pauseVideoShowVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.pauseVideo();
  videoPlayer.showPlaying();
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 3);
  EXPECT_THAT(commandOutput[0], HasSubstr("Playing video: Amazing Cats"));
  EXPECT_THAT(commandOutput[1], HasSubstr("Pausing video: Amazing Cats"));
  EXPECT_THAT(commandOutput[2],
              HasSubstr("Currently playing: Amazing Cats "
                        "(amazing_cats_video_id) [#cat #animal] - PAUSED"));
}

TEST(Part1, pauseVideoPlayVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.pauseVideo();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.showPlaying();
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 5);
  EXPECT_THAT(commandOutput[0], HasSubstr("Playing video: Amazing Cats"));
  EXPECT_THAT(commandOutput[1], HasSubstr("Pausing video: Amazing Cats"));
  EXPECT_THAT(commandOutput[2], HasSubstr("Stopping video: Amazing Cats"));
  EXPECT_THAT(commandOutput[3], HasSubstr("Playing video: Amazing Cats"));
  EXPECT_THAT(commandOutput[4],
              HasSubstr("Currently playing: Amazing Cats "
                        "(amazing_cats_video_id) [#cat #animal]"));
  EXPECT_THAT(output, Not(HasSubstr("PAUSED")));
}

TEST(Part1, pauseAlreadyPausedVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.pauseVideo();
  videoPlayer.pauseVideo();
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 3);
  EXPECT_THAT(commandOutput[0], HasSubstr("Playing video: Amazing Cats"));
  EXPECT_THAT(commandOutput[1], HasSubstr("Pausing video: Amazing Cats"));
  EXPECT_THAT(commandOutput[2], HasSubstr("Video already paused: Amazing Cats"));
}

TEST(Part1, pauseVideoNothingPlaying) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.pauseVideo();
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 1);
  EXPECT_THAT(commandOutput[0],
              HasSubstr("Cannot pause video: No video is currently playing"));
}

TEST(Part1, continueVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.pauseVideo();
  videoPlayer.continueVideo();
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 3);
  EXPECT_THAT(commandOutput[0], HasSubstr("Playing video: Amazing Cats"));
  EXPECT_THAT(commandOutput[1], HasSubstr("Pausing video: Amazing Cats"));
  EXPECT_THAT(commandOutput[2], HasSubstr("Continuing video: Amazing Cats"));
}

TEST(Part1, continueVideoNotPaused) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.playVideo("amazing_cats_video_id");
  videoPlayer.continueVideo();
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 2);
  EXPECT_THAT(commandOutput[1], HasSubstr("Cannot continue video: Video is not paused"));
}

TEST(Part1, continueVideoNothingPlaying) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.continueVideo();
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 1);
  EXPECT_THAT(
      commandOutput[0],
      HasSubstr("Cannot continue video: No video is currently playing"));
}
