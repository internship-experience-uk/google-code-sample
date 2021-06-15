package com.google;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.not;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
    assertThat(outputStream.toString(), containsString("5 videos in the library"));
  }

  @Test
  public void testShowAllVideos() {
    videoPlayer.showAllVideos();
    assertThat(outputStream.toString(), containsString("Here's a list of all available videos:"));
    assertThat(outputStream.toString(),
        containsString("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
    assertThat(outputStream.toString(),
        containsString("Another Cat Video (another_cat_video_id) [#cat #animal]"));
    assertThat(outputStream.toString(),
        containsString("Funny Dogs (funny_dogs_video_id) [#dog #animal]"));
    assertThat(outputStream.toString(),
        containsString("Life at Google (life_at_google_video_id) [#google #career]"));
    assertThat(outputStream.toString(),
        containsString("Video about nothing (nothing_video_id) []"));

    List<String> outputList = new ArrayList<>(Arrays.asList(outputStream.toString().split("\n")));
    outputList.remove(0);
    List<String> expected = new ArrayList<>(outputList);
    Collections.sort(expected);
    assertThat(outputList, contains(expected));
  }

  @Test
  public void testPlayVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    assertThat(outputStream.toString(), containsString("Playing video: Amazing Cats"));
  }

  @Test
  public void testPlayVideoNonExistent() {
    videoPlayer.playVideo("some_other_video_that_doesnt_exist");
    assertThat(outputStream.toString(), containsString("Cannot play video: Video does not exist"));
  }

  @Test
  public void testPlayVideoStopPrevious() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.playVideo("funny_dogs_video_id");
    assertThat(outputStream.toString(), containsString("Stopping video: Amazing Cats"));
    assertThat(outputStream.toString(), containsString("Playing video: Funny Dogs"));
  }

  @Test
  public void testPlayVideoDontStopPreviousIfNonExistent() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.playVideo("some_other_video");
    assertThat(outputStream.toString(), not(containsString("Stopping video: Amazing Cats")));
    assertThat(outputStream.toString(), containsString("Cannot play video: Video does not exist"));
  }

  @Test
  public void testStopVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.stopVideo();

    assertThat(outputStream.toString(), containsString("Playing video: Amazing Cats"));
    assertThat(outputStream.toString(), containsString("Stopping video: Amazing Cats"));
  }

  @Test
  public void testStopVideoTwice() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.stopVideo();
    videoPlayer.stopVideo();

    assertThat(outputStream.toString(), containsString("Playing video: Amazing Cats"));
    assertThat(outputStream.toString(), containsString("Stopping video: Amazing Cats"));
    assertThat(outputStream.toString(),
        containsString("Cannot stop video: No video is currently playing"));
  }

  @Test
  public void testStopVideoNothingPlaying() {
    videoPlayer.stopVideo();
    assertThat(outputStream.toString(),
        containsString("Cannot stop video: No video is currently playing"));
  }

  @Test
  public void testPlayRandomVideo() {
    videoPlayer.playRandomVideo();

    Pattern pattern = Pattern
        .compile(
            "Playing video: (Amazing Cats|Another Cat Video|Funny Dogs|Life at Google|Video about nothing).*",
            Pattern.DOTALL);
    assertThat(outputStream.toString(), matchesPattern(pattern));
  }

  @Test
  public void testPlayRandomVideoStopsPreviousVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.playRandomVideo();

    assertThat(outputStream.toString(), containsString("Stopping video: Amazing Cats"));
    Pattern pattern = Pattern
        .compile(
            "Playing video: Amazing Cats.*Playing video: (Amazing Cats|Another Cat Video|Funny Dogs|Life at Google|Video about nothing).*",
            Pattern.DOTALL);
    assertThat(outputStream.toString(), matchesPattern(pattern));
  }

  @Test
  public void testShowPlaying() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.showPlaying();
    assertThat(outputStream.toString(), containsString("Playing video: Amazing Cats"));
    assertThat(outputStream.toString(),
        containsString("Currently playing: Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
  }

  @Test
  public void testShowNothingPlaying() {
    videoPlayer.showPlaying();
    assertThat(outputStream.toString(), containsString("No video is currently playing"));
  }

  @Test
  public void testPauseVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.pauseVideo();
    assertThat(outputStream.toString(), containsString("Playing video: Amazing Cats"));
    assertThat(outputStream.toString(), containsString("Pausing video: Amazing Cats"));
  }

  @Test
  public void testPauseVideoShowVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.pauseVideo();
    videoPlayer.showPlaying();
    assertThat(outputStream.toString(), containsString("Playing video: Amazing Cats"));
    assertThat(outputStream.toString(), containsString("Pausing video: Amazing Cats"));
    assertThat(outputStream.toString(), containsString(
        "Currently playing: Amazing Cats (amazing_cats_video_id) [#cat #animal] - PAUSED"));
  }

  @Test
  public void testPauseVideoPlayVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.pauseVideo();
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.showPlaying();
    assertThat(outputStream.toString(), containsString("Playing video: Amazing Cats"));
    assertThat(outputStream.toString(), containsString("Pausing video: Amazing Cats"));
    assertThat(outputStream.toString(), not(containsString("PAUSED")));
  }

  @Test
  public void testPauseVideoNothingPlaying() {
    videoPlayer.pauseVideo();
    assertThat(outputStream.toString(),
        containsString("Cannot pause video: No video is currently playing"));
  }

  @Test
  public void testContinueVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.pauseVideo();
    videoPlayer.continueVideo();
    assertThat(outputStream.toString(), containsString("Playing video: Amazing Cats"));
    assertThat(outputStream.toString(), containsString("Pausing video: Amazing Cats"));
    assertThat(outputStream.toString(), containsString("Continuing video: Amazing Cats"));
  }

  @Test
  public void testContinueVideoNotPaused() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.continueVideo();
    assertThat(outputStream.toString(),
        containsString("Cannot continue video: Video is not paused"));
  }

  @Test
  public void testContinueVideoNothingPlaying() {
    videoPlayer.continueVideo();
    assertThat(outputStream.toString(),
        containsString("Cannot continue video: No video is currently playing"));
  }
}
