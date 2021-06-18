package com.google;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

public class Part3Test extends TestBase {

  @Test
  public void testSearchVideosWithNoAnswer() {
    setInput("No");

    videoPlayer.searchVideos("cat");
    assertThat(outputStream.toString(), containsString("Here are the results for cat:"));
    assertThat(outputStream.toString(), containsString("1) Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
    assertThat(outputStream.toString(),
        containsString("2) Another Cat Video (another_cat_video_id) [#cat #animal]"));
    assertThat(outputStream.toString(), containsString(
        "Would you like to play any of the above? If yes, specify the number of the video."));
    assertThat(outputStream.toString(),
        containsString("If your answer is not a valid number, we will assume it's a no."));
    assertThat(outputStream.toString(), not(containsString("Playing video")));
  }

  @Test
  public void testSearchVideosAndPlayAnswer() {
    setInput("2");

    videoPlayer.searchVideos("cat");
    assertThat(outputStream.toString(), containsString("Here are the results for cat:"));
    assertThat(outputStream.toString(), containsString("1) Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
    assertThat(outputStream.toString(),
        containsString("2) Another Cat Video (another_cat_video_id) [#cat #animal]"));
    assertThat(outputStream.toString(), containsString(
        "Would you like to play any of the above? If yes, specify the number of the video."));
    assertThat(outputStream.toString(),
        containsString("If your answer is not a valid number, we will assume it's a no."));
    assertThat(outputStream.toString(), containsString("Playing video: Another Cat Video"));
  }

  @Test
  public void testSearchVideosAnswerOutOfBounds() {
    setInput("5");

    videoPlayer.searchVideos("cat");
    assertThat(outputStream.toString(), containsString("Here are the results for cat:"));
    assertThat(outputStream.toString(), containsString("1) Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
    assertThat(outputStream.toString(),
        containsString("2) Another Cat Video (another_cat_video_id) [#cat #animal]"));
    assertThat(outputStream.toString(), containsString(
        "Would you like to play any of the above? If yes, specify the number of the video."));
    assertThat(outputStream.toString(),
        containsString("If your answer is not a valid number, we will assume it's a no."));
    assertThat(outputStream.toString(), not(containsString("Playing video")));
  }

  @Test
  public void testSearchVideosInvalidNumber() {
    setInput("ab3g");
    videoPlayer.searchVideos("cat");
    assertThat(outputStream.toString(), containsString("Here are the results for cat:"));
    assertThat(outputStream.toString(), containsString("1) Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
    assertThat(outputStream.toString(),
        containsString("2) Another Cat Video (another_cat_video_id) [#cat #animal]"));
    assertThat(outputStream.toString(), containsString(
        "Would you like to play any of the above? If yes, specify the number of the video."));
    assertThat(outputStream.toString(),
        containsString("If your answer is not a valid number, we will assume it's a no."));
    assertThat(outputStream.toString(), not(containsString("Playing video")));
  }

  @Test
  public void testSearchVideosNoResults() {
    videoPlayer.searchVideos("blah");
    assertThat(outputStream.toString(), containsString("No search results for blah"));
  }

  @Test
  public void testSearchVideosWithTagNoAnswer() {
    setInput("no");
    videoPlayer.searchVideosWithTag("#cat");
    assertThat(outputStream.toString(), containsString("Here are the results for #cat:"));
    assertThat(outputStream.toString(), containsString("1) Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
    assertThat(outputStream.toString(),
        containsString("2) Another Cat Video (another_cat_video_id) [#cat #animal]"));
    assertThat(outputStream.toString(), containsString(
        "Would you like to play any of the above? If yes, specify the number of the video."));
    assertThat(outputStream.toString(),
        containsString("If your answer is not a valid number, we will assume it's a no."));
    assertThat(outputStream.toString(), not(containsString("Playing video")));
  }

  @Test
  public void testSearchVideosWithTagPlayAnswer() {
    setInput("1");
    videoPlayer.searchVideosWithTag("#cat");
    assertThat(outputStream.toString(), containsString("Here are the results for #cat:"));
    assertThat(outputStream.toString(), containsString("1) Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
    assertThat(outputStream.toString(),
        containsString("2) Another Cat Video (another_cat_video_id) [#cat #animal]"));
    assertThat(outputStream.toString(), containsString(
        "Would you like to play any of the above? If yes, specify the number of the video."));
    assertThat(outputStream.toString(),
        containsString("If your answer is not a valid number, we will assume it's a no."));
    assertThat(outputStream.toString(), containsString("Playing video: Amazing Cats"));
  }

  @Test
  public void testSearchVideosWithTagAnswerOutOfBounds() {
    setInput("5");
    videoPlayer.searchVideosWithTag("#cat");
    assertThat(outputStream.toString(), containsString("Here are the results for #cat:"));
    assertThat(outputStream.toString(), containsString("1) Amazing Cats (amazing_cats_video_id) [#cat #animal]"));
    assertThat(outputStream.toString(),
        containsString("2) Another Cat Video (another_cat_video_id) [#cat #animal]"));
    assertThat(outputStream.toString(), containsString(
        "Would you like to play any of the above? If yes, specify the number of the video."));
    assertThat(outputStream.toString(),
        containsString("If your answer is not a valid number, we will assume it's a no."));
    assertThat(outputStream.toString(), not(containsString("Playing video")));
  }

  @Test
  public void testSearchVideosWithTagNoResults() {
    videoPlayer.searchVideosWithTag("#blah");
    assertThat(outputStream.toString(), containsString("No search results for #blah"));
  }
}
