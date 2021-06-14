#include <gmock/gmock.h>
#include <gtest/gtest.h>

#include "../src/videoplayer.h"

using ::testing::HasSubstr;
using ::testing::MatchesRegex;

TEST(Part2, createPlaylist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output,
              HasSubstr("Successfully created new playlist: mY_plaYList"));
}

TEST(Part2, createExistingPlaylist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.createPlaylist("mY_plaYList");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output,
              HasSubstr("Successfully created new playlist: mY_plaYList"));
  EXPECT_THAT(output, HasSubstr("Cannot create playlist: A playlist with the "
                                "same name already exists"));
}

TEST(Part2, addToPlaylist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "amazing_cats_video_id");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output,
              HasSubstr("Successfully created new playlist: mY_plaYList"));
  EXPECT_THAT(output, HasSubstr("Added video to mY_plaYList: Amazing Cats"));
}

TEST(Part2, addToPlaylistAlreadyAdded) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "amazing_cats_video_id");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "amazing_cats_video_id");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output,
              HasSubstr("Successfully created new playlist: mY_plaYList"));
  EXPECT_THAT(output, HasSubstr("Added video to mY_plaYList: Amazing Cats"));
  EXPECT_THAT(
      output,
      HasSubstr("Cannot add video to mY_plaYList: Video already added"));
}

TEST(Part2, addVideoToPlaylistNonExistentVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "amazing_cats_video_id");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "some_other_video_id");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output,
              HasSubstr("Successfully created new playlist: mY_plaYList"));
  EXPECT_THAT(output, HasSubstr("Added video to mY_plaYList: Amazing Cats"));
  EXPECT_THAT(
      output,
      HasSubstr("Cannot add video to mY_plaYList: Video does not exist"));
}

TEST(Part2, addVideoToPlaylistNonExistent) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.addVideoToPlaylist("anotHER_playlist", "amazing_cats_video_id");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(
      output,
      HasSubstr(
          "Cannot add video to anotHER_playlist: Playlist does not exist"));
}

TEST(Part2, showAllPlaylistsNoPlaylistsExist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.showAllPlaylists();
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("No playlists exist yet"));
}

TEST(Part2, showAllPlaylist) {
  VideoPlayer videoPlayer = VideoPlayer();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.createPlaylist("anotHER_playlist");
  testing::internal::CaptureStdout();
  videoPlayer.showAllPlaylists();
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Showing all playlists:"));
  EXPECT_THAT(output, HasSubstr("mY_plaYList"));
  EXPECT_THAT(output, HasSubstr("anotHER_playlist"));
  EXPECT_TRUE(output.find("anotHER_playlist") < output.find("mY_plaYList"));
}

TEST(Part2, showPlaylist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.showPlaylist("mY_plaYList");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "amazing_cats_video_id");
  videoPlayer.showPlaylist("mY_plaYList");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output,
              HasSubstr("Successfully created new playlist: mY_plaYList"));
  EXPECT_THAT(output, HasSubstr("Showing playlist: mY_plaYList"));
  EXPECT_THAT(output, HasSubstr("No videos here yet"));
  EXPECT_THAT(output, HasSubstr("Added video to mY_plaYList: Amazing Cats"));
  EXPECT_THAT(output, HasSubstr("Showing playlist: mY_plaYList"));
  EXPECT_THAT(output,
              HasSubstr("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
}

TEST(Part2, showPlaylistAfterRemoveAVideoFromPlaylistThenReAdd) {
  VideoPlayer videoPlayer = VideoPlayer();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "amazing_cats_video_id");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "life_at_google_video_id");
  videoPlayer.removeFromPlaylist("mY_plaYList", "amazing_cats_video_id");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "amazing_cats_video_id");
  testing::internal::CaptureStdout();
  videoPlayer.showPlaylist("mY_plaYList");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Showing playlist: mY_plaYList"));
  EXPECT_THAT(output,
              HasSubstr("Life at Google (life_at_google_video_id) [#google #career]"));
  EXPECT_THAT(output,
              HasSubstr("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
  EXPECT_TRUE(output.find("Life at Google") < output.find("Amazing Cats"));
}

TEST(Part2, showPlaylistNonExistent) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.showPlaylist("mY_plaYList");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(
      output,
      HasSubstr("Cannot show playlist mY_plaYList: Playlist does not exist"));
}

TEST(Part2, removeFromPlaylist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "amazing_cats_video_id");
  videoPlayer.removeFromPlaylist("mY_plaYList", "amazing_cats_video_id");
  videoPlayer.removeFromPlaylist("mY_plaYList", "amazing_cats_video_id");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output,
              HasSubstr("Successfully created new playlist: mY_plaYList"));
  EXPECT_THAT(output, HasSubstr("Added video to mY_plaYList: Amazing Cats"));
  EXPECT_THAT(output,
              HasSubstr("Removed video from mY_plaYList: Amazing Cats"));
  EXPECT_THAT(
      output,
      HasSubstr(
          "Cannot remove video from mY_plaYList: Video is not in playlist"));
}

TEST(Part2, removeFromPlaylistVideoNotInPlaylist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.removeFromPlaylist("mY_plaYList", "amazing_cats_video_id");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(
      output,
      HasSubstr(
          "Cannot remove video from mY_plaYList: Video is not in playlist"));
}

TEST(Part2, removeFromPlaylistNonexistentVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "amazing_cats_video_id");
  videoPlayer.removeFromPlaylist("mY_plaYList", "some_other_video_id");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output,
              HasSubstr("Successfully created new playlist: mY_plaYList"));
  EXPECT_THAT(output, HasSubstr("Added video to mY_plaYList: Amazing Cats"));
  EXPECT_THAT(
      output,
      HasSubstr("Cannot remove video from mY_plaYList: Video does not exist"));
}

TEST(Part2, removeFromPlaylistNonexistentPlaylist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.removeFromPlaylist("my_cool_playlist", "some_other_video_id");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output, HasSubstr("Cannot remove video from my_cool_playlist: "
                                "Playlist does not exist"));
}

TEST(Part2, clearPlaylist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "amazing_cats_video_id");
  videoPlayer.showPlaylist("mY_plaYList");
  videoPlayer.clearPlaylist("mY_plaYList");
  videoPlayer.showPlaylist("mY_plaYList");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output,
              HasSubstr("Successfully created new playlist: mY_plaYList"));
  EXPECT_THAT(output, HasSubstr("Added video to mY_plaYList: Amazing Cats"));
  EXPECT_THAT(output, HasSubstr("Showing playlist: mY_plaYList"));
  EXPECT_THAT(output,
              HasSubstr("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
  EXPECT_THAT(output,
              HasSubstr("Successfully removed all videos from mY_plaYList"));
  EXPECT_THAT(output, HasSubstr("Showing playlist: mY_plaYList"));
  EXPECT_THAT(output, HasSubstr("No videos here yet"));
}

TEST(Part2, clearPlaylistNonexistent) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.clearPlaylist("mY_plaYList");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(
      output,
      HasSubstr("Cannot clear playlist mY_plaYList: Playlist does not exist"));
}

TEST(Part2, deletePlaylist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.deletePlaylist("mY_plaYList");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(output,
              HasSubstr("Successfully created new playlist: mY_plaYList"));
  EXPECT_THAT(output, HasSubstr("Deleted playlist: mY_plaYList"));
}

TEST(Part2, deletePlaylistNonexistent) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.deletePlaylist("mY_plaYList");
  std::string output = testing::internal::GetCapturedStdout();
  EXPECT_THAT(
      output,
      HasSubstr("Cannot delete playlist mY_plaYList: Playlist does not exist"));
}