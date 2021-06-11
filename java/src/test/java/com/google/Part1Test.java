package com.google;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Part1Test {

  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private VideoPlayer videoPlayer;

  @BeforeEach
  public void setUp() {
    System.setOut(new PrintStream(outputStream));
    videoPlayer = new VideoPlayer();
  }

  @Test
  public void testNumberOfVideos() {
    videoPlayer.numberOfVideos();
    assertEquals("5 videos in the library\n", outputStream.toString());
  }

  @Test
  public void testShowAllVideos() {
    videoPlayer.showAllVideos();

    assertEquals("Here's a list of all available videos:\n"
            + "Amazing Cats (amazing_cats_video_id) [#cat #animal]\n"
            + "Another Cat Video (another_cat_video_id) [#cat #animal]\n"
            + "Funny Dogs (funny_dogs_video_id) [#dog #animal]\n"
            + "Life at Google (life_at_google_video_id) [#google #career]\n"
            + "Video about nothing (nothing_video_id) []\n"
        , outputStream.toString());

    List<String> outputList = new ArrayList<>(Arrays.asList(outputStream.toString().split("\n")));
    outputList.remove(0);
    List<String> expected = new ArrayList<>(outputList);
    Collections.sort(expected);
    assertEquals(expected, outputList);
  }

  @Test
  public void testPlayVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    assertEquals(outputStream.toString(),"Playing video: Amazing Cats\n");
  }

  @Test
  public void testPlayVideoNonExistent() {
    videoPlayer.playVideo("some_other_video_that_doesnt_exist");
    assertEquals("Cannot play video: Video does not exist\n", outputStream.toString());
  }

  @Test
  public void testPlayVideoStopPrevious() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.playVideo("funny_dogs_video_id");
    assertEquals("Playing video: Amazing Cats\n"
            + "Stopping video: Amazing Cats\n"
            + "Playing video: Funny Dogs\n"
        , outputStream.toString());
  }

  @Test
  public void testPlayVideoDontStopPreviousIfNonExistent() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.playVideo("some_other_video");

    assertFalse(outputStream.toString().contains("Stopping video: Amazing Cats"));

    assertEquals("Playing video: Amazing Cats\n"
            + "Cannot play video: Video does not exist\n"
        , outputStream.toString());
  }

  @Test
  public void testStopVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.stopVideo();

    assertEquals("Playing video: Amazing Cats\n"
            + "Stopping video: Amazing Cats\n"
        , outputStream.toString());
  }

  @Test
  public void testStopVideoTwice() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.stopVideo();
    videoPlayer.stopVideo();

    assertEquals("Playing video: Amazing Cats\n"
            + "Stopping video: Amazing Cats\n"
            + "Cannot stop video: No video is currently playing\n"
        , outputStream.toString());
  }

  @Test
  public void testStopVideoNothingPlaying() {
    videoPlayer.stopVideo();
    assertEquals("Cannot stop video: No video is currently playing\n", outputStream.toString());
  }

  @Test
  public void testPlayRandomVideo() {
    videoPlayer.playRandomVideo();

    Pattern pattern = Pattern
        .compile(
            "Playing video: (Amazing Cats|Another Cat Video|Funny Dogs|Life at Google|Video about nothing)");
    Matcher matcher = pattern.matcher(outputStream.toString());
    assertTrue(matcher.find());
  }

  @Test
  public void testPlayRandomVideoStopsPreviousVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.playRandomVideo();

    assertTrue(outputStream.toString().contains("Stopping video: Amazing Cats"));
    Pattern pattern = Pattern
        .compile(
            "Playing video: (Amazing Cats|Another Cat Video|Funny Dogs|Life at Google|Video about nothing)");
    Matcher matcher = pattern.matcher(outputStream.toString());
    assertTrue(matcher.find());
  }

  @Test
  public void testShowPlaying() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.showPlaying();

    assertEquals(outputStream.toString(),
        "Playing video: Amazing Cats\n"
            + "Currently playing: Amazing Cats (amazing_cats_video_id) [#cat #animal]\n");
  }

  @Test
  public void testShowNothingPlaying() {
    videoPlayer.showPlaying();

    assertEquals("No video is currently playing\n", outputStream.toString());
  }

  @Test
  public void testPauseVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.pauseVideo();

    assertEquals("Playing video: Amazing Cats\n"
            + "Pausing video: Amazing Cats\n"
        , outputStream.toString());
  }

  @Test
  public void testPauseVideoShowVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.pauseVideo();
    videoPlayer.showPlaying();

    assertEquals("Playing video: Amazing Cats\n"
            + "Pausing video: Amazing Cats\n"
            + "Currently playing: Amazing Cats (amazing_cats_video_id) [#cat #animal] - PAUSED\n"
        , outputStream.toString());
  }

  @Test
  public void testPauseVideoNothingPlaying() {
    videoPlayer.pauseVideo();

    assertEquals("Cannot pause video: No video is currently playing\n", outputStream.toString());
  }

  @Test
  public void testContinueVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.pauseVideo();
    videoPlayer.continueVideo();

    assertEquals("Playing video: Amazing Cats\n"
            + "Pausing video: Amazing Cats\n"
            + "Continuing video: Amazing Cats\n"
        , outputStream.toString());
  }

  @Test
  public void testContinueVideoNotPaused() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.continueVideo();

    assertEquals("Playing video: Amazing Cats\n"
            + "Cannot continue video: Video is not paused\n"
        , outputStream.toString());
  }

  @Test
  public void testContinueVideoNothingPlaying() {
    videoPlayer.continueVideo();

    assertEquals("Cannot continue video: No video is currently playing\n"
        , outputStream.toString());
  }
}
