package com.google;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    assertEquals("Here are the results for cat:\n"
            + "1) Amazing Cats (amazing_cats_video_id)\n"
            + "2) Another Cat Video (another_cat_video_id)\n"
            + "Would you like to play any of the above? If yes, specify the number of the video. \n"
            + "If your answer is not a valid number, we will assume it's a no.\n\n"
        , outputStream.toString());

  }

  @Test
  public void testSearchVideosAndPlayAnswer() {
    System.setIn(new ByteArrayInputStream("2\r\n".getBytes()));

    videoPlayer.searchVideos("cat");

    assertEquals("Here are the results for cat:\n"
            + "1) Amazing Cats (amazing_cats_video_id)\n"
            + "2) Another Cat Video (another_cat_video_id)\n"
            + "Would you like to play any of the above? If yes, specify the number of the video. \n"
            + "If your answer is not a valid number, we will assume it's a no.\n\n"
            + "Playing video: Another Cat Video\n"
        , outputStream.toString());
  }

  @Test
  public void testSearchVideosAnswerOutOfBounds() {
    System.setIn(new ByteArrayInputStream("5\r\n".getBytes()));

    videoPlayer.searchVideos("cat");

    assertEquals("Here are the results for cat:\n"
            + "1) Amazing Cats (amazing_cats_video_id)\n"
            + "2) Another Cat Video (another_cat_video_id)\n"
            + "Would you like to play any of the above? If yes, specify the number of the video. \n"
            + "If your answer is not a valid number, we will assume it's a no.\n\n"
        , outputStream.toString());
  }

  @Test
  public void testSearchVideosInvalidNumber() {
    System.setIn(new ByteArrayInputStream("ab3g\r\n".getBytes()));
    videoPlayer.searchVideos("cat");

    assertEquals("Here are the results for cat:\n"
            + "1) Amazing Cats (amazing_cats_video_id)\n"
            + "2) Another Cat Video (another_cat_video_id)\n"
            + "Would you like to play any of the above? If yes, specify the number of the video. \n"
            + "If your answer is not a valid number, we will assume it's a no.\n\n"
        , outputStream.toString());
  }

  @Test
  public void testSearchVideosNoResults() {
    videoPlayer.searchVideos("blah");
    assertEquals("No search results for blah\n", outputStream.toString());
  }

  @Test
  public void testSearchVideosWithTagNoAnswer() {
    System.setIn(new ByteArrayInputStream("no\r\n".getBytes()));
    videoPlayer.searchVideosWithTag("#cat");

    assertEquals("Here are the results for #cat:\n"
            + "1) Amazing Cats (amazing_cats_video_id)\n"
            + "2) Another Cat Video (another_cat_video_id)\n"
            + "Would you like to play any of the above? If yes, specify the number of the video. \n"
            + "If your answer is not a valid number, we will assume it's a no.\n\n"
        , outputStream.toString());
  }

  @Test
  public void testSearchVideosWithTagPlayAnswer() {
    System.setIn(new ByteArrayInputStream("1\r\n".getBytes()));
    videoPlayer.searchVideosWithTag("#cat");

    assertEquals("Here are the results for #cat:\n"
            + "1) Amazing Cats (amazing_cats_video_id)\n"
            + "2) Another Cat Video (another_cat_video_id)\n"
            + "Would you like to play any of the above? If yes, specify the number of the video. \n"
            + "If your answer is not a valid number, we will assume it's a no.\n\n"
            + "Playing video: Amazing Cats\n"
        , outputStream.toString());
  }

  @Test
  public void testSearchVideosWithTagAnswerOutOfBounds() {
    System.setIn(new ByteArrayInputStream("5\r\n".getBytes()));
    videoPlayer.searchVideosWithTag("#cat");

    assertEquals("Here are the results for #cat:\n"
            + "1) Amazing Cats (amazing_cats_video_id)\n"
            + "2) Another Cat Video (another_cat_video_id)\n"
            + "Would you like to play any of the above? If yes, specify the number of the video. \n"
            + "If your answer is not a valid number, we will assume it's a no.\n\n"
        , outputStream.toString());
  }

  @Test
  public void testSearchVideosWithTagNoResults() {
    videoPlayer.searchVideosWithTag("#blah");

    assertEquals("No search results for #blah\n", outputStream.toString());
  }
}
