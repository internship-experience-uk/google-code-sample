package com.google;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Part4Test extends TestBase {

  @Test
  public void testFlagVideoWithReason() {
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    assertEquals(1, getOutputLines().length);
    assertThat(outputStream.toString(),
        containsString("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
  }

  @Test
  public void testFlagVideoWithoutReason() {
    videoPlayer.flagVideo("another_cat_video_id");
    assertEquals(1, getOutputLines().length);
    assertThat(outputStream.toString(),
        containsString("Successfully flagged video: Another Cat Video (reason: Not supplied)"));
  }

  @Test
  public void testFlagVideoAlreadyFlagged() {
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");

    var lines = getOutputLines();
    assertEquals(2, lines.length, outputStream.toString());
    assertThat(lines[0],
        containsString("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertThat(lines[1],
        containsString("Cannot flag video: Video is already flagged"));
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

    var lines = getOutputLines();
    assertEquals(2, lines.length, outputStream.toString());
    assertThat(lines[0],
        containsString("Successfully flagged video: Amazing Cats (reason: Not supplied)"));
    assertThat(lines[1],
        containsString("Cannot play video: Video is currently flagged (reason: Not supplied)"));
  }

  @Test
  public void testFlagVideosPlayRandom() {
    videoPlayer.flagVideo("funny_dogs_video_id");
    videoPlayer.flagVideo("amazing_cats_video_id");
    videoPlayer.flagVideo("another_cat_video_id");
    videoPlayer.flagVideo("life_at_google_video_id");
    videoPlayer.flagVideo("nothing_video_id");
    videoPlayer.playRandomVideo();
    var lines = getOutputLines();
    assertEquals(6, lines.length, outputStream.toString());
    assertThat(lines[0],
        containsString("Successfully flagged video: Funny Dogs (reason: Not supplied)"));
    assertThat(lines[1],
        containsString("Successfully flagged video: Amazing Cats (reason: Not supplied)"));
    assertThat(lines[2],
        containsString("Successfully flagged video: Another Cat Video (reason: Not supplied)"));
    assertThat(lines[3],
        containsString("Successfully flagged video: Life at Google (reason: Not supplied)"));
    assertThat(lines[4],
        containsString("Successfully flagged video: Video about nothing (reason: Not supplied)"));
    assertThat(lines[5],
        containsString("No videos available"));
  }

  @Test
  public void testFlagVideoAddVideoToPlaylist() {
    videoPlayer.flagVideo("amazing_cats_video_id");
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");

    var lines = getOutputLines();
    assertEquals(3, lines.length, outputStream.toString());
    assertThat(lines[0],
        containsString("Successfully flagged video: Amazing Cats (reason: Not supplied)"));
    assertThat(lines[1],
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(lines[2], containsString(
        "Cannot add video to my_playlist: Video is currently flagged (reason: Not supplied)"));
  }

  @Test
  public void testFlagVideoShowPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.showPlaylist("my_playlist");

    var lines = getOutputLines();
    assertEquals(5, lines.length, outputStream.toString());
    assertThat(lines[0],
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(lines[1], containsString("Added video to my_playlist: Amazing Cats"));
    assertThat(lines[2],
        containsString("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertThat(lines[3], containsString("Showing playlist: my_playlist"));
    assertThat(lines[4], containsString(
        "Amazing Cats (amazing_cats_video_id) [#cat #animal] - FLAGGED (reason: dont_like_cats)"));
  }

  @Test
  public void testFlagVideoShowAllVideos() {
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.showAllVideos();

    var lines = getOutputLines();
    assertEquals(7, lines.length, outputStream.toString());
    assertThat(lines[0],
        containsString("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertThat(lines[1], containsString("Here's a list of all available videos:"));
    assertThat(lines[2], containsString(
        "Amazing Cats (amazing_cats_video_id) [#cat #animal] - FLAGGED (reason: dont_like_cats)"));
    assertThat(lines[3],
        containsString("Another Cat Video (another_cat_video_id) [#cat #animal]"));
    assertThat(lines[4],
        containsString("Funny Dogs (funny_dogs_video_id) [#dog #animal]"));
    assertThat(lines[5],
        containsString("Life at Google (life_at_google_video_id) [#google #career]"));
    assertThat(lines[6],
        containsString("Video about nothing (nothing_video_id) []"));
  }

  @Test
  public void testFlagVideoSearchVideos() {
    setInput("No");

    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.searchVideos("cat");

    var lines = getOutputLines();
    assertEquals(5, lines.length, outputStream.toString());
    assertThat(lines[0],
        containsString("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertThat(lines[1], containsString("Here are the results for cat:"));
    assertThat(lines[2],
        containsString("1) Another Cat Video (another_cat_video_id) [#cat #animal]"));
    assertThat(lines[3], containsString(
        "Would you like to play any of the above? If yes, specify the number of the video."));
    assertThat(lines[4],
        containsString("If your answer is not a valid number, we will assume it's a no."));
  }

  @Test
  public void testFlagVideoSearchVideosWithTag() {
    setInput("No");

    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.searchVideosWithTag("#cat");

    var lines = getOutputLines();
    assertEquals(5, lines.length, outputStream.toString());
    assertThat(lines[0],
        containsString("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertThat(lines[1], containsString("Here are the results for #cat:"));
    assertThat(lines[2],
        containsString("1) Another Cat Video (another_cat_video_id) [#cat #animal]"));
    assertThat(lines[3], containsString(
        "Would you like to play any of the above? If yes, specify the number of the video."));
    assertThat(lines[4],
        containsString("If your answer is not a valid number, we will assume it's a no."));
  }

  @Test
  public void testFlagVideoStopPlayingVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.showPlaying();

    var lines = getOutputLines();
    assertEquals(4, lines.length, outputStream.toString());
    assertThat(lines[0], containsString("Playing video: Amazing Cats"));
    assertThat(lines[1], containsString("Stopping video: Amazing Cats"));
    assertThat(lines[2],
        containsString("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertThat(lines[3], containsString("No video is currently playing"));
  }

  @Test
  public void testFlagVideoStopPausedVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.pauseVideo();
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.showPlaying();

    var lines = getOutputLines();
    assertEquals(5, lines.length, outputStream.toString());
    assertThat(lines[0], containsString("Playing video: Amazing Cats"));
    assertThat(lines[1], containsString("Pausing video: Amazing Cats"));
    assertThat(lines[2], containsString("Stopping video: Amazing Cats"));
    assertThat(lines[3],
        containsString("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertThat(lines[4], containsString("No video is currently playing"));
  }

  @Test
  public void testFlagVideoKeepVideoPlayingIfDifferentFromFlaggedVideo() {
    videoPlayer.playVideo("amazing_cats_video_id");
    videoPlayer.flagVideo("another_cat_video_id", "dont_like_cats");
    videoPlayer.showPlaying();

    var lines = getOutputLines();
    assertEquals(3, lines.length, outputStream.toString());
    assertThat(lines[0], containsString("Playing video: Amazing Cats"));
    assertThat(lines[1],
        containsString("Successfully flagged video: Another Cat Video (reason: dont_like_cats)"));
    assertThat(lines[2],
        containsString("Currently playing: Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
  }

  @Test
  public void testAllowVideo() {
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.allowVideo("amazing_cats_video_id");

    var lines = getOutputLines();
    assertEquals(2, lines.length, outputStream.toString());
    assertThat(lines[0],
        containsString("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertThat(lines[1],
        containsString("Successfully removed flag from video: Amazing Cats"));
  }

  @Test
  public void testAllowVideoNotFlagged() {
    videoPlayer.allowVideo("amazing_cats_video_id");

    assertEquals(1, getOutputLines().length);
    assertThat(outputStream.toString(),
        containsString("Cannot remove flag from video: Video is not flagged"));
  }

  @Test
  public void testAllowVideoNonexistent() {
    videoPlayer.allowVideo("video_does_not_exist");
    assertEquals(1, getOutputLines().length);
    assertThat(outputStream.toString(),
        containsString("Cannot remove flag from video: Video does not exist"));
  }

  @Test
  public void testAllowVideoShowPlaylist() {
    videoPlayer.createPlaylist("my_playlist");
    videoPlayer.addVideoToPlaylist("my_playlist", "amazing_cats_video_id");
    videoPlayer.flagVideo("amazing_cats_video_id", "dont_like_cats");
    videoPlayer.showPlaylist("my_playlist");
    videoPlayer.allowVideo("amazing_cats_video_id");
    videoPlayer.showPlaylist("my_playlist");

    var lines = getOutputLines();
    assertEquals(8, lines.length, outputStream.toString());
    assertThat(lines[0],
        containsString("Successfully created new playlist: my_playlist"));
    assertThat(lines[1], containsString("Added video to my_playlist: Amazing Cats"));
    assertThat(lines[2],
        containsString("Successfully flagged video: Amazing Cats (reason: dont_like_cats)"));
    assertThat(lines[3], containsString("Showing playlist: my_playlist"));
    assertThat(lines[4], containsString(
        "Amazing Cats (amazing_cats_video_id) [#cat #animal] - FLAGGED (reason: dont_like_cats)"));
    assertThat(lines[5],
        containsString("Successfully removed flag from video: Amazing Cats"));
    assertThat(lines[7],
        containsString("Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
  }
}
