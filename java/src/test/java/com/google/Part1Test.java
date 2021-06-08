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
  public void testShowAllVideos() {
    videoPlayer.showAllVideos();
    assertTrue(outputStream.toString().contains("Here's a list of all available videos:"));
    assertTrue(
        outputStream.toString().contains("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
    assertTrue(outputStream.toString()
        .contains("Another Cat Video (another_cat_video_id) [#cat #animal]"));
    assertTrue(outputStream.toString().contains("Funny Dogs (funny_dogs_video_id) [#dog #animal]"));
    assertTrue(outputStream.toString()
        .contains("Life at Google (life_at_google_video_id) [#google #career]"));
    assertTrue(outputStream.toString().contains("Video about nothing (nothing_video_id) []"));

    List<String> outputList = new ArrayList<>(Arrays.asList(outputStream.toString().split("\n")));
    outputList.remove(0);
    List<String> expected = new ArrayList<>(outputList);
    Collections.sort(expected);
    assertEquals(expected, outputList);
  }

  @Test
  public void testPlayVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    assertTrue(outputStream.toString().contains("Playing video: Amazing Cats"));
  }

  @Test
  public void testPlayVideoNonExistent() {
    videoPlayer.playVideo("some_other_video_that_doesnt_exist");
    assertTrue(outputStream.toString().contains("Cannot play video: Video does not exist"));
  }

  @Test
  public void testPlayVideoStopPrevious() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.playVideo("funny_dogs_video_id");
    assertTrue(outputStream.toString().contains("Stopping video: Amazing Cats"));
    assertTrue(outputStream.toString().contains("Playing video: Funny Dogs"));
  }

  @Test
  public void testPlayVideoDontStopPreviousIfNonExistent() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.playVideo("some_other_video");
    assertFalse(outputStream.toString().contains("Stopping video: Amazing Cats"));
    assertTrue(outputStream.toString().contains("Cannot play video: Video does not exist"));
  }

  @Test
  public void testStopVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.stopVideo();

    assertTrue(outputStream.toString().contains("Playing video: Amazing Cats"));
    assertTrue(outputStream.toString().contains("Stopping video: Amazing Cats"));
  }

  @Test
  public void testStopVideoTwice() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.stopVideo();
    videoPlayer.stopVideo();

    assertTrue(outputStream.toString().contains("Playing video: Amazing Cats"));
    assertTrue(outputStream.toString().contains("Stopping video: Amazing Cats"));
    assertTrue(
        outputStream.toString().contains("Cannot stop video: No video is currently playing"));
  }

  @Test
  public void testStopVideoNothingPlaying() {
    videoPlayer.stopVideo();
    assertTrue(
        outputStream.toString().contains("Cannot stop video: No video is currently playing"));
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
    assertTrue(outputStream.toString().contains("Playing video: Amazing Cats"));
    assertTrue(outputStream.toString()
        .contains("Currently playing: Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
  }

  @Test
  public void testShowNothingPlaying() {
    videoPlayer.showPlaying();
    assertTrue(outputStream.toString().contains("No video is currently playing"));
  }

  @Test
  public void testPauseVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.pauseVideo();
    assertTrue(outputStream.toString().contains("Playing video: Amazing Cats"));
    assertTrue(outputStream.toString().contains("Pausing video: Amazing Cats"));
  }

  @Test
  public void testPauseVideoShowVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.pauseVideo();
    videoPlayer.showPlaying();
    assertTrue(outputStream.toString().contains("Playing video: Amazing Cats"));
    assertTrue(outputStream.toString().contains("Pausing video: Amazing Cats"));
    assertTrue(outputStream.toString().contains(
        "Currently playing: Amazing Cats (amazing_cats_video_id) [#cat #animal] - PAUSED"));
  }

  @Test
  public void testPauseVideoNothingPlaying() {
    videoPlayer.pauseVideo();
    assertTrue(
        outputStream.toString().contains("Cannot pause video: No video is currently playing"));
  }

  @Test
  public void testContinueVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.pauseVideo();
    videoPlayer.continueVideo();
    assertTrue(outputStream.toString().contains("Playing video: Amazing Cats"));
    assertTrue(outputStream.toString().contains("Pausing video: Amazing Cats"));
    assertTrue(outputStream.toString().contains("Continuing video: Amazing Cats"));
  }

  @Test
  public void testContinueVideoNotPaused() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.continueVideo();
    assertTrue(outputStream.toString()
        .contains("Cannot continue video: Video is not paused"));
  }

  @Test
  public void testContinueVideoNothingPlaying() {
    videoPlayer.continueVideo();
    assertTrue(
        outputStream.toString().contains("Cannot continue video: No video is currently playing"));
  }
}
