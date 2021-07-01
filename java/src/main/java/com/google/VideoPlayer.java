package com.google;

import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private boolean playing;
  private String currPlaying = "";
  private List<Queue> allPlaylists = new ArrayList<>();

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

//  public boolean amountCheck() {
//    if (videoLibrary.getVideos().size()==0){
//      System.out.println("There are no videos");
//      return false;
//    }
//    return true;
//  }

  public void showAllVideos() {
    List<Video> v = this.videoLibrary.getVideos();
    for (Video temp : v){
      System.out.println(temp.getTitle() + " " + temp.getVideoId() + " " + temp.getTags());
    }
  }

  public void playVideo(String name) {
    if(videoLibrary.getVideoName(name)==null){
      System.out.println("Video does not exist");
    }
    else{
      if(this.playing){
        System.out.println("Stopping video: " + this.currPlaying);
        System.out.println("Playing vidoe: " + name);
      }
      else{
        System.out.println("Playing vidoe: " + name);
        this.playing = true;
      }
      this.currPlaying = name;
    }
  }

  public void stopVideo() {
    if(this.playing){
      System.out.println("Stopping video: " + this.currPlaying);
      this.playing = false;
    }
    else{
      System.out.println("Cannot stop video: No Video is playing");
    }
  }

  public void playRandomVideo() {
    Random r = new Random();
    List<Video> v = this.videoLibrary.getVideos();
    String name = v.get(r.nextInt(videoLibrary.getVideos().size())).getTitle();
    playVideo(name);
  }

  public void pauseVideo() {
    if (this.playing){
      System.out.println("Pausing Video: " + this.currPlaying);
      this.playing = false;
    }
    else{
      System.out.println("Cannot pause video: no videos currently playing");
    }
  }

  public void continueVideo() {
    if (!this.playing){
      System.out.println("Playing Video: " + this.currPlaying);
      this.playing = true;
    }
    else{
      System.out.println("Cannot continue: no videos paused");
    }
  }

  public void showPlaying() {
    if(this.playing){
      System.out.println("Playing Video: " + videoLibrary.getVidDetails(this.currPlaying) );
    }
    else{
      System.out.println("No video is currently paying");
    }
  }

  public void createPlaylist(String playlistName) {
    if(this.allPlaylists.contains(playlistName)){
      System.out.println("Cannot create playList: a playList with the same name already exits");
    }
    else{
      Queue<String> playlistname = new LinkedList<>();
      this.allPlaylists.add(playlistname);
      System.out.println("Successfully created ");
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    if (this.allPlaylists.contains(playlistName)){
      for (int i = 0; i < this.allPlaylists.size(); i++){
        if (this.allPlaylists.get(i).equals(playlistName)){
          this.allPlaylists.get(i).add(videoId);
          System.out.println("added video to my_playList " + videoId);
        }
      }
    }
    else{
      System.out.println("Cannot add video to " + playlistName + ": video already added");
    }
  }

  public void showAllPlaylists() {
    if (this.allPlaylists.size()>0) {
      for (int i = 0; i < this.allPlaylists.size(); i++) {
        System.out.println(this.allPlaylists.get(i).toString());
      }
    }
    else{
      System.out.println("No playlists exist yet");
    }
  }

  public void showPlaylist(String playlistName) {
    if (this.allPlaylists.contains(playlistName)){
      for (int i = 0; i < this.allPlaylists.size(); i++){
        if (this.allPlaylists.get(i).equals(playlistName)){
          for (int j = 0; j < this.allPlaylists.get(i).size(); j++){
            System.out.println(this.allPlaylists.get(i).[j].toString());
          }
        }
      }
    }
    else{
      System.out.println("Cannot add video to " + playlistName + ": video already added");
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    System.out.println("removeFromPlaylist needs implementation");
  }

  public void clearPlaylist(String playlistName) {
    System.out.println("clearPlaylist needs implementation");
  }

  public void deletePlaylist(String playlistName) {
    System.out.println("deletePlaylist needs implementation");
  }

  public void searchVideos(String searchTerm) {
    System.out.println("searchVideos needs implementation");
  }

  public void searchVideosWithTag(String videoTag) {
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}