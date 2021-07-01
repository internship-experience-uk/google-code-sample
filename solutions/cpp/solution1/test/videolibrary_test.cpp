#include "../src/videolibrary.h"

#include <gmock/gmock.h>
#include <gtest/gtest.h>

#include "../src/video.h"

using ::testing::ContainsRegex;
using ::testing::HasSubstr;

TEST(VideoLibrary, VideoLibraryHasAllVideos) {
  VideoLibrary videoLibrary = VideoLibrary();
  EXPECT_EQ(videoLibrary.getVideos().size(), 5);
}

TEST(VideoLibrary, VideoLibraryParsesTagsCorrectly) {
  std::vector<std::string> tags{"#cat", "#animal"};
  VideoLibrary videoLibrary = VideoLibrary();
  const Video *video = videoLibrary.getVideo("amazing_cats_video_id");
  bool result = std::equal(tags.begin(), tags.end(), video->getTags().begin());
  EXPECT_EQ("Amazing Cats", video->getTitle());
  EXPECT_EQ("amazing_cats_video_id", video->getVideoId());
  EXPECT_TRUE(result);
}

TEST(VideoLibrary, testLibraryParsesVideoCorrectlyWithoutTags) {
  VideoLibrary videoLibrary = VideoLibrary();
  const Video *video = videoLibrary.getVideo("nothing_video_id");
  EXPECT_EQ("Video about nothing", video->getTitle());
  EXPECT_EQ("nothing_video_id", video->getVideoId());
  EXPECT_TRUE(video->getTags().empty());
}
