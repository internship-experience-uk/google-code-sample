package com.google;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Part2Test extends TestBase {
  @Test
  public void testCreatePlaylist() {
    videoPlayer.createPlaylist("my_PLAYlist");
    assertEquals(1, getOutputLines().length);
    assertThat(outputStream.toString(),
        containsString("Successfully created new playlist: my_PLAYlist"));
  }

  @Test
  public void testCreateExistingPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.createPlaylist("my_PLAYlist");
    String[] lines = getOutputLines();
    assertEquals(2, lines.length, outputStream.toString());
    assertThat(lines[0],
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(lines[1],
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

    var lines = getOutputLines();
    assertEquals( 3, lines.length, outputStream.toString());
    assertThat(lines[0], containsString("Successfully created new playlist: my_playlist"));
    assertThat(lines[1], containsString("Added video to my_playlist: Amazing Cats"));
    assertThat(lines[2], containsString("Cannot add video to my_PLAYlist: Video already added"));
  }

  @Test
  public void testAddVideoToPlaylistNonExistentVideo() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.addVideoToPlaylist("my_playlist", "some_other_video_id");

    var lines = getOutputLines();
    assertEquals(3, lines.length, outputStream.toString());
    assertThat(lines[0],
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(lines[2],
        containsString("Cannot add video to my_playlist: Video does not exist"));
  }

  @Test
  public void testAddVideoToPlaylistNonExistent() {
    videoPlayer.addVideoToPlaylist("another_playlist", "amazing_cats_video_id");
    assertEquals(1, getOutputLines().length);
    assertThat(outputStream.toString(),
        containsString("Cannot add video to another_playlist: Playlist does not exist"));
  }

  @Test
  public void testAddVideoToPlaylistNoVideoNoPlaylist() {
    videoPlayer.addVideoToPlaylist("another_playlist", "does_not_exist_video_id");
    assertEquals(1, getOutputLines().length);
    assertThat(outputStream.toString(),
        containsString("Cannot add video to another_playlist: Playlist does not exist"));
  }

  @Test
  public void testShowAllPlaylistsNoPlaylistsExist() {
    videoPlayer.showAllPlaylists();
    assertEquals(1, getOutputLines().length);
    assertThat(outputStream.toString(), containsString("No playlists exist yet"));
  }

  @Test
  public void testShowAllPlaylists() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.createPlaylist("another_playlist");
    videoPlayer.showAllPlaylists();

    var lines = getOutputLines();
    assertEquals(5, lines.length, outputStream.toString());
    assertThat(lines[2], containsString("Showing all playlists:"));
    assertThat(lines[3], containsString("another_playlist"));
    assertThat(lines[4], containsString("my_playlist"));
  }

  @Test
  public void testShowPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.showPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.showPlaylist("my_PLAYlist");

    var lines = getOutputLines();
    assertEquals(6, lines.length, outputStream.toString());
    assertThat(lines[0],
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(lines[1], containsString("Showing playlist: my_playlist"));
    assertThat(lines[2], containsString("No videos here yet"));
    assertThat(lines[3], containsString("Added video to my_playlist: Amazing Cats"));
    assertThat(lines[4], containsString("Showing playlist: my_PLAYlist"));
    assertThat(lines[5],
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

    var lines = getOutputLines();
    assertEquals(8, lines.length, outputStream.toString());
    assertThat(lines[5], containsString("Showing playlist: mY_plaYList"));
    assertThat(lines[6],
        containsString("Life at Google (life_at_google_video_id) [#google #career]"));
    assertThat(lines[7],
        containsString("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
  }

  @Test
  public void testShowPlaylistNonExistent() {
    videoPlayer.showPlaylist("my_playlist");
    assertEquals(1, getOutputLines().length);
    assertThat(outputStream.toString(), containsString(
        "Cannot show playlist my_playlist: Playlist does not exist"));
  }

  @Test
  public void testRemoveFromPlaylist() {
    videoPlayer.createPlaylist("my_PLAYlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.removeFromPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.removeFromPlaylist("my_PLAYlist", "amazing_cats_video_id");

    var lines = getOutputLines();
    assertEquals(4, lines.length, outputStream.toString());
    assertThat(lines[0],
        containsString("Successfully created new playlist: my_PLAYlist"));
    assertThat(lines[1], containsString("Added video to my_playlist: Amazing Cats"));
    assertThat(lines[2],
        containsString("Removed video from my_playlist: Amazing Cats"));
    assertThat(lines[3],
        containsString("Cannot remove video from my_PLAYlist: Video is not in playlist"));
  }

  @Test
  public void testRemoveFromPlaylistVideoNotInPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.removeFromPlaylist("my_playlist", "amazing_cats_video_id");

    var lines = getOutputLines();
    assertEquals(2, lines.length, outputStream.toString());
    assertThat(lines[1],
        containsString("Cannot remove video from my_playlist: Video is not in playlist"));
  }

  @Test
  public void testRemoveFromPlaylistNonexistentVideo() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_PLAYlist", "amazing_cats_video_id");
    videoPlayer.removeFromPlaylist("my_playlist", "some_other_video_id");

    var lines = getOutputLines();
    assertEquals(3, lines.length, outputStream.toString());
    assertThat(lines[0],
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(lines[1], containsString("Added video to my_PLAYlist: Amazing Cats"));
    assertThat(lines[2],
        containsString("Cannot remove video from my_playlist: Video does not exist"));
  }

  @Test
  public void testRemoveFromPlaylistNonexistentPlaylist() {
    videoPlayer.removeFromPlaylist("my_cool_playlist", "some_other_video_id");
    assertEquals(1, getOutputLines().length);
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

    var lines = getOutputLines();
    assertEquals(7, lines.length, outputStream.toString());
    assertThat(lines[0],
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(lines[1], containsString("Added video to my_playlist: Amazing Cats"));
    assertThat(lines[2], containsString("Showing playlist: my_playlist"));
    assertThat(lines[3],
        containsString("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
    assertThat(lines[4],
        containsString("Successfully removed all videos from my_PLAYlist"));
    assertThat(lines[5], containsString("Showing playlist: my_playlist"));
    assertThat(lines[6], containsString("No videos here yet"));
  }

  @Test
  public void testClearPlaylistNonexistent() {
    videoPlayer.clearPlaylist("my_PLAYlist");
    assertEquals(1, getOutputLines().length);
    assertThat(outputStream.toString(),
        containsString("Cannot clear playlist my_PLAYlist: Playlist does not exist"));
  }

  @Test
  public void testDeletePlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.deletePlaylist("my_PLAYlist");

    var lines = getOutputLines();
    assertEquals(2, lines.length, outputStream.toString());
    assertThat(lines[0],
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(lines[1], containsString("Deleted playlist: my_PLAYlist"));
  }

  @Test
  public void testDeletePlaylistNonexistent() {
    videoPlayer.deletePlaylist("my_playlist");
    assertEquals(1, getOutputLines().length);
    assertThat(outputStream.toString(),
        containsString("Cannot delete playlist my_playlist: Playlist does not exist"));
  }
}
