package com.google;

import java.sql.Array;
import java.util.*;

/** A class used to represent a Playlist */
class VideoPlaylist {
    String playlistName;
    List<String> videoList;

    public VideoPlaylist(String playlistName) {
        videoList = new ArrayList<>();
        this.playlistName = playlistName;
    }

    public void addVideo(String videoID) {
        videoList.add(videoID);
    }

    public boolean containsVideo(String videoID) {
        return videoList.contains(videoID);
    }

    public String getName() {
        return this.playlistName;
    }

    public void removeVideo(String videoID) {
        videoList.remove(videoID);
    }

    public List<String> getVideoList() {
        return new ArrayList<>(videoList);
    }

    public void clearVideoList() {
        videoList.clear();
    }
}
