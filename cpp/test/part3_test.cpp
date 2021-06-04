#include <gtest/gtest.h>
#include <gmock/gmock.h>
#include "../src/videoplayer.h"

using ::testing::HasSubstr;
using ::testing::MatchesRegex;

TEST(Part3, searchVideos)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.searchVideos("cat");
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Here are the results for cat:"));
    EXPECT_THAT(output, HasSubstr("1) Amazing Cats (amazing_cats_video_id)"));
    EXPECT_THAT(output, HasSubstr("2) Another Cat Video (another_cat_video_id)"));
    EXPECT_THAT(output, HasSubstr("Would you like to play any of the above? If yes, specify the number of the video. If your answer is not a valid number, we will assume it's a no."));
}

TEST(Part3, searchVideosNonexistent)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.searchVideos("blah");
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("No search results for blah"));
}

TEST(Part3, searchVideosWithTag)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.searchVideosWithTag("#cat");
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Here are the results for #cat:"));
    EXPECT_THAT(output, HasSubstr("1) Amazing Cats (amazing_cats_video_id)"));
    EXPECT_THAT(output, HasSubstr("2) Another Cat Video (another_cat_video_id)"));
    EXPECT_THAT(output, HasSubstr("Would you like to play any of the above? If yes, specify the number of the video. If your answer is not a valid number, we will assume it's a no."));
}

TEST(Part3, searchVideosWithTagNonexistent)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.searchVideos("#blah");
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("No search results for #blah"));
}