package com.google;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    videoPlayer.createPlaylist("my_PLAYlist");
    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_PLAYlist"));
  }

  @Test
  public void testCreateExistingPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.createPlaylist("my_PLAYlist");
    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(outputStream.toString(),
        containsString("Cannot create playlist: A playlist with the same name already exists"));
  }

  @Test
  public void testAddToPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_PLAYlist", "amazing_cats_video_id");
    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("Added video to my_PLAYlist: Amazing Cats"));
  }

  @Test
  public void testAddToPlaylistAlreadyAdded() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.addVideoToPlaylist("my_PLAYlist", "amazing_cats_video_id");
    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("Added video to my_playlist: Amazing Cats"));
    assertThat(outputStream.toString(),
        containsString("Cannot add video to my_PLAYlist: Video already added"));
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

    List<String> outputList = Stream.of(outputStream.toString().split("\n"))
        .map(String::trim)
        .collect(Collectors.toList());
    outputList = outputList.subList(outputList.size() - 2, outputList.size());
    List<String> expected = new ArrayList<>(outputList);
    Collections.sort(expected);
    assertEquals(outputList, expected);
  }

  @Test
  public void testShowPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.showPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.showPlaylist("my_PLAYlist");
    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("Showing playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("No videos here yet"));
    assertThat(outputStream.toString(), containsString("Added video to my_playlist: Amazing Cats"));
    assertThat(outputStream.toString(), containsString("Showing playlist: my_PLAYlist"));
    assertThat(outputStream.toString(),
        containsString("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
  }

  @Test
  public void showPlaylistAfterRemoveAVideoFromPlaylistThenReAdd() {
    videoPlayer.createPlaylist("mY_plaYList");
    videoPlayer.addVideoToPlaylist("mY_plaYList", "amazing_cats_video_id");
    videoPlayer.addVideoToPlaylist("mY_plaYList", "life_at_google_video_id");
    videoPlayer.removeFromPlaylist("mY_plaYList", "amazing_cats_video_id");
    videoPlayer.addVideoToPlaylist("mY_plaYList", "amazing_cats_video_id");
    videoPlayer.showPlaylist("mY_plaYList");

    assertThat(outputStream.toString(), containsString("Showing playlist: mY_plaYList"));
    assertThat(outputStream.toString(),
        containsString("Life at Google (life_at_google_video_id) [#google #career]"));
    assertThat(outputStream.toString(),
        containsString("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
    assertTrue(
        outputStream.toString()
            .indexOf("Life at Google (life_at_google_video_id) [#google #career]") <
            outputStream.toString()
                .indexOf("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
  }

  @Test
  public void testShowPlaylistNonExistent() throws Exception {
    videoPlayer.showPlaylist("my_playlist");
    assertThat(outputStream.toString(), containsString(
        "Cannot show playlist my_playlist: Playlist does not exist"));
  }

  @Test
  public void testRemoveFromPlaylist() {
    videoPlayer.createPlaylist("my_PLAYlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.removeFromPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.removeFromPlaylist("my_PLAYlist", "amazing_cats_video_id");
    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_PLAYlist"));
    assertThat(outputStream.toString(), containsString("Added video to my_playlist: Amazing Cats"));
    assertThat(outputStream.toString(),
        containsString("Removed video from my_playlist: Amazing Cats"));
    assertThat(outputStream.toString(),
        containsString("Cannot remove video from my_PLAYlist: Video is not in playlist"));
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
    videoPlayer.addVideoToPlaylist("my_PLAYlist", "amazing_cats_video_id");
    videoPlayer.removeFromPlaylist("my_playlist", "some_other_video_id");
    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("Added video to my_PLAYlist: Amazing Cats"));
    assertThat(outputStream.toString(),
        containsString("Cannot remove video from my_playlist: Video does not exist"));
  }

  @Test
  public void testRemoveFromPlaylistNonexistentPlaylist() {
    videoPlayer.removeFromPlaylist("my_cool_playlist", "some_other_video_id");
    assertThat(outputStream.toString(),
        containsString("Cannot remove video from my_cool_playlist: Playlist does not exist"));
  }

  @Test
  public void testClearPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.showPlaylist("my_playlist");
    videoPlayer.clearPlaylist("my_PLAYlist");
    videoPlayer.showPlaylist("my_playlist");

    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("Added video to my_playlist: Amazing Cats"));
    assertThat(outputStream.toString(), containsString("Showing playlist: my_playlist"));
    assertThat(outputStream.toString(),
        containsString("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
    assertThat(outputStream.toString(),
        containsString("Successfully removed all videos from my_PLAYlist"));
    assertThat(outputStream.toString(), containsString("Showing playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("No videos here yet"));
  }

  @Test
  public void testClearPlaylistNonexistent() {
    videoPlayer.clearPlaylist("my_PLAYlist");
    assertThat(outputStream.toString(),
        containsString("Cannot clear playlist my_PLAYlist: Playlist does not exist"));
  }

  @Test
  public void testDeletePlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.deletePlaylist("my_PLAYlist");
    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("Deleted playlist: my_PLAYlist"));
  }

  @Test
  public void test_delete_playlist_nonexistent() {
    videoPlayer.deletePlaylist("my_playlist");
    assertThat(outputStream.toString(),
        containsString("Cannot delete playlist my_playlist: Playlist does not exist"));
  }
}
