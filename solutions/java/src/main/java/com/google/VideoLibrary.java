package com.google;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A class used to represent a Video Library.
 */
class VideoLibrary {

  private final HashMap<String, Video> videos;

  // Keep track of flagged videos with mapping from VideoId to the corresponding flagged reason.
  private Map<String, String> flaggedVideos;

  VideoLibrary() {
    this.videos = new HashMap<>();
    flaggedVideos = new HashMap<>();
    try {
      File file = new File(this.getClass().getResource("/videos.txt").getFile());

      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] split = line.split("\\|");
        String title = split[0].strip();
        String id = split[1].strip();
        List<String> tags;
        if (split.length > 2) {
          tags = Arrays.stream(split[2].split(",")).map(String::strip).collect(
              Collectors.toList());
        } else {
          tags = new ArrayList<>();
        }
        this.videos.put(id, new Video(title, id, tags));
      }
    } catch (FileNotFoundException e) {
      System.out.println("Couldn't find videos.txt");
      e.printStackTrace();
    }
  }

  List<Video> getVideos() {
    return new ArrayList<>(this.videos.values());
  }

  // Return a list videos in the library that is not flagged and hence playable.
  // Note this method is not scalable for a large amount of videos because we need to go
  // through all the video each time this method is called. One way to improve it is that
  // we can store the list of playable videos in a field and update it when a video is flagged
  // and un-flagged. However, we will need to store another field in this way. It's a tradeoff
  // between space and runtime complexity.
  List<Video> getPlayableVideos() {
    List<Video> playableVideo = new ArrayList<>();
    // We go through all the videos and only add non-flagged videos to the list.
    for (String videoId: this.videos.keySet()) {
      if (!this.flaggedVideos.containsKey(videoId)) {
        playableVideo.add(this.videos.get(videoId));
      }
    }
    return playableVideo;
  }
  /**
   * Get a video by id. Returns null if the video is not found.
   */
  Video getVideo(String videoId) {
    return this.videos.get(videoId);
  }

  String getFlaggedReason(String videoId) {
    return this.flaggedVideos.get(videoId);
  }

  void flagVideo(String videoId, String reason) {
    this.flaggedVideos.put(videoId, reason);
  }

  boolean isFlagged(String videoId) {
    return this.flaggedVideos.containsKey(videoId);
  }

  void allowVideo(String videoId) {
    this.flaggedVideos.remove(videoId);
  }
}
