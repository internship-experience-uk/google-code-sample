package com.google;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

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
    assertThat(outputStream.toString(), containsString("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
  }

  @Test
  public void testFlagVideoWithoutReason() {
    videoPlayer.flagVideo("another_cat_video_id");
    assertThat(outputStream.toString(), containsString("Successfully flagged video: Another Cat Video (reason: Not supplied)"));
  }

  @Test
  public void testFlagVideoAlreadyFlagged() {
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    assertThat(outputStream.toString(), containsString("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertThat(outputStream.toString(), containsString("Cannot flag video: Video is already flagged"));
  }

  @Test
  public void testFlagVideoNonexistent() {
    videoPlayer.flagVideo("video_does_not_exist", "flagVideo_reason");
    assertThat(outputStream.toString(), containsString("Cannot flag video: Video does not exist"));
  }

  @Test
  public void testFlagVideoCanNoLongerPlay() {
    videoPlayer.flagVideo("amazing_cats_video_id");
    videoPlayer.playVideo("amazing_cats_video_id");
    assertThat(outputStream.toString(), containsString("Successfully flagged video: Amazing Cats (reason: Not supplied)"));
    assertThat(outputStream.toString(), containsString("Cannot play video: Video is currently flagged (reason: Not supplied)"));
  }

  @Test
  public void testFlagVideoAddVideoToPlaylist() {
    videoPlayer.flagVideo("amazing_cats_video_id");
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    assertThat(outputStream.toString(), containsString("Successfully flagged video: Amazing Cats (reason: Not supplied)"));
    assertThat(outputStream.toString(), containsString("Successfully created new playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("Cannot add video to my_playlist: Video is currently flagged (reason: Not supplied)"));
  }

  @Test
  public void testFlagVideoShowPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.showPlaylist("my_playlist");
    assertThat(outputStream.toString(), containsString("Successfully created new playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("Added video to my_playlist: Amazing Cats"));
    assertThat(outputStream.toString(), containsString("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertThat(outputStream.toString(), containsString("Showing playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("Amazing Cats (amazing_cats_video_id) [#cat #animal] - FLAGGED (reason: dont_like_cats)"));
  }

  @Test
  public void testFlagVideoShowAllVideos() {
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.showAllVideos();

    assertThat(outputStream.toString(), containsString("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertThat(outputStream.toString(), containsString("Here's a list of all available videos:"));
    assertThat(outputStream.toString(), containsString("Amazing Cats (amazing_cats_video_id) [#cat #animal] - FLAGGED (reason: dont_like_cats)"));
    assertThat(outputStream.toString(), containsString("Another Cat Video (another_cat_video_id) [#cat #animal]"));
    assertThat(outputStream.toString(), containsString("Funny Dogs (funny_dogs_video_id) [#dog #animal]"));
    assertThat(outputStream.toString(), containsString("Life at Google (life_at_google_video_id) [#google #career]"));
  }

  @Test
  public void testFlagVideoSearchVideos() {
    System.setIn(new ByteArrayInputStream("No\r\n".getBytes()));
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.searchVideos("cat");
    assertThat(outputStream.toString(), containsString("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertThat(outputStream.toString(), containsString("Here are the results for cat:"));
    assertThat(outputStream.toString(), containsString("1) Another Cat Video (another_cat_video_id)"));
    assertThat(outputStream.toString(), containsString("Would you like to play any of the above? If yes, specify the number of the video."));
    assertThat(outputStream.toString(), containsString("If your answer is not a valid number, we will assume it's a no."));
  }

  @Test
  public void testFlagVideoStopVideoPlaying() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.showPlaying();
    assertThat(outputStream.toString(), containsString("Playing video: Amazing Cats"));
    assertThat(outputStream.toString(), containsString("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertThat(outputStream.toString(), containsString("No video is currently playing"));
  }

  @Test
  public void testAllowVideo() {
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.allowVideo("amazing_cats_video_id");
    assertThat(outputStream.toString(), containsString("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertThat(outputStream.toString(), containsString("Successfully removed flag from video: Amazing Cats"));
  }

  @Test
  public void testAllowVideoNotFlagged() {
    videoPlayer.allowVideo("amazing_cats_video_id");
    videoPlayer.showPlaying();
    assertThat(outputStream.toString(), containsString("Cannot remove flag from video: Video is not flagged"));
  }

  @Test
  public void testAllowVideoNonexistent() {
    videoPlayer.allowVideo("video_does_not_exist");
    assertThat(outputStream.toString(), containsString("Cannot remove flag from video: Video does not exist"));
  }

  @Test
  public void testAllowVideoShowPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.showPlaylist("my_playlist");
    videoPlayer.allowVideo("amazing_cats_video_id");
    videoPlayer.showPlaylist("my_playlist");
    assertThat(outputStream.toString(), containsString("Successfully created new playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("Added video to my_playlist: Amazing Cats"));
    assertThat(outputStream.toString(), containsString("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertThat(outputStream.toString(), containsString("Showing playlist: my_playlist"));
    assertThat(outputStream.toString(), containsString("Amazing Cats (amazing_cats_video_id) [#cat #animal] - FLAGGED (reason: dont_like_cats)"));
    assertThat(outputStream.toString(), containsString("Successfully removed flag from video: Amazing Cats"));
    assertThat(outputStream.toString(), containsString("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
  }
}
