#include <gtest/gtest.h>
#include <gmock/gmock.h>
#include "../src/videoplayer.h"

using ::testing::HasSubstr;
using ::testing::MatchesRegex;

TEST(Part1, playVideo)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.playVideo("amazing_cats_video_id");
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Playing video:  Amazing Cats"));
}

TEST(Part1, showPlaying)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.showPlaying();
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Playing video:  Amazing Cats"));
    EXPECT_THAT(output, HasSubstr("Currently playing: Amazing Cats (amazing_cats_video_id)"));
}

TEST(Part1, showNothingPlaying)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.showPlaying();
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Nothing currently playing."));
}

TEST(Part1, playVideoStopCurrent)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.playVideo("funny_dogs_video_id");
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Stopping video: Amazing Cats"));
    EXPECT_THAT(output, HasSubstr("Playing video: Funny Dogs"));
}

TEST(Part1, playRandomVideo)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.playRandomVideo();
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, MatchesRegex("Playing video: (Amazing cats|Another Cat Video|Funny Dogs|Life at Google)\n"));
}

TEST(Part1, stopVideo)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.stopVideo();
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Playing video:  Amazing Cats"));
    EXPECT_THAT(output, HasSubstr("Stopping video: Amazing Cats"));
}

TEST(Part1, stopVideoNothingPlaying)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.stopVideo();
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Nothing is currently playing."));
}

TEST(Part1, pauseVideo)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.pauseVideo();
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Playing video:  Amazing Cats"));
    EXPECT_THAT(output, HasSubstr("Pausing video: Amazing Cats"));
}

TEST(Part1, pauseVideoNothingPlaying)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.pauseVideo();
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Cannot pause video: No video currently playing."));
}

TEST(Part1, continueVideo)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.pauseVideo();
    videoPlayer.continueVideo();
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Playing video:  Amazing Cats"));
    EXPECT_THAT(output, HasSubstr("Pausing video: Amazing Cats"));
    EXPECT_THAT(output, HasSubstr("Continuing video: Amazing Cats"));
}

TEST(Part1, continueVideoNotPaused)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.continueVideo();
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Cannot continue video: Video is currently playing and not paused."));
}

TEST(Part1, continueVideoNothingPlaying)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.continueVideo();
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Cannot continue video: No video currently playing."));
}