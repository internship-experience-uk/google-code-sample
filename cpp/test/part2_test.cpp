#include <gtest/gtest.h>
#include <gmock/gmock.h>
#include "../src/videoplayer.h"

using ::testing::HasSubstr;
using ::testing::MatchesRegex;

TEST(Part2, createPlaylist)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.createPlaylist("my_playlist");
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Successfully created new playlist: my_playlist"));
}

TEST(Part2, createExistingPlaylist)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.createPlaylist("my_playlist");
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Successfully created new playlist: my_playlist"));
    EXPECT_THAT(output, HasSubstr("Cannot create playlist: A playlist with the same name already "));
}

TEST(Part2, addToPlaylist)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Successfully created new playlist: my_playlist"));
    EXPECT_THAT(output, HasSubstr("Added video to my_playlist: Amazing Cats"));
}

TEST(Part2, addToPlaylistAlreadyAdded)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Successfully created new playlist: my_playlist"));
    EXPECT_THAT(output, HasSubstr("Added video to my_playlist: Amazing Cats"));
    EXPECT_THAT(output, HasSubstr("Cannot add video to my_playlist: Video already added"));
}

TEST(Part2, addVideoToPlaylistNonExistentVideo)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.addVideoToPlaylist("my_playlist", "some_other_video_id");
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Successfully created new playlist: my_playlist"));
    EXPECT_THAT(output, HasSubstr("Added video to my_playlist: Amazing Cats"));
    EXPECT_THAT(output, HasSubstr("Cannot add video to my_playlist: Video does not exist"));
}

TEST(Part2, addVideoToPlaylistNonExistent)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.addVideoToPlaylist("another_playlist", "amazing_cats_video_id");
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Cannot add video to another_playlist: Playlist does not exist"));
}

TEST(Part2, showAllPlaylist)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.showAllPlaylists();
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.showAllPlaylists();
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("No playlists exist yet"));
    EXPECT_THAT(output, HasSubstr("Showing all playlists:"));
    EXPECT_THAT(output, HasSubstr("my_playlist"));
}

TEST(Part2, showPlaylist)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.showPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.showPlaylist("my_playlist");
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Successfully created new playlist: my_playlist"));
    EXPECT_THAT(output, HasSubstr("Showing Playlist: my_playlist"));
    EXPECT_THAT(output, HasSubstr("No videos here yet"));
    EXPECT_THAT(output, HasSubstr("Added video to my_playlist: Amazing Cats"));
    EXPECT_THAT(output, HasSubstr("Showing Playlist: my_playlist"));
    EXPECT_THAT(output, HasSubstr("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
}

TEST(Part2, removeFromPlaylist)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.removeFromPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.removeFromPlaylist("my_playlist", "amazing_cats_video_id");
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Successfully created new playlist: my_playlist"));
    EXPECT_THAT(output, HasSubstr("Added video to my_playlist: Amazing Cats"));
    EXPECT_THAT(output, HasSubstr("Removed video from my_playlist: Amazing Cats"));
    EXPECT_THAT(output, HasSubstr("Cannot remove video from my_playlist: Video is not in playlist"));
}

TEST(Part2, removeFromPlaylistNonexistantVideo)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.removeFromPlaylist("my_playlist", "some_other_video_id");
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Successfully created new playlist: my_playlist"));
    EXPECT_THAT(output, HasSubstr("Added video to my_playlist: Amazing Cats"));
    EXPECT_THAT(output, HasSubstr("Cannot remove video from my_playlist: Video does not exist"));
}

TEST(Part2, clearPlaylist)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.showPlaylist("my_playlist");
    videoPlayer.clearPlaylist("my_playlist");
    videoPlayer.showPlaylist("my_playlist");
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Successfully created new playlist: my_playlist"));
    EXPECT_THAT(output, HasSubstr("Added video to my_playlist: Amazing Cats"));
    EXPECT_THAT(output, HasSubstr("Showing Playlist: my_playlist"));
    EXPECT_THAT(output, HasSubstr("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
    EXPECT_THAT(output, HasSubstr("Successfully removed all videos from my_playlist"));
    EXPECT_THAT(output, HasSubstr("Showing Playlist: my_playlist"));
    EXPECT_THAT(output, HasSubstr("No videos here yet"));
}

TEST(Part2,  clearPlaylistNonexistant)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.clearPlaylist("my_playlist");
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Cannot clear playlist another_playlist: Playlist does not exist"));
}

TEST(Part2,  deletePlaylist)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.deletePlaylist("my_playlist");
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Successfully created new playlist: my_playlist"));
    EXPECT_THAT(output, HasSubstr("Deleted playlist: my_playlist"));
}

TEST(Part2,  deletePlaylistNonexistant)
{
    VideoPlayer videoPlayer = VideoPlayer();
    testing::internal::CaptureStdout();
    videoPlayer.deletePlaylist("my_playlist");
    std::string output = testing::internal::GetCapturedStdout();
    EXPECT_THAT(output, HasSubstr("Cannot delete playlist my_playlist: Playlist does not exist"));
}