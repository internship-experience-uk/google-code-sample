package com.google;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashMultimap;

/** A class used to represent a Playlist */
class VideoPlaylist {
    private final List<String> playLists;
    //playlist to prevent duplicate playlists
    private final List<String> searchPlayList;
    private final HashMultimap<String, Video> playListVidoes;
    private final VideoLibrary videoLibrary;

   VideoPlaylist() {
    this.videoLibrary = new VideoLibrary();
    this.playLists = new ArrayList<>(); 
    this.searchPlayList = new ArrayList<>(); 
    this.playListVidoes = HashMultimap.create();
    }
    public void addNewPlaylist(String playListName) {
        playListVidoes.put(playListName, null);
    }
    public List<String> getPlayLists() {
        return new ArrayList<>(this.playListVidoes.keys());
    }
    public List<String> getSearchPlayLists() {
        return new ArrayList<>(this.searchPlayList);
    }
    public List<Video> getVideosFromPlList(String playlistName){
        return new ArrayList<>(this.playListVidoes.get(playlistName));
    }

    public void addVideoToPlaylist(String playListName, String videoId) {
        playListVidoes.put(playListName, videoLibrary.getVideo(videoId));
        playListVidoes.remove(playListName, null);
    }
    public void removeVideo(String playListName, String videoId) {
        this.playListVidoes.remove(playListName, videoLibrary.getVideo(videoId));
    }

    public void clearVideos(String playlistName) {
        playListVidoes.removeAll(playlistName);
        playListVidoes.put(playlistName, null);
    }

    public void deletePlList(String playlistName) {
        this.playListVidoes.removeAll(playlistName);
        this.playListVidoes.remove(playlistName, null);
        playLists.remove(playlistName);
    }
}
