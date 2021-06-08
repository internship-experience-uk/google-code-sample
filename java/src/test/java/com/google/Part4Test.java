package com.google;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Part4Test {

  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private InputStream stdin;
  private VideoPlayer videoPlayer;

  @BeforeEach
  public void setUp() {
    System.setOut(new PrintStream(outputStream));
    videoPlayer = new VideoPlayer();
    stdin = System.in;
  }

  @AfterEach
  public void tearDown() {
    System.setIn(stdin);
  }

  @Test
  public void testFlagVideoWithReason() {
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    assertTrue(outputStream.toString()
        .contains("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
  }

  @Test
  public void testFlagVideoWithoutReason() {
    videoPlayer.flagVideo("another_cat_video_id");
    assertTrue(outputStream.toString()
        .contains("Successfully flagged video: Another Cat Video (reason: Not supplied)"));
  }

  @Test
  public void testFlagVideoAlreadyFlagged() {
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    assertTrue(outputStream.toString()
        .contains("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertTrue(outputStream.toString().contains("Cannot flag video: Video is already flagged"));
  }

  @Test
  public void testFlagVideoNonexistent() {
    videoPlayer.flagVideo("video_does_not_exist", "flagVideo_reason");
    assertTrue(outputStream.toString().contains("Cannot flag video: Video does not exist"));
  }

  @Test
  public void testFlagVideoCanNoLongerPlay() {
    videoPlayer.flagVideo("amazing_cats_video_id");
    videoPlayer.playVideo("amazing_cats_video_id");
    assertTrue(outputStream.toString()
        .contains("Successfully flagged video: Amazing Cats (reason: Not supplied)"));
    assertTrue(outputStream.toString()
        .contains("Cannot play video: Video is currently flagged (reason: Not supplied)"));
  }

  @Test
  public void testFlagVideoAddVideoToPlaylist() {
    videoPlayer.flagVideo("amazing_cats_video_id");
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    assertTrue(outputStream.toString()
        .contains("Successfully flagged video: Amazing Cats (reason: Not supplied)"));
    assertTrue(outputStream.toString().contains("Successfully created new playlist: my_playlist"));
    assertTrue(outputStream.toString().contains(
        "Cannot add video to playlist my_playlist: Video is currently flagged (reason: Not supplied)"));
  }

  @Test
  public void testFlagVideoShowPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.showPlaylist("my_playlist");
    assertTrue(outputStream.toString().contains("Successfully created new playlist: my_playlist"));
    assertTrue(outputStream.toString().contains("Added video to my_playlist: Amazing Cats"));
    assertTrue(outputStream.toString()
        .contains("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertTrue(outputStream.toString().contains("Showing playlist: my_playlist"));
    assertTrue(outputStream.toString().contains(
        "Amazing Cats (amazing_cats_video_id) [#cat #animal] - FLAGGED (reason: dont_like_cats)"));
  }

  @Test
  public void testFlagVideoShowAllVideos() {
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.showAllVideos();

    assertTrue(outputStream.toString()
        .contains("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertTrue(outputStream.toString().contains("Here's a list of all available videos:"));
    assertTrue(outputStream.toString().contains(
        " Amazing Cats (amazing_cats_video_id) [#cat #animal] - FLAGGED (reason: dont_like_cats)"));
    assertTrue(outputStream.toString()
        .contains("Another Cat Video (another_cat_video_id) [#cat #animal]"));
    assertTrue(outputStream.toString().contains("Funny Dogs (funny_dogs_video_id) [#dog #animal]"));
    assertTrue(outputStream.toString()
        .contains("Life at Google (life_at_google_video_id) [#google #career]"));
  }

  @Test
  public void testFlagVideoSearchVideos() {
    System.setIn(new ByteArrayInputStream("No\r\n".getBytes()));
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.searchVideos("cat");
    assertTrue(outputStream.toString()
        .contains("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertTrue(outputStream.toString().contains("Here are the results for cat:"));
    assertTrue(outputStream.toString().contains("1) Another Cat Video (another_cat_video_id)"));
    assertTrue(outputStream.toString().contains(
        "Would you like to play any of the above? If yes, specify the number of the video. If your answer is not a valid number, we will assume it's a no"));
  }

  @Test
  public void testFlagVideoStopVideoPlaying() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.showPlaying();
    assertTrue(outputStream.toString().contains("Playing video: Amazing Cats"));
    assertTrue(outputStream.toString()
        .contains("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertTrue(outputStream.toString().contains("No video is currently playing"));
  }

  @Test
  public void testAllowVideo() {
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.allowVideo("amazing_cats_video_id");
    assertTrue(outputStream.toString()
        .contains("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertTrue(
        outputStream.toString().contains("Successfully removed flag from video: Amazing Cats"));
  }

  @Test
  public void testAllowVideoNotFlagged() {
    videoPlayer.allowVideo("amazing_cats_video_id");
    videoPlayer.showPlaying();
    assertTrue(
        outputStream.toString().contains("Cannot remove flag from video: Video is not flagged"));
  }

  @Test
  public void testAllowVideoShowPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.showPlaylist("my_playlist");
    videoPlayer.allowVideo("amazing_cats_video_id");
    videoPlayer.showPlaylist("my_playlist");
    assertTrue(outputStream.toString().contains("Successfully created new playlist: my_playlist"));
    assertTrue(outputStream.toString().contains("Added video to my_playlist: Amazing Cats"));
    assertTrue(outputStream.toString()
        .contains("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertTrue(outputStream.toString().contains("Showing playlist: my_playlist"));
    assertTrue(outputStream.toString().contains(
        "Amazing Cats (amazing_cats_video_id) [#cat #animal] - FLAGGED (reason: dont_like_cats)"));
    assertTrue(
        outputStream.toString().contains("Successfully removed flag from video: Amazing Cats"));
    assertTrue(
        outputStream.toString().contains("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
  }
}
