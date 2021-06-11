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
    assertEquals("Successfully created new playlist: my_playlist\n", outputStream.toString());
  }

  @Test
  public void testCreateExistingPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.createPlaylist("my_playlist");
    assertEquals("Successfully created new playlist: my_playlist\n"
            + "Cannot create playlist: A playlist with the same name already exists\n"
        , outputStream.toString());
  }

  @Test
  public void testAddToPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    assertEquals("Successfully created new playlist: my_playlist\n"
            + "Added video to my_playlist: Amazing Cats\n"
        , outputStream.toString());
  }

  @Test
  public void testAddToPlaylistAlreadyAdded() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    assertEquals("Successfully created new playlist: my_playlist\n"
            + "Added video to my_playlist: Amazing Cats\n"
            + "Cannot add video to my_playlist: Video already added\n"
        , outputStream.toString());
  }

  @Test
  public void testAddVideoToPlaylistNonExistentVideo() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.addVideoToPlaylist("my_playlist", "some_other_video_id");
    assertEquals("Successfully created new playlist: my_playlist\n"
            + "Added video to my_playlist: Amazing Cats\n"
            + "Cannot add video to my_playlist: Video does not exist\n"
        , outputStream.toString());
  }

  @Test
  public void testAddVideoToPlaylistNonExistent() {
    videoPlayer.addVideoToPlaylist("another_playlist", "amazing_cats_video_id");
    assertEquals("Cannot add video to another_playlist: Playlist does not exist\n", outputStream.toString());
  }

  @Test
  public void testShowAllPlaylistsNoPlaylistsExist() {
    videoPlayer.showAllPlaylists();
    assertEquals("No playlists exist yet\n", outputStream.toString());
  }

  @Test
  public void testShowAllPlaylists() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.createPlaylist("another_playlist");
    videoPlayer.showAllPlaylists();

    assertEquals("Successfully created new playlist: my_playlist\n"
            + "Successfully created new playlist: another_playlist\n"
            + "Showing all playlists:\n"
            + "another_playlist\n"
            + "my_playlist\n"
        , outputStream.toString());

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

    assertEquals("Successfully created new playlist: my_playlist\n"
            + "Showing playlist: my_playlist\n"
            + "No videos here yet\n"
            + "Added video to my_playlist: Amazing Cats\n"
            + "Showing playlist: my_playlist\n"
            + "Amazing Cats (amazing_cats_video_id) [#cat #animal]\n"
        , outputStream.toString());
  }

  @Test
  public void testShowPlaylistNonExistent() throws Exception {
    videoPlayer.showPlaylist("my_playlist");
    assertEquals("Cannot show playlist my_playlist: Playlist does not exist\n", outputStream.toString());
  }

  @Test
  public void testRemoveFromPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.removeFromPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.removeFromPlaylist("my_playlist", "amazing_cats_video_id");

    assertEquals("Successfully created new playlist: my_playlist\n"
            + "Added video to my_playlist: Amazing Cats\n"
            + "Removed video from my_playlist: Amazing Cats\n"
            + "Cannot remove video from my_playlist: Video is not in playlist\n"
        , outputStream.toString());
  }

  @Test
  public void testRemoveFromPlaylistVideoNotInPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.removeFromPlaylist("my_playlist", "amazing_cats_video_id");
    assertEquals("Successfully created new playlist: my_playlist\n"
            + "Cannot remove video from my_playlist: Video is not in playlist\n"
        , outputStream.toString());
  }

  @Test
  public void testRemoveFromPlaylistNonexistentVideo() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.removeFromPlaylist("my_playlist", "some_other_video_id");
    assertEquals("Successfully created new playlist: my_playlist\n"
            + "Added video to my_playlist: Amazing Cats\n"
            + "Cannot remove video from my_playlist: Video does not exist\n"
        , outputStream.toString());
  }

  @Test
  public void testRemoveFromPlaylistNonexistentPlaylist() throws Exception {
    videoPlayer.removeFromPlaylist("my_cool_playlist", "some_other_video_id");
    assertEquals("Cannot remove video from my_cool_playlist: Playlist does not exist\n", outputStream.toString());
  }

  @Test
  public void testClearPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.showPlaylist("my_playlist");
    videoPlayer.clearPlaylist("my_playlist");
    videoPlayer.showPlaylist("my_playlist");

    assertEquals("Successfully created new playlist: my_playlist\n"
            + "Added video to my_playlist: Amazing Cats\n"
            + "Showing playlist: my_playlist\n"
            + "Amazing Cats (amazing_cats_video_id) [#cat #animal]\n"
            + "Successfully removed all videos from my_playlist\n"
            + "Showing playlist: my_playlist\n"
            + "No videos here yet\n"
        , outputStream.toString());
  }

  @Test
  public void testClearPlaylistNonexistent() {
    videoPlayer.clearPlaylist("my_playlist");
    assertEquals("Cannot clear playlist my_playlist: Playlist does not exist\n"
        , outputStream.toString());
  }

  @Test
  public void testDeletePlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.deletePlaylist("my_playlist");
    assertEquals("Successfully created new playlist: my_playlist\n"
            + "Deleted playlist: my_playlist\n"
        , outputStream.toString());
  }

  @Test
  public void test_delete_playlist_nonexistent() {
    videoPlayer.deletePlaylist("my_playlist");
    assertEquals("Cannot delete playlist my_playlist: Playlist does not exist\n"
        , outputStream.toString());
  }
}
