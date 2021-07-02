package com.google;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

public class Part1Test extends TestBase {

  public static final Pattern PLAYING_RANDOM_VIDEO_PATTERN =
    Pattern.compile(
      "Playing video: (Amazing Cats|Another Cat Video|Funny Dogs|Life at Google|Video about nothing).*",
             Pattern.DOTALL);

  @Test
  public void testNumberOfVideos() {
    videoPlayer.numberOfVideos();
    assertThat(outputStream.toString(), containsString("5 videos in the library"));
  }

  @Test
  public void testShowAllVideos() {
    videoPlayer.showAllVideos();

    String[] lines = getOutputLines();
    assertEquals(6, lines.length, outputStream.toString());
    assertThat(lines[0], containsString("Here's a list of all available videos:"));
    assertThat(lines[1],
        containsString("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
    assertThat(lines[2],
        containsString("Another Cat Video (another_cat_video_id) [#cat #animal]"));
    assertThat(lines[3],
        containsString("Funny Dogs (funny_dogs_video_id) [#dog #animal]"));
    assertThat(lines[4],
        containsString("Life at Google (life_at_google_video_id) [#google #career]"));
    assertThat(lines[5],
        containsString("Video about nothing (nothing_video_id) []"));
  }

  @Test
  public void testPlayVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    assertEquals(1, getOutputLines().length);
    assertThat(outputStream.toString(), containsString("Playing video: Amazing Cats"));
  }

  @Test
  public void testPlayVideoNonExistent() {
    videoPlayer.playVideo("some_other_video_that_doesnt_exist");
    assertEquals(1, getOutputLines().length);
    assertThat(outputStream.toString(), containsString("Cannot play video: Video does not exist"));
  }

  @Test
  public void testPlayVideoStopPrevious() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.playVideo("funny_dogs_video_id");

    String[] lines = getOutputLines();
    assertEquals(3, lines.length, outputStream.toString());
    assertThat(lines[0], containsString("Playing video: Amazing Cats"));
    assertThat(lines[1], containsString("Stopping video: Amazing Cats"));
    assertThat(lines[2], containsString("Playing video: Funny Dogs"));
  }

  @Test
  public void testPlayVideoDontStopPreviousIfNonExistent() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.playVideo("some_other_video");

