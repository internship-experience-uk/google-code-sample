package com.google;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;

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
    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_playlist"));
  }

  @Test
  public void testCreateExistingPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.createPlaylist("my_playlist");
    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(outputStream.toString(),
        containsString("Cannot create playlist: A playlist with the same name already exists"));
  }

  @Test
  public void testAddToPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("Added video to my_playlist: Amazing Cats"));
  }

  @Test
  public void testAddToPlaylistAlreadyAdded() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("Added video to my_playlist: Amazing Cats"));
    assertThat(outputStream.toString(),
        containsString("Cannot add video to my_playlist: Video already added"));
  }

  @Test
  public void testAddVideoToPlaylistNonExistentVideo() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.addVideoToPlaylist("my_playlist", "some_other_video_id");
    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(outputStream.toString(),
        containsString("Cannot add video to my_playlist: Video does not exist"));
  }

  @Test
  public void testAddVideoToPlaylistNonExistent() {
    videoPlayer.addVideoToPlaylist("another_playlist", "amazing_cats_video_id");
    assertThat(outputStream.toString(),
        containsString("Cannot add video to another_playlist: Playlist does not exist"));
  }

  @Test
  public void testShowAllPlaylistsNoPlaylistsExist() {
    videoPlayer.showAllPlaylists();
    assertThat(outputStream.toString(), containsString("No playlists exist yet"));
  }

  @Test
  public void testShowAllPlaylists() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.createPlaylist("another_playlist");
    videoPlayer.showAllPlaylists();
    assertThat(outputStream.toString(), containsString("Showing all playlists:"));
    assertThat(outputStream.toString(), containsString("my_playlist"));
    assertThat(outputStream.toString(), containsString("another_playlist"));

    List<String> outputList = new ArrayList<>(Arrays.asList(outputStream.toString().split("\n")));
    outputList = outputList.subList(outputList.size() - 2, outputList.size());
    List<String> expected = new ArrayList<>(outputList);
    Collections.sort(expected);
    assertThat(outputList, contains(expected));
  }

  @Test
  public void testShowPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.showPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.showPlaylist("my_playlist");
    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("Showing playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("No videos here yet"));
    assertThat(outputStream.toString(), containsString("Added video to my_playlist: Amazing Cats"));
    assertThat(outputStream.toString(), containsString("Showing playlist: my_playlist"));
    assertThat(outputStream.toString(),
        containsString("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
  }

  @Test
  public void testShowPlaylistNonExistent() throws Exception {
    videoPlayer.showPlaylist("my_playlist");
    assertThat(outputStream.toString(), containsString(
        "Cannot show playlist my_playlist: Playlist does not exist"));
  }

  @Test
  public void testRemoveFromPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.removeFromPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.removeFromPlaylist("my_playlist", "amazing_cats_video_id");
    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("Added video to my_playlist: Amazing Cats"));
    assertThat(outputStream.toString(),
        containsString("Removed video from my_playlist: Amazing Cats"));
    assertThat(outputStream.toString(),
        containsString("Cannot remove video from my_playlist: Video is not in playlist"));
  }

  @Test
  public void testRemoveFromPlaylistVideoNotInPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.removeFromPlaylist("my_playlist", "amazing_cats_video_id");
    assertThat(outputStream.toString(),
        containsString("Cannot remove video from my_playlist: Video is not in playlist"));
  }

  @Test
  public void testRemoveFromPlaylistNonexistentVideo() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.removeFromPlaylist("my_playlist", "some_other_video_id");
    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("Added video to my_playlist: Amazing Cats"));
    assertThat(outputStream.toString(),
        containsString("Cannot remove video from my_playlist: Video does not exist"));
  }

  @Test
  public void testRemoveFromPlaylistNonexistentPlaylist() throws Exception {
    videoPlayer.removeFromPlaylist("my_cool_playlist", "some_other_video_id");
    assertThat(outputStream.toString(),
        containsString("Cannot remove video from my_cool_playlist: Playlist does not exist"));
  }

  @Test
  public void testClearPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.showPlaylist("my_playlist");
    videoPlayer.clearPlaylist("my_playlist");
    videoPlayer.showPlaylist("my_playlist");

    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("Added video to my_playlist: Amazing Cats"));
    assertThat(outputStream.toString(), containsString("Showing playlist: my_playlist"));
    assertThat(outputStream.toString(),
        containsString("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
    assertThat(outputStream.toString(),
        containsString("Successfully removed all videos from my_playlist"));
    assertThat(outputStream.toString(), containsString("Showing playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("No videos here yet"));
  }

  @Test
  public void testClearPlaylistNonexistent() {
    videoPlayer.clearPlaylist("my_playlist");
    assertThat(outputStream.toString(),
        containsString("Cannot clear playlist my_playlist: Playlist does not exist"));
  }

  @Test
  public void testDeletePlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.deletePlaylist("my_playlist");
    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("Deleted playlist: my_playlist"));
  }

  @Test
  public void test_delete_playlist_nonexistent() {
    videoPlayer.deletePlaylist("my_playlist");
    assertThat(outputStream.toString(),
        containsString("Cannot delete playlist my_playlist: Playlist does not exist"));
  }
}
