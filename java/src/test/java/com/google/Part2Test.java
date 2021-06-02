package com.google;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Part2Test {

  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private VideoPlayer videoPlayer;

  @BeforeEach
  public void setUp() {
    System.setOut(new PrintStream(outputStream));
    videoPlayer = new VideoPlayer();
  }

  @Test
  public void testCreatePlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    assertTrue(outputStream.toString().contains("Successfully created new playlist: my_playlist"));
  }

  @Test
  public void testCreateExistingPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.createPlaylist("my_playlist");
    assertTrue(outputStream.toString().contains("Successfully created new playlist: my_playlist"));
    assertTrue(outputStream.toString()
        .contains("Can not create playlist: A playlist with the same name already "));
  }

  @Test
  public void testAddToPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    assertTrue(outputStream.toString().contains("Successfully created new playlist: my_playlist"));
    assertTrue(outputStream.toString().contains("Added video to my_playlist: Amazing Cats"));
  }

  @Test
  public void testAddToPlaylistAlreadyAdded() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    assertTrue(outputStream.toString().contains("Successfully created new playlist: my_playlist"));
    assertTrue(outputStream.toString().contains("Added video to my_playlist: Amazing Cats"));
    assertTrue(
        outputStream.toString().contains("Can not add video to my_playlist. Video already added."));
  }

  @Test
  public void TestAddVideoToPlaylistNonExistentVideo() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.addVideoToPlaylist("my_playlist", "some_other_video_id");
    assertTrue(outputStream.toString().contains("Successfully created new playlist: my_playlist"));
    assertTrue(outputStream.toString()
        .contains("Can not add video to my_playlist: Video does not exist."));
  }

  @Test
  public void TestAddVideoToPlaylistNonExistent() {
    videoPlayer.addVideoToPlaylist("another_playlist", "amazing_cats_video_id");
    assertTrue(outputStream.toString()
        .contains("Can not add video to another_playlist. Playlist does not exist."));
  }

  @Test
  public void TestShowAllPlaylist() {
    videoPlayer.showAllPlaylists();
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.showAllPlaylists();
    assertTrue(outputStream.toString().contains("No playlists exist yet."));
    assertTrue(outputStream.toString().contains("Showing all playlists:"));
    assertTrue(outputStream.toString().contains("my_playlist"));
  }

  @Test
  public void TestShowPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.showPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.showPlaylist("my_playlist");
    assertTrue(outputStream.toString().contains("Successfully created new playlist: my_playlist"));
    assertTrue(outputStream.toString().contains("Showing Playlist: my_playlist"));
    assertTrue(outputStream.toString().contains("No videos here yet"));
    assertTrue(outputStream.toString().contains("Added video to my_playlist: Amazing Cats"));
    assertTrue(outputStream.toString().contains("Showing Playlist: my_playlist"));
    assertTrue(
        outputStream.toString().contains("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
  }

  @Test
  public void testRemoveFromPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.removeFromPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.removeFromPlaylist("my_playlist", "amazing_cats_video_id");
    assertTrue(outputStream.toString().contains("Successfully created new playlist: my_playlist"));
    assertTrue(outputStream.toString().contains("Added video to my_playlist: Amazing Cats"));
    assertTrue(outputStream.toString().contains("Removed video from my_playlist: Amazing Cats"));
    assertTrue(outputStream.toString()
        .contains("Can not remove video from my_playlist: Video is not in playlist."));
  }

  @Test
  public void testRemoveFromPlaylistNonexistantVideo() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.removeFromPlaylist("my_playlist", "some_other_video_id");
    assertTrue(outputStream.toString().contains("Successfully created new playlist: my_playlist"));
    assertTrue(outputStream.toString().contains("Added video to my_playlist: Amazing Cats"));
    assertTrue(outputStream.toString()
        .contains("Can not remove video from my_playlist: Video does not exist."));
  }
  @Test
  public void testClearPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.showPlaylist("my_playlist");
    videoPlayer.clearPlaylist("my_playlist");
    videoPlayer.showPlaylist("my_playlist");

    assertTrue(outputStream.toString().contains("Successfully created new playlist: my_playlist"));
    assertTrue(outputStream.toString().contains("Added video to my_playlist: Amazing Cats"));
    assertTrue(outputStream.toString().contains("Showing Playlist: my_playlist"));
    assertTrue(
        outputStream.toString().contains("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
    assertTrue(
        outputStream.toString().contains("Successfully removed all videos from my_playlist."));
    assertTrue(outputStream.toString().contains("Showing Playlist: my_playlist"));
    assertTrue(outputStream.toString().contains("No videos here yet."));
  }

  @Test
  public void testClearPlaylistNonexistant() {
    videoPlayer.clearPlaylist("my_playlist");
    assertTrue(outputStream.toString()
        .contains("Can not clear playlist another_playlist: Playlist does not exist."));
  }

  @Test
  public void testDeletePlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.clearPlaylist("my_playlist");
    assertTrue(outputStream.toString().contains("Successfully created new playlist: my_playlist"));
    assertTrue(outputStream.toString().contains("Deleted playlist: my_playlist"));
  }

  @Test
  public void test_delete_playlist_nonexistant() {
    videoPlayer.clearPlaylist("my_playlist");
    assertTrue(outputStream.toString()
        .contains("Can not delete playlist my_playlist: Playlist does not exist."));
  }
}
