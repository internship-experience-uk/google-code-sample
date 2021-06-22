package com.google;

import java.util.LinkedList;
import java.util.List;

/** A class used to represent a Playlist */
class VideoPlaylist {

  private final String playlistName;
  private final List<String> videos = new LinkedList<>();

  VideoPlaylist(String playlistName) {
    this.playlistName = playlistName;
  }

  public String getPlaylistName() {
    return playlistName;
  }

  public List<String> getVideos() {
    return videos;
  }
}