    String[] lines = getOutputLines();
    assertEquals(2, lines.length, outputStream.toString());
    assertThat(lines[0], not(containsString("Stopping video: Amazing Cats")));
    assertThat(lines[1], containsString("Cannot play video: Video does not exist"));
  }

  @Test
  public void testStopVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.stopVideo();

    String[] lines = getOutputLines();
    assertEquals(2, lines.length, outputStream.toString());
    assertThat(lines[0], containsString("Playing video: Amazing Cats"));
    assertThat(lines[1], containsString("Stopping video: Amazing Cats"));
  }

  @Test
  public void testStopVideoTwice() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.stopVideo();
    videoPlayer.stopVideo();

    String[] lines = getOutputLines();
    assertEquals(3, lines.length, outputStream.toString());
    assertThat(lines[0], containsString("Playing video: Amazing Cats"));
    assertThat(lines[1], containsString("Stopping video: Amazing Cats"));
    assertThat(lines[2],
        containsString("Cannot stop video: No video is currently playing"));
  }

  @Test
  public void testStopVideoNothingPlaying() {
    videoPlayer.stopVideo();
    assertEquals(1, getOutputLines().length);
    assertThat(outputStream.toString(),
        containsString("Cannot stop video: No video is currently playing"));
  }

  @Test
  public void testPlayRandomVideo() {
    videoPlayer.playRandomVideo();
    assertEquals(1, getOutputLines().length);
    assertThat(outputStream.toString(), matchesPattern(PLAYING_RANDOM_VIDEO_PATTERN));
  }

  @Test
  public void testPlayRandomVideoStopsPreviousVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.playRandomVideo();

    String[] lines = getOutputLines();
    assertEquals(3, lines.length, outputStream.toString());
    assertThat(lines[0], containsString("Playing video: Amazing Cats"));
    assertThat(lines[1], containsString("Stopping video: Amazing Cats"));
    assertThat(lines[2], matchesPattern(PLAYING_RANDOM_VIDEO_PATTERN));
  }

  @Test
  public void testShowPlaying() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.showPlaying();

    String[] lines = getOutputLines();
    assertEquals(2, lines.length, outputStream.toString());
    assertThat(lines[0], containsString("Playing video: Amazing Cats"));
    assertThat(lines[1],
        containsString("Currently playing: Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
  }

  @Test
  public void testShowNothingPlaying() {
    videoPlayer.showPlaying();
    assertEquals(1, getOutputLines().length);
    assertThat(outputStream.toString(), containsString("No video is currently playing"));
  }

  @Test
  public void testPauseVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.pauseVideo();

    String[] lines = getOutputLines();
    assertEquals(2, lines.length, outputStream.toString());
    assertThat(lines[0], containsString("Playing video: Amazing Cats"));
    assertThat(lines[1], containsString("Pausing video: Amazing Cats"));
  }

  @Test
  public void testPauseVideoShowVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.pauseVideo();
    videoPlayer.showPlaying();

    String[] lines = getOutputLines();
    assertEquals(3, lines.length, outputStream.toString());
    assertThat(lines[0], containsString("Playing video: Amazing Cats"));
    assertThat(lines[1], containsString("Pausing video: Amazing Cats"));
    assertThat(lines[2], containsString(
        "Currently playing: Amazing Cats (amazing_cats_video_id) [#cat #animal] - PAUSED"));
  }

  @Test
  public void testPauseVideoPlayVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.pauseVideo();
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.showPlaying();

    String[] lines = getOutputLines();
    assertEquals(5, lines.length, outputStream.toString());
    assertThat(lines[0], containsString("Playing video: Amazing Cats"));
    assertThat(lines[1], containsString("Pausing video: Amazing Cats"));
    assertThat(lines[2], containsString("Stopping video: Amazing Cats"));
    assertThat(lines[3], containsString("Playing video: Amazing Cats"));
    assertThat(lines[4], not(containsString("PAUSED")));
  }

  @Test
  public void testPauseAlreadyPausedVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.pauseVideo();
    videoPlayer.pauseVideo();

    String[] lines = getOutputLines();
    assertEquals(3, lines.length);
    assertThat(lines[0], containsString("Playing video: Amazing Cats"));
    assertThat(lines[1], containsString("Pausing video: Amazing Cats"));
    assertThat(lines[2], containsString("Video already paused: Amazing Cats"));
  }
  
  @Test
  public void testPauseVideoNothingPlaying() {
    videoPlayer.pauseVideo();
    assertEquals(1, getOutputLines().length);
    assertThat(outputStream.toString(),
        containsString("Cannot pause video: No video is currently playing"));
  }

  @Test
  public void testContinueVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.pauseVideo();
    videoPlayer.continueVideo();

    String[] lines = getOutputLines();
    assertEquals(3, lines.length, outputStream.toString());
    assertThat(lines[0], containsString("Playing video: Amazing Cats"));
    assertThat(lines[1], containsString("Pausing video: Amazing Cats"));
    assertThat(lines[2], containsString("Continuing video: Amazing Cats"));
  }

  @Test
  public void testContinueVideoNotPaused() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.continueVideo();

    String[] lines = getOutputLines();
    assertEquals(2, lines.length, outputStream.toString());
    assertThat(lines[1],
        containsString("Cannot continue video: Video is not paused"));
  }

  @Test
  public void testContinueVideoNothingPlaying() {
    videoPlayer.continueVideo();
    assertEquals(1, getOutputLines().length);
    assertThat(outputStream.toString(),
        containsString("Cannot continue video: No video is currently playing"));
  }
}
