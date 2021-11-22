package com.google;
import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  
  public boolean vidPlaying = false;
  public boolean vidPaused = false;
  String prevVid = "";
  Video currentVid=null;
  LinkedList<String> playlists = new LinkedList<String>();
  
  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");
    List<Video> vids = this.videoLibrary.getVideos();
    // Use a self-implemented comparator to sort videos by their titles.
    Collections.sort(vids, new SortByTitle());
    for (int i=0; i<vids.size();i++){
      Video cVid = vids.get(i);
      
      String name = cVid.getTitle();
      String ident = cVid.getVideoId();
      List<String> tags = cVid.getTags();
      String tagNem = tags.toString().replaceAll(",","");

      System.out.println(name+" ("+ident+") "+tagNem);

    }
  

  }
    
  public void playVideo(String videoId) {
      Video realVid = this.videoLibrary.getVideo(videoId);
      String nem="";
      
      if (realVid!=null){
        while (vidPlaying){
        stopVideo();
        vidPlaying = false;
        }
      
        prevVid = realVid.getTitle();
        currentVid = realVid;
        nem = "Playing video: "+ prevVid;
        vidPlaying = true;
      }
      else{
        nem ="Cannot play video: Video does not exist";

      }
    
    System.out.println(nem);
  }

  public void stopVideo() {

    if(!vidPlaying){
      System.out.println("Cannot stop video: No video is currently playing");
    }
    else{
      if(!prevVid.equals("")){
      System.out.println("Stopping video: "+prevVid);
      vidPlaying=false;
      vidPaused=false;
      }
    }
    
  }

  public void playRandomVideo() {

    List<Video> vids = this.videoLibrary.getVideos();
    Random r = new Random();
        int rndmInt = r.nextInt(vids.size()-1);
        String selectedV = vids.get(rndmInt).getVideoId();
    //get length of vid system and select a random vid tween there and send that to the videoId!!
    playVideo(selectedV);
  }

  public void pauseVideo() {
    if(vidPaused){
      System.out.println("Video already paused: "+prevVid);
    }
    else{
      if(!vidPlaying){
      System.out.println("Cannot pause video: No video is currently playing");
      }
      else{
        vidPaused = true;
        if(!prevVid.equals("")){
        System.out.println("Pausing video: "+prevVid);
        }
      }
    
    }
  }

  public void continueVideo() {
    if(!vidPlaying){
      System.out.println("Cannot continue video: No video is currently playing");
    }
    else{
      if(vidPaused){
      System.out.println("Continuing video: "+prevVid);
    }
    else{
      System.out.println("Cannot continue video: Video is not paused");
    }
    }
    
    
  }

  public void showPlaying() {

    if (vidPlaying){
      String name = currentVid.getTitle();
      String ident = currentVid.getVideoId();
      List<String> tags = currentVid.getTags();
      String tagNem = tags.toString().replaceAll(",","");

      if(vidPaused){
        System.out.println("Currently playing: "+name+" ("+ident+") "+tagNem+" - PAUSED");
      }
      else{
        System.out.println("Currently playing: "+name+" ("+ident+") "+tagNem);
      }
    
    }
    else{
    System.out.println("No video is currently playing");

    }

    
  }

  public void createPlaylist(String playlistName) {
    VideoPlaylist v = new VideoPlaylist();
    System.out.println("Successfully created new playlist: "+ playlistName);
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    System.out.println("addVideoToPlaylist needs implementation");
    //add it deya....loaed the playist and check if it's there already tbh
  }

  public void showAllPlaylists() {
    System.out.println("showAllPlaylists needs implementation");
    // just implement a list of playlists and their names no?
  }

  public void showPlaylist(String playlistName) {
    System.out.println("showPlaylist needs implementation");
    //a list must be assigned to each playlist...to display the videos
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