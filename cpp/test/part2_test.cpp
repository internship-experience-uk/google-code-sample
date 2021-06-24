#include <gmock/gmock.h>
#include <gtest/gtest.h>

#include "../src/videoplayer.h"
#include "../src/helper.h"

using ::testing::HasSubstr;
using ::testing::MatchesRegex;

TEST(Part2, createPlaylist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 1);
  EXPECT_THAT(commandOutput[0],
              HasSubstr("Successfully created new playlist: mY_plaYList"));
}

TEST(Part2, createExistingPlaylist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.createPlaylist("MY_PLAYLIST");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 2);
  EXPECT_THAT(commandOutput[0],
              HasSubstr("Successfully created new playlist: mY_plaYList"));
  EXPECT_THAT(commandOutput[1], HasSubstr("Cannot create playlist: A playlist with the "
                                "same name already exists"));
}

TEST(Part2, addToPlaylist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.addVideoToPlaylist("mY_playLIST", "amazing_cats_video_id");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 2);
  EXPECT_THAT(commandOutput[0],
              HasSubstr("Successfully created new playlist: mY_plaYList"));
  EXPECT_THAT(commandOutput[1], HasSubstr("Added video to mY_playLIST: Amazing Cats"));
}

TEST(Part2, addToPlaylistAlreadyAdded) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "amazing_cats_video_id");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "amazing_cats_video_id");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 3);
  EXPECT_THAT(commandOutput[0],
              HasSubstr("Successfully created new playlist: mY_plaYList"));
  EXPECT_THAT(commandOutput[1], HasSubstr("Added video to mY_plaYList: Amazing Cats"));
  EXPECT_THAT(
      commandOutput[2],
      HasSubstr("Cannot add video to mY_plaYList: Video already added"));
}

TEST(Part2, addVideoToPlaylistNonExistentVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "amazing_cats_video_id");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "some_other_video_id");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 3);
  EXPECT_THAT(commandOutput[0],
              HasSubstr("Successfully created new playlist: mY_plaYList"));
  EXPECT_THAT(commandOutput[1], HasSubstr("Added video to mY_plaYList: Amazing Cats"));
  EXPECT_THAT(
      commandOutput[2],
      HasSubstr("Cannot add video to mY_plaYList: Video does not exist"));
}

TEST(Part2, addVideoToPlaylistNonExistent) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.addVideoToPlaylist("anotHER_playlist", "amazing_cats_video_id");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 1);
  EXPECT_THAT(
      commandOutput[0],
      HasSubstr(
          "Cannot add video to anotHER_playlist: Playlist does not exist"));
}

TEST(Part2, addVideoToPlaylistNonExistentNoPlaylistNoVideo) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.addVideoToPlaylist("anotHER_playlist", "video_does_not_exist");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 1);
  EXPECT_THAT(
      commandOutput[0],
      HasSubstr(
          "Cannot add video to anotHER_playlist: Playlist does not exist"));
}

TEST(Part2, showAllPlaylistsNoPlaylistsExist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.showAllPlaylists();
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 1);
  EXPECT_THAT(commandOutput[0], HasSubstr("No playlists exist yet"));
}

TEST(Part2, showAllPlaylist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.createPlaylist("anotHER_playlist");
  videoPlayer.showAllPlaylists();
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 5);
  EXPECT_THAT(commandOutput[2], HasSubstr("Showing all playlists:"));
  EXPECT_THAT(commandOutput[4], HasSubstr("mY_plaYList"));
  EXPECT_THAT(commandOutput[3], HasSubstr("anotHER_playlist"));
}

TEST(Part2, showPlaylist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.showPlaylist("mY_plaYList");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "amazing_cats_video_id");
  videoPlayer.showPlaylist("mY_plaYList");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 6);
  EXPECT_THAT(commandOutput[0],
              HasSubstr("Successfully created new playlist: mY_plaYList"));
  EXPECT_THAT(commandOutput[1], HasSubstr("Showing playlist: mY_plaYList"));
  EXPECT_THAT(commandOutput[2], HasSubstr("No videos here yet"));
  EXPECT_THAT(commandOutput[3], HasSubstr("Added video to mY_plaYList: Amazing Cats"));
  EXPECT_THAT(commandOutput[4], HasSubstr("Showing playlist: mY_plaYList"));
  EXPECT_THAT(commandOutput[5],
              HasSubstr("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
}

TEST(Part2, showPlaylistAfterRemoveAVideoFromPlaylistThenReAdd) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "amazing_cats_video_id");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "life_at_google_video_id");
  videoPlayer.removeFromPlaylist("mY_plaYList", "amazing_cats_video_id");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "amazing_cats_video_id");
  videoPlayer.showPlaylist("mY_plaYList");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 8);
  EXPECT_THAT(commandOutput[5], HasSubstr("Showing playlist: mY_plaYList"));
  EXPECT_THAT(commandOutput[6],
              HasSubstr("Life at Google (life_at_google_video_id) [#google #career]"));
  EXPECT_THAT(commandOutput[7],
              HasSubstr("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
}

TEST(Part2, showPlaylistNonExistent) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.showPlaylist("mY_plaYList");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 1);
  EXPECT_THAT(
      commandOutput[0],
      HasSubstr("Cannot show playlist mY_plaYList: Playlist does not exist"));
}

TEST(Part2, removeFromPlaylist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.addVideoToPlaylist("mY_plaYList", "amazing_cats_video_id");
  videoPlayer.removeFromPlaylist("MY_playlist", "amazing_cats_video_id");
  videoPlayer.removeFromPlaylist("mY_plaYList", "amazing_cats_video_id");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 4);
  EXPECT_THAT(commandOutput[0],
              HasSubstr("Successfully created new playlist: mY_plaYList"));
  EXPECT_THAT(commandOutput[1], HasSubstr("Added video to mY_plaYList: Amazing Cats"));
  EXPECT_THAT(commandOutput[2],
              HasSubstr("Removed video from MY_playlist: Amazing Cats"));
  EXPECT_THAT(
      commandOutput[3],
      HasSubstr(
          "Cannot remove video from mY_plaYList: Video is not in playlist"));
}

TEST(Part2, removeFromPlaylistVideoNotInPlaylist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.removeFromPlaylist("mY_plaYList", "amazing_cats_video_id");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 2);
  EXPECT_THAT(
      commandOutput[1],
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
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 3);
  EXPECT_THAT(commandOutput[0],
              HasSubstr("Successfully created new playlist: mY_plaYList"));
  EXPECT_THAT(commandOutput[1], HasSubstr("Added video to mY_plaYList: Amazing Cats"));
  EXPECT_THAT(
      commandOutput[2],
      HasSubstr("Cannot remove video from mY_plaYList: Video does not exist"));
}

TEST(Part2, removeFromPlaylistNonexistentPlaylist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.removeFromPlaylist("my_cool_playlist", "some_other_video_id");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 1);
  EXPECT_THAT(commandOutput[0], HasSubstr("Cannot remove video from my_cool_playlist: "
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
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 7);
  EXPECT_THAT(commandOutput[0],
              HasSubstr("Successfully created new playlist: mY_plaYList"));
  EXPECT_THAT(commandOutput[1], HasSubstr("Added video to mY_plaYList: Amazing Cats"));
  EXPECT_THAT(commandOutput[2], HasSubstr("Showing playlist: mY_plaYList"));
  EXPECT_THAT(commandOutput[3],
              HasSubstr("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
  EXPECT_THAT(commandOutput[4],
              HasSubstr("Successfully removed all videos from mY_plaYList"));
  EXPECT_THAT(commandOutput[5], HasSubstr("Showing playlist: mY_plaYList"));
  EXPECT_THAT(commandOutput[6], HasSubstr("No videos here yet"));
}

TEST(Part2, clearPlaylistNonexistent) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.clearPlaylist("mY_plaYList");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 1);
  EXPECT_THAT(
      commandOutput[0],
      HasSubstr("Cannot clear playlist mY_plaYList: Playlist does not exist"));
}

TEST(Part2, deletePlaylist) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.createPlaylist("mY_plaYList");
  videoPlayer.deletePlaylist("MY_PLAYLIST");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 2);
  EXPECT_THAT(commandOutput[0],
              HasSubstr("Successfully created new playlist: mY_plaYList"));
  EXPECT_THAT(commandOutput[1], HasSubstr("Deleted playlist: MY_PLAYLIST"));
}

TEST(Part2, deletePlaylistNonexistent) {
  VideoPlayer videoPlayer = VideoPlayer();
  testing::internal::CaptureStdout();
  videoPlayer.deletePlaylist("mY_plaYList");
  std::string output = testing::internal::GetCapturedStdout();
  std::vector<std::string> commandOutput = splitlines(output);
  ASSERT_EQ(commandOutput.size(), 1);
  EXPECT_THAT(
      commandOutput[0],
      HasSubstr("Cannot delete playlist mY_plaYList: Playlist does not exist"));
}
