package com.google;

import java.util.ArrayList;
import java.util.List;

/**
 * A class used to represent a Playlist
 */
class VideoPlaylist implements Comparable<VideoPlaylist> {

  private final String name;
  private final List<Video> videos;

  VideoPlaylist(String name) {
    this.name = name;
    this.videos = new ArrayList<>();
  }

  String getName() {
    return name;
  }

  List<Video> getVideos() {
    return videos;
  }

  @Override
  public int compareTo(VideoPlaylist playlist) {
    return Integer.compare(this.name.compareTo(playlist.name), 0);
  }
}
