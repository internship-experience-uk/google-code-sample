package com.google;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** A class used to represent a Playlist */
class VideoPlaylist {

  private final String name;
  private final ArrayList<Video> videos = new ArrayList();
  private Set<String> videoLookup = new HashSet<>();

  String getName() { return name; }

  public VideoPlaylist(String name) {
    this.name = name;
  }

  public void addVideo(Video v) {
    videos.add(v);
    videoLookup.add(v.getVideoId());
  }

  public boolean removeVideo(Video v) {
    if(containsVideo(v.getVideoId())) {
      videos.remove(v);
      videoLookup.remove(v.getVideoId());
      return true;
    } else {
      return false;
    }
  }

  public void clear() {
    videos.clear();
    videoLookup.clear();
  }

  public boolean containsVideo(String videoId) {
    return videoLookup.contains(videoId);
  }

  List<Video> getVideos() {
    return Collections.unmodifiableList(videos);
  }
}