package com.google;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Part3Test {

  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private VideoPlayer videoPlayer;

  private InputStream stdin;

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
  public void testSearchVideosWithNoAnswer() {
    System.setIn(new ByteArrayInputStream("No\r\n".getBytes()));

    videoPlayer.searchVideos("cat");
    assertThat(outputStream.toString(), containsString("Here are the results for cat:"));
    assertThat(outputStream.toString(), containsString("1) Amazing Cats (amazing_cats_video_id)"));
    assertThat(outputStream.toString(),
        containsString("2) Another Cat Video (another_cat_video_id)"));
    assertThat(outputStream.toString(), containsString(
        "Would you like to play any of the above? If yes, specify the number of the video."));
    assertThat(outputStream.toString(),
        containsString("If your answer is not a valid number, we will assume it's a no."));
    assertThat(outputStream.toString(), not(containsString("Playing video")));
  }

  @Test
  public void testSearchVideosAndPlayAnswer() {
    System.setIn(new ByteArrayInputStream("2\r\n".getBytes()));

    videoPlayer.searchVideos("cat");
    assertThat(outputStream.toString(), containsString("Here are the results for cat:"));
    assertThat(outputStream.toString(), containsString("1) Amazing Cats (amazing_cats_video_id)"));
    assertThat(outputStream.toString(),
        containsString("2) Another Cat Video (another_cat_video_id)"));
    assertThat(outputStream.toString(), containsString(
        "Would you like to play any of the above? If yes, specify the number of the video."));
    assertThat(outputStream.toString(),
        containsString("If your answer is not a valid number, we will assume it's a no."));
    assertThat(outputStream.toString(), containsString("Playing video: Another Cat Video"));
  }

  @Test
  public void testSearchVideosAnswerOutOfBounds() {
    System.setIn(new ByteArrayInputStream("5\r\n".getBytes()));

    videoPlayer.searchVideos("cat");
    assertThat(outputStream.toString(), containsString("Here are the results for cat:"));
    assertThat(outputStream.toString(), containsString("1) Amazing Cats (amazing_cats_video_id)"));
    assertThat(outputStream.toString(),
        containsString("2) Another Cat Video (another_cat_video_id)"));
    assertThat(outputStream.toString(), containsString(
        "Would you like to play any of the above? If yes, specify the number of the video."));
    assertThat(outputStream.toString(),
        containsString("If your answer is not a valid number, we will assume it's a no."));
    assertThat(outputStream.toString(), not(containsString("Playing video")));
  }

  @Test
  public void testSearchVideosInvalidNumber() {
    System.setIn(new ByteArrayInputStream("ab3g\r\n".getBytes()));
    videoPlayer.searchVideos("cat");
    assertThat(outputStream.toString(), containsString("Here are the results for cat:"));
    assertThat(outputStream.toString(), containsString("1) Amazing Cats (amazing_cats_video_id)"));
    assertThat(outputStream.toString(),
        containsString("2) Another Cat Video (another_cat_video_id)"));
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
    System.setIn(new ByteArrayInputStream("no\r\n".getBytes()));
    videoPlayer.searchVideosWithTag("#cat");
    assertThat(outputStream.toString(), containsString("Here are the results for #cat:"));
    assertThat(outputStream.toString(), containsString("1) Amazing Cats (amazing_cats_video_id)"));
    assertThat(outputStream.toString(),
        containsString("2) Another Cat Video (another_cat_video_id)"));
    assertThat(outputStream.toString(), containsString(
        "Would you like to play any of the above? If yes, specify the number of the video."));
    assertThat(outputStream.toString(),
        containsString("If your answer is not a valid number, we will assume it's a no."));
    assertThat(outputStream.toString(), not(containsString("Playing video")));
  }

  @Test
  public void testSearchVideosWithTagPlayAnswer() {
    System.setIn(new ByteArrayInputStream("1\r\n".getBytes()));
    videoPlayer.searchVideosWithTag("#cat");
    assertThat(outputStream.toString(), containsString("Here are the results for #cat:"));
    assertThat(outputStream.toString(), containsString("1) Amazing Cats (amazing_cats_video_id)"));
    assertThat(outputStream.toString(),
        containsString("2) Another Cat Video (another_cat_video_id)"));
    assertThat(outputStream.toString(), containsString(
        "Would you like to play any of the above? If yes, specify the number of the video."));
    assertThat(outputStream.toString(),
        containsString("If your answer is not a valid number, we will assume it's a no."));
    assertThat(outputStream.toString(), containsString("Playing video: Amazing Cats"));
  }

  @Test
  public void testSearchVideosWithTagAnswerOutOfBounds() {
    System.setIn(new ByteArrayInputStream("5\r\n".getBytes()));
    videoPlayer.searchVideosWithTag("#cat");
    assertThat(outputStream.toString(), containsString("Here are the results for #cat:"));
    assertThat(outputStream.toString(), containsString("1) Amazing Cats (amazing_cats_video_id)"));
    assertThat(outputStream.toString(),
        containsString("2) Another Cat Video (another_cat_video_id)"));
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
