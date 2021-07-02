package com.google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class used to represent a Playlist
 */
class VideoPlaylistLibrary {

  private final HashMap<String, List<VideoPlaylist>> playlists;

  VideoPlaylistLibrary() {
    this.playlists = new HashMap<>();
  }

  void createPlaylist(String name) {
    playlists.put(name, new ArrayList<>());
  }

  List<VideoPlaylist> getPlaylists() {
    return new ArrayList<>(playlists.values().getName());
  }
}