package com.google;

import java.util.LinkedHashMap;

/** A class used to represent a Playlist */
class VideoPlaylist {

    String inputName;
    LinkedHashMap<String, Video> videos;

    public VideoPlaylist(String inputName) {
        this.inputName = inputName;
        this.videos = new LinkedHashMap<>();
    }
}
