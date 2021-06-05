package com.google;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
        .contains("Cannot create playlist: A playlist with the same name already exists"));
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
        outputStream.toString().contains("Cannot add video to my_playlist: Video already added"));
  }

  @Test
  public void testAddVideoToPlaylistNonExistentVideo() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.addVideoToPlaylist("my_playlist", "some_other_video_id");
    assertTrue(outputStream.toString().contains("Successfully created new playlist: my_playlist"));
    assertTrue(outputStream.toString()
        .contains("Cannot add video to my_playlist: Video does not exist"));
  }

  @Test
  public void testAddVideoToPlaylistNonExistent() {
    videoPlayer.addVideoToPlaylist("another_playlist", "amazing_cats_video_id");
    assertTrue(outputStream.toString()
        .contains("Cannot add video to another_playlist: Playlist does not exist"));
  }

  @Test
  public void testShowAllPlaylistsNoPlaylistsExist() {
    videoPlayer.showAllPlaylists();
    assertTrue(outputStream.toString().contains("No playlists exist yet"));
  }

  @Test
  public void testShowAllPlaylists() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.createPlaylist("another_playlist");
    videoPlayer.showAllPlaylists();
    assertTrue(outputStream.toString().contains("Showing all playlists:"));
    assertTrue(outputStream.toString().contains("my_playlist"));
    assertTrue(outputStream.toString().contains("another_playlist"));

    List<String> outputList = new ArrayList<>(Arrays.asList(outputStream.toString().split("\n")));
    outputList = outputList.subList(outputList.size() - 2, outputList.size());
    List<String> expected = new ArrayList<>(outputList);
    Collections.sort(expected);
    assertEquals(expected, outputList);
  }

  @Test
  public void testShowPlaylist() {
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
  public void testShowPlaylistNonExistent() throws Exception {
    videoPlayer.showPlaylist("my_playlist");
    assertTrue(outputStream.toString().contains(
        "Cannot show playlist my_playlist: Playlist does not exist"));
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
        .contains("Cannot remove video from my_playlist: Video is not in playlist"));
  }

  @Test
  public void testRemoveFromPlaylistVideoNotInPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.removeFromPlaylist("my_playlist", "amazing_cats_video_id");
    assertTrue(outputStream.toString()
        .contains("Cannot remove video from my_playlist: Video is not in playlist"));
  }

  @Test
  public void testRemoveFromPlaylistNonexistentVideo() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.removeFromPlaylist("my_playlist", "some_other_video_id");
    assertTrue(outputStream.toString().contains("Successfully created new playlist: my_playlist"));
    assertTrue(outputStream.toString().contains("Added video to my_playlist: Amazing Cats"));
    assertTrue(outputStream.toString()
        .contains("Cannot remove video from my_playlist: Video does not exist"));
  }

  @Test
  public void testRemoveFromPlaylistNonexistentPlaylist() throws Exception {
    videoPlayer.removeFromPlaylist("my_cool_playlist", "some_other_video_id");
    assertTrue(outputStream.toString()
        .contains("Cannot remove video from my_cool_playlist: Playlist does not exist"));
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
        outputStream.toString().contains("Successfully removed all videos from my_playlist"));
    assertTrue(outputStream.toString().contains("Showing Playlist: my_playlist"));
    assertTrue(outputStream.toString().contains("No videos here yet"));
  }

  @Test
  public void testClearPlaylistNonexistent() {
    videoPlayer.clearPlaylist("my_playlist");
    assertTrue(outputStream.toString()
        .contains("Cannot clear playlist my_playlist: Playlist does not exist"));
  }

  @Test
  public void testDeletePlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.deletePlaylist("my_playlist");
    assertTrue(outputStream.toString().contains("Successfully created new playlist: my_playlist"));
    assertTrue(outputStream.toString().contains("Deleted playlist: my_playlist"));
  }

  @Test
  public void test_delete_playlist_nonexistent() {
    videoPlayer.deletePlaylist("my_playlist");
    assertTrue(outputStream.toString()
        .contains("Cannot delete playlist my_playlist: Playlist does not exist"));
  }
}
