package com.google;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Part3Test {

  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private VideoPlayer videoPlayer;

  @BeforeEach
  public void setUp() {
    System.setOut(new PrintStream(outputStream));
    videoPlayer = new VideoPlayer();
  }

  @Test
  public void testSearchVideos() {
    videoPlayer.searchVideos("cat");
    assertTrue(outputStream.toString().contains("Here are the results for cat:"));
    assertTrue(outputStream.toString().contains("1) Amazing Cats (amazing_cats_video_id)"));
    assertTrue(outputStream.toString().contains("2) Another Cat Video (another_cat_video_id)"));
    assertTrue(outputStream.toString().contains(
        "Would you like to play any of the above? If yes, specify the number of the video. If your answer is not a valid number, we will assume it's a no."));
  }

  @Test
  public void testSearchVideosNonexistent() {
    videoPlayer.searchVideos("blah");
    assertTrue(outputStream.toString().contains("No search results for blah."));
  }

  @Test
  public void testSearchVideosTag() {
    videoPlayer.showAllVideos();
    assertTrue(outputStream.toString().contains("Here are the results for cat:"));
    assertTrue(outputStream.toString().contains("1) Amazing Cats (amazing_cats_video_id)"));
    assertTrue(outputStream.toString().contains("2) Another Cat Video (another_cat_video_id)"));
    assertTrue(outputStream.toString().contains(
        "Would you like to play any of the above? If yes, specify the number of the video. If your answer is not a valid number, we will assume it's a no."));
  }

  @Test
  public void testSearchVideosTagNonexistant() {
    videoPlayer.searchVideosWithTag("#blah");
    assertTrue(outputStream.toString().contains("No search results for #blah."));
  }
}
