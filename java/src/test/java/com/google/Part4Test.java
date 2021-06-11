package com.google;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    assertEquals("Successfully flagged video: Amazing Cats (reason: dont_like_cats)\n", outputStream.toString());
  }

  @Test
  public void testFlagVideoWithoutReason() {
    videoPlayer.flagVideo("another_cat_video_id");

    assertEquals("Successfully flagged video: Another Cat Video (reason: Not supplied)\n", outputStream.toString());
  }

  @Test
  public void testFlagVideoAlreadyFlagged() {
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");

    assertEquals("Successfully flagged video: Amazing Cats (reason: dont_like_cats)\n"
            + "Cannot flag video: Video is already flagged\n"
        , outputStream.toString());
  }

  @Test
  public void testFlagVideoNonexistent() {
    videoPlayer.flagVideo("video_does_not_exist", "flagVideo_reason");

    assertEquals("Cannot flag video: Video does not exist\n", outputStream.toString());
  }

  @Test
  public void testFlagVideoCanNoLongerPlay() {
    videoPlayer.flagVideo("amazing_cats_video_id");
    videoPlayer.playVideo("amazing_cats_video_id");

    assertEquals("Successfully flagged video: Amazing Cats (reason: Not supplied)\n"
            + "Cannot play video: Video is currently flagged (reason: Not supplied)\n"
        , outputStream.toString());
  }

  @Test
  public void testFlagVideoAddVideoToPlaylist() {
    videoPlayer.flagVideo("amazing_cats_video_id");
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");

    assertEquals("Successfully flagged video: Amazing Cats (reason: Not supplied)\n"
            + "Successfully created new playlist: my_playlist\n"
            + "Cannot add video to my_playlist: Video is currently flagged (reason: Not supplied)\n"
        , outputStream.toString());
  }

  @Test
  public void testFlagVideoShowPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.showPlaylist("my_playlist");

    assertEquals("Successfully created new playlist: my_playlist\n"
            + "Added video to my_playlist: Amazing Cats\n"
            + "Successfully flagged video: Amazing Cats (reason: dont_like_cats)\n"
            + "Showing playlist: my_playlist\n"
            + "Amazing Cats (amazing_cats_video_id) [#cat #animal] - FLAGGED (reason: dont_like_cats)\n"
        , outputStream.toString());
  }

  @Test
  public void testFlagVideoShowAllVideos() {
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.showAllVideos();

    assertEquals("Successfully flagged video: Amazing Cats (reason: dont_like_cats)\n"
            + "Here's a list of all available videos:\n"
            + "Amazing Cats (amazing_cats_video_id) [#cat #animal] - FLAGGED (reason: dont_like_cats)\n"
            + "Another Cat Video (another_cat_video_id) [#cat #animal]\n"
            + "Funny Dogs (funny_dogs_video_id) [#dog #animal]\n"
            + "Life at Google (life_at_google_video_id) [#google #career]\n"
            + "Video about nothing (nothing_video_id) []\n"
        , outputStream.toString());
  }

  @Test
  public void testFlagVideoSearchVideos() {
    System.setIn(new ByteArrayInputStream("No\r\n".getBytes()));
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.searchVideos("cat");

    assertEquals("Successfully flagged video: Amazing Cats (reason: dont_like_cats)\n"
            + "Here are the results for cat:\n"
            + "1) Another Cat Video (another_cat_video_id)\n"
            + "Would you like to play any of the above? If yes, specify the number of the video. \n"
            + "If your answer is not a valid number, we will assume it's a no.\n\n"
        , outputStream.toString());
  }

  @Test
  public void testFlagVideoStopVideoPlaying() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.showPlaying();

    assertEquals("Playing video: Amazing Cats\n"
            + "Successfully flagged video: Amazing Cats (reason: dont_like_cats)\n"
            + "Stopping video: Amazing Cats\n"
            + "No video is currently playing\n"
        , outputStream.toString());
  }

  @Test
  public void testAllowVideo() {
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.allowVideo("amazing_cats_video_id");

    assertEquals("Successfully flagged video: Amazing Cats (reason: dont_like_cats)\n"
            + "Successfully removed flag from video: Amazing Cats\n"
        , outputStream.toString());
  }

  @Test
  public void testAllowVideoNotFlagged() {
    videoPlayer.allowVideo("amazing_cats_video_id");
    videoPlayer.showPlaying();

    assertEquals("Cannot remove flag from video: Video is not flagged\n"
            + "No video is currently playing\n"
        , outputStream.toString());
  }

  @Test
  public void testAllowVideoShowPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.showPlaylist("my_playlist");
    videoPlayer.allowVideo("amazing_cats_video_id");
    videoPlayer.showPlaylist("my_playlist");

    assertEquals("Successfully created new playlist: my_playlist\n"
            + "Added video to my_playlist: Amazing Cats\n"
            + "Successfully flagged video: Amazing Cats (reason: dont_like_cats)\n"
            + "Showing playlist: my_playlist\n"
            + "Amazing Cats (amazing_cats_video_id) [#cat #animal] - FLAGGED (reason: dont_like_cats)\n"
            + "Successfully removed flag from video: Amazing Cats\n"
            + "Showing playlist: my_playlist\n"
            + "Amazing Cats (amazing_cats_video_id) [#cat #animal]\n"
        , outputStream.toString());
  }
}
