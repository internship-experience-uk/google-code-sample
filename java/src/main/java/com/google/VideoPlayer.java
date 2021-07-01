package com.google;

import java.util.List;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private String playing;
  private boolean isPaused;
  private Video currPlaying;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.playing = " ";
    this.isPaused = false;
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    List<Video> videoList = videoLibrary.getVideos();
    // lex sort
    for(int i = 0; i < videoList.size()-1; ++i) {
      for (int j = i + 1; j < videoList.size(); ++j) {
         if (videoList.get(i).getVideoId().compareTo(videoList.get(j).getVideoId()) > 0) {
            Video temp = videoList.get(i);
            videoList.set(i, videoList.get(j));
            videoList.set(j, temp);
         }
      }
   }
   System.out.println("Here's a list of all available videos: ");
    for(Video video: videoList) {
    if(video.getTags().isEmpty()) System.out.println(video.getTitle() +" (" + video.getVideoId() +") []");
    if(!video.getTags().isEmpty()) System.out.println(video.getTitle() +" (" + video.getVideoId() +") " + video.getTags().toString().replace(",", ""));
    }
  }

  public void playVideo(String videoId) {
    if(videoLibrary.getVideo(videoId) == null) System.out.println("Cannot play video: Video does not exist"); 
    if(videoLibrary.getVideo(videoId) != null) {
      if(!playing.equals(" ")) {
        System.out.println("Stopping video: " + playing);
        System.out.println("Playing video: " + videoLibrary.getVideo(videoId).getTitle());
      } else {
        System.out.println("Playing video: " + videoLibrary.getVideo(videoId).getTitle());
      }
        playing = videoLibrary.getVideo(videoId).getTitle();
        currPlaying = videoLibrary.getVideo(videoId);
        isPaused = false;
    }

  }

  public void stopVideo() {
    if(playing.equals(" ")) System.out.println("Cannot stop video: No video is currently playing ");
    if(!playing.equals(" ")) System.out.println("Stopping video: " + playing);
    isPaused = false;
    playing = " ";
  }

  public void playRandomVideo() {
    int randomVideo = (int) Math.round(Math.random() * (videoLibrary.getVideos().size()-1));
    if(videoLibrary.getVideos().size() == 0) System.out.println("No videos available");
    if(videoLibrary.getVideos().size() > 0) playVideo(videoLibrary.getVideos().get(randomVideo).getVideoId());
    
  }

  public void pauseVideo() {
    if(isPaused == false && !playing.equals(" ") ) System.out.println("Pausing video: " + playing);
    if(isPaused == true) System.out.println("Video already paused: " + playing );
    if(playing.equals(" ")) System.out.println("Cannot pause video: No video is currently playing");
    isPaused = true;
  }

  public void continueVideo() {
    if(isPaused == true && !playing.equals(" ") ) System.out.println("Continuing video: " + playing);
    if(isPaused == false && !playing.equals(" ")) System.out.println("Cannot continue video: Video is not paused");
    if(playing.equals(" ")) System.out.println("Cannot continue video: No video is currently playing");
    isPaused = false;
  }

  public void showPlaying() {
    if(isPaused == false && !playing.equals(" ") ) {
      if(currPlaying.getTags().isEmpty()) System.out.println("Currently playing: " + currPlaying.getTitle() +" (" + currPlaying.getVideoId() +") []");
      if(!currPlaying.getTags().isEmpty()) System.out.println("Currently playing: " + currPlaying.getTitle() +" (" + currPlaying.getVideoId() +") " + currPlaying.getTags().toString().replace(",", ""));
    }
    if(isPaused == true) {
      if(currPlaying.getTags().isEmpty()) System.out.println("Currently playing: " + currPlaying.getTitle() +" (" + currPlaying.getVideoId() +") [] - PAUSED");
      if(!currPlaying.getTags().isEmpty()) System.out.println("Currently playing: " + currPlaying.getTitle() +" (" + currPlaying.getVideoId() +") " + currPlaying.getTags().toString().replace(",", "") + " - PAUSED");
    }
    if(playing.equals(" ")) System.out.println("No video is currently playing");
  }

  public void createPlaylist(String playlistName) {
    System.out.println("createPlaylist needs implementation");
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    System.out.println("addVideoToPlaylist needs implementation");
  }

  public void showAllPlaylists() {
    System.out.println("showAllPlaylists needs implementation");
  }

  public void showPlaylist(String playlistName) {
    System.out.println("showPlaylist needs implementation");
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