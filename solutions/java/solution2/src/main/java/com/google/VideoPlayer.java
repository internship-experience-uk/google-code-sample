package com.google;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;

  private final Map<String, VideoPlaylist> videoPlaylistMap;

  private Video currentVideo;

  private boolean currentVideoPaused;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.videoPlaylistMap = new HashMap<>();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    List<Video> videos = new ArrayList<>(videoLibrary.getVideos());
    videos.sort(new Comparator<Video>() {
      @Override
      public int compare(Video v1, Video v2) {
        return v1.getTitle().compareTo(v2.getTitle());
      }
    });

    System.out.println("Here's a list of all available videos:");
    for (Video v : videos) {
      System.out.println(v);
    }
  }

  public void playVideo(String videoId) {
    if (videoId == null || !videoLibrary.containsVideo(videoId)) {
      System.out.println("Cannot play video: Video does not exist");
      return;
    }

    Video playVideo = videoLibrary.getVideo(videoId);
    if (currentVideo != null) {
      System.out.println("Stopping video: " + currentVideo.getTitle());
    }
    currentVideo = playVideo;
    currentVideoPaused = false;
    System.out.println("Playing video: " + currentVideo.getTitle());
  }

  public void stopVideo() {
    if (currentVideo == null) {
      System.out.println("Cannot stop video: No video is currently playing");
      return;
    }
    System.out.println("Stopping video: " + currentVideo.getTitle());
    currentVideo = null;
    currentVideoPaused = false;
  }

  public void playRandomVideo() {
    int randomPosition = ThreadLocalRandom.current().nextInt(0, videoLibrary.getVideos().size());

    playVideo(videoLibrary.getVideos().get(randomPosition).getVideoId());
  }

  public void pauseVideo() {
    if (currentVideo == null) {
      System.out.println("Cannot pause video: No video is currently playing");
      return;
    }
    if (currentVideoPaused) {
      System.out.println("Video already paused: " + currentVideo.getTitle());
      return;
    }
    currentVideoPaused = true;
    System.out.println("Pausing video: " + currentVideo.getTitle());
  }

  public void continueVideo() {
    if (currentVideo == null) {
      System.out.println("Cannot continue video: No video is currently playing");
      return;
    }
    if (currentVideoPaused == false) {
      System.out.println("Cannot continue video: Video is not paused");
      return;
    }
    currentVideoPaused = false;
    System.out.println("Continuing video: " + currentVideo.getTitle());
  }

  public void showPlaying() {
    if (currentVideo == null) {
      System.out.println("No video is currently playing");
      return;
    }
    if (currentVideoPaused) {
      System.out.println("Currently playing: " + currentVideo.toString() + " - PAUSED");
      return;
    }
    System.out.println("Currently playing: " + currentVideo.toString());
  }

  public void createPlaylist(String playlistName) {
    if (videoPlaylistMap.containsKey(playlistName.toLowerCase())) {
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
      return;
    }
    VideoPlaylist videoPlaylist = new VideoPlaylist(playlistName);
    videoPlaylistMap.put(playlistName.toLowerCase(), videoPlaylist);
    System.out.println("Successfully created new playlist: " + playlistName);
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    if (!videoPlaylistMap.containsKey(playlistName.toLowerCase())) {
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
      return;
    }
    if (!videoLibrary.containsVideo(videoId)) {
      System.out.println("Cannot add video to "+ playlistName + ": Video does not exist");
      return;
    }
    if (videoPlaylistMap.get(playlistName.toLowerCase()).videos.containsKey(videoId)) {
      System.out.println("Cannot add video to " + playlistName + ": Video already added");
      return;
    }

    videoPlaylistMap.get(playlistName.toLowerCase()).videos.put(videoId, videoLibrary.getVideo(videoId));
    System.out.println("Added video to " + playlistName + ": " + videoLibrary.getVideo(videoId).getTitle());
  }

  public void showAllPlaylists() {
    if (videoPlaylistMap.size() == 0) {
      System.out.println("No playlists exist yet");
      return;
    }
    System.out.println("Showing all playlists:");
    System.out.println(videoPlaylistMap.values().stream().sorted().toString());
  }

  public void showPlaylist(String playlistName) {
    if (!videoPlaylistMap.containsKey(playlistName.toLowerCase())) {
      System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
      return;
    }
    System.out.println("Showing playlist: " + playlistName);
    if (videoPlaylistMap.get(playlistName.toLowerCase()).videos.size() == 0) {
      System.out.println("No videos here yet");
    }
    System.out.println(videoPlaylistMap.get(playlistName.toLowerCase()).videos);
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    if (!videoPlaylistMap.containsKey(playlistName.toLowerCase())) {
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
      return;
    }
    if (!videoLibrary.containsVideo(videoId.toLowerCase())) {
      System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
      return;
    }
    if (!videoPlaylistMap.get(playlistName.toLowerCase()).videos.containsKey(videoId.toLowerCase())) {
      System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
    }
    videoPlaylistMap.get(playlistName.toLowerCase()).videos.remove(videoId);
    System.out.println("Removed video from " + playlistName + ": " + videoLibrary.getVideo(videoId).getTitle());
  }

  public void clearPlaylist(String playlistName) {
    if (!videoPlaylistMap.containsKey(playlistName.toLowerCase())) {
      System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
      return;
    }
    videoPlaylistMap.get(playlistName.toLowerCase()).videos.clear();
    System.out.println("Successfully removed all videos from " + playlistName);
  }

  public void deletePlaylist(String playlistName) {
    if (!videoPlaylistMap.containsKey(playlistName.toLowerCase())) {
      System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");
      return;
    }
    videoPlaylistMap.remove(playlistName.toLowerCase());
    System.out.println("Deleted playlist: " + playlistName);
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