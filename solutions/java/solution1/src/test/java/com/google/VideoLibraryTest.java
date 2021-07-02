package com.google;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VideoLibraryTest {

  private VideoLibrary videoLibrary;

  @BeforeEach
  public void setUp() {
    videoLibrary = new VideoLibrary();
  }

  @Test
  public void testLibraryHasAllVideos() {
    assertEquals(videoLibrary.getVideos().size(), 5);
  }

  @Test
  public void testLibraryParsesTagsCorrectly() {
    var video = videoLibrary.getVideo("amazing_cats_video_id");

    assertNotNull(video);
    assertEquals("Amazing Cats", video.getTitle());
    assertEquals("amazing_cats_video_id", video.getVideoId());
    assertEquals(new ArrayList<>(List.of("#cat", "#animal")), video.getTags());
  }

  @Test
  public void testLibraryParsesVideoCorrectlyWithoutTags() {
    var video = videoLibrary.getVideo("nothing_video_id");

    assertNotNull(video);
    assertEquals("Video about nothing", video.getTitle());
    assertEquals("nothing_video_id", video.getVideoId());
    assertTrue(video.getTags().isEmpty());
  }
}
