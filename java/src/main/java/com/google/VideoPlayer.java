package com.google;

import java.util.List;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private VideoPlaylist videoPlaylist;
  private String playing;
  private boolean isPaused;
  private Video currPlaying;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.videoPlaylist = new VideoPlaylist();
    this.playing = " ";
    this.isPaused = false;
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    List<Video> videoList = videoLibrary.getVideos();
    // lex sort
    for (int i = 0; i < videoList.size()-1; ++i) {
      for (int j = i + 1; j < videoList.size(); ++j) {
         if (videoList.get(i).getVideoId().compareTo(videoList.get(j).getVideoId()) > 0) {
            Video temp = videoList.get(i);
            videoList.set(i, videoList.get(j));
            videoList.set(j, temp);
         }
      }
   }
   System.out.println("Here's a list of all available videos: ");
    for (Video video: videoList) {
    if (video.getTags().isEmpty()) System.out.println(video.getTitle() +" (" + video.getVideoId() +") []");
    if (!video.getTags().isEmpty()) System.out.println(video.getTitle() +" (" + video.getVideoId() +") " + video.getTags().toString().replace(",", ""));
    }
  }

  public void playVideo(String videoId) {
    if (videoLibrary.getVideo(videoId) == null) System.out.println("Cannot play video: Video does not exist"); 
    if (videoLibrary.getVideo(videoId) != null) {
      if (!playing.equals(" ")) {
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
    if (playing.equals(" ")) System.out.println("Cannot stop video: No video is currently playing ");
    if (!playing.equals(" ")) System.out.println("Stopping video: " + playing);
    isPaused = false;
    playing = " ";
  }

  public void playRandomVideo() {
    int randomVideo = (int) Math.round(Math.random() * (videoLibrary.getVideos().size()-1));
    if (videoLibrary.getVideos().size() == 0) System.out.println("No videos available");
    if (videoLibrary.getVideos().size() > 0) playVideo(videoLibrary.getVideos().get(randomVideo).getVideoId());
    
  }

  public void pauseVideo() {
    if (isPaused == false && !playing.equals(" ") ) System.out.println("Pausing video: " + playing);
    if (isPaused == true) System.out.println("Video already paused: " + playing );
    if (playing.equals(" ")) System.out.println("Cannot pause video: No video is currently playing");
    isPaused = true;
  }

  public void continueVideo() {
    if (isPaused == true && !playing.equals(" ") ) System.out.println("Continuing video: " + playing);
    if (isPaused == false && !playing.equals(" ")) System.out.println("Cannot continue video: Video is not paused");
    if (playing.equals(" ")) System.out.println("Cannot continue video: No video is currently playing");
    isPaused = false;
  }

  public void showPlaying() {
    if (isPaused == false && !playing.equals(" ") ) {
      if (currPlaying.getTags().isEmpty()) {
        System.out.println("Currently playing: " + currPlaying.getTitle() +" (" + currPlaying.getVideoId() +") []");
      } else {
        System.out.println("Currently playing: " + currPlaying.getTitle() +" (" + currPlaying.getVideoId() +") " + currPlaying.getTags().toString().replace(",", ""));
      }
    }

    if (isPaused == true) {
      if (currPlaying.getTags().isEmpty()) {
        System.out.println("Currently playing: " + currPlaying.getTitle() +" (" + currPlaying.getVideoId() +") [] - PAUSED");
      } else {
        System.out.println("Currently playing: " + currPlaying.getTitle() +" (" + currPlaying.getVideoId() +") " + currPlaying.getTags().toString().replace(",", "") + " - PAUSED");
      }
    }
    
    if (playing.equals(" ")) System.out.println("No video is currently playing");
  }

  public void createPlaylist(String playListName) {
    if (videoPlaylist.getPlayLists().stream().noneMatch(a->a.equalsIgnoreCase(playListName))) {
      videoPlaylist.addNewPlaylist(playListName);
      System.out.println("Successfully created new playlist: " + playListName);
    } else {
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    // check playlist exist, vidoes is not added in playlist, video exist
    if (videoLibrary.getVideo(videoId) != null && videoPlaylist.getPlayLists().stream().noneMatch(a->a.equalsIgnoreCase(playlistName)) 
    && !videoPlaylist.getVideosFromPlList(playlistName).contains(videoLibrary.getVideo(videoId)))
      {
      videoPlaylist.addVideoToPlaylist(playlistName, videoId);
      System.out.println("Added video to playlist " + playlistName + ": " + videoLibrary.getVideo(videoId).getTitle());

    } else {
      if (videoPlaylist.getVideosFromPlList(playlistName).contains(videoLibrary.getVideo(videoId))) {
        System.out.println("Cannot add video to " + playlistName + ": Video already added" );
      } 
      if (videoPlaylist.getPlayLists().contains(playlistName)) {
        System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
      }
      if (videoLibrary.getVideo(videoId) == null) {
        System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
      }
    }
  }

  public void showAllPlaylists() {
    if (videoPlaylist.getPlayLists().size() > 0) {
        System.out.println("Showing all playlists:");
        List<String> lexPlaylist = videoPlaylist.getPlayLists();
        // lex sort
        for (int i = 0; i < lexPlaylist.size()-1; ++i) {
          for (int j = i + 1; j < lexPlaylist.size(); ++j) {
            if (lexPlaylist.get(i).compareToIgnoreCase(lexPlaylist.get(j)) > 0) {
                String temp = lexPlaylist.get(i);
                lexPlaylist.set(i, lexPlaylist.get(j));
                lexPlaylist.set(j, temp);
            }
          }
      }

        for (String playlist : lexPlaylist) {
          System.out.println(playlist);
        }
    } else {
      System.out.println("No playlists exist yet");
    }

  }

  public void showPlaylist(String playlistName) {
    if (!videoPlaylist.getPlayLists().stream().noneMatch(a->a.equalsIgnoreCase(playlistName))) {
      System.out.println("Showing playlist: " + playlistName);
      if (videoPlaylist.getVideosFromPlList(playlistName).size() > 0) {
        for (Video playlist : videoPlaylist.getVideosFromPlList(playlistName)) {
          if (playlist.getTags().isEmpty()) {
            System.out.println(playlist.getTitle() +" (" + playlist.getVideoId() +") []");
          } else {
            System.out.println(playlist.getTitle() +" (" + playlist.getVideoId() +") " + playlist.getTags().toString().replace(",", ""));
          }
          }
      } else {
        System.out.println("No videos here yet");
      }
     
    } else {
      System.out.println("Cannot show playlist" + playlistName + ": Playlist does not exist");

    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    if (videoLibrary.getVideo(videoId) != null) {
        if (!videoPlaylist.getPlayLists().stream().noneMatch(a->a.equalsIgnoreCase(playlistName))) {
          if (videoPlaylist.getVideosFromPlList(playlistName).contains(videoLibrary.getVideo(videoId))) {
            videoPlaylist.removeVideo(playlistName, videoId);
            System.out.println("Removed video from " + playlistName + ": " + videoLibrary.getVideo(videoId).getTitle());
          } else {
            System.out.println("Cannot remove video from " + playlistName +": Video is not in playlist");
        }
      } else {
        System.out.println("Cannot remove video from " + playlistName +": Playlist does not exist");
      }
    } else {
      System.out.println("Cannot remove video from " + playlistName +": Video does not exist");
    }
  }

  public void clearPlaylist(String playlistName) {
    if (!videoPlaylist.getPlayLists().stream().noneMatch(a->a.equalsIgnoreCase(playlistName))) {
      videoPlaylist.clearVideos(playlistName);
      System.out.println("Successfully removed all videos from " + playlistName);
    } else {
      System.out.println("Cannot clear playlist " + playlistName +": Playlist does not exist");
    }
  }

  public void deletePlaylist(String playlistName) {
    if (!videoPlaylist.getPlayLists().stream().noneMatch(a->a.equalsIgnoreCase(playlistName))) {
      videoPlaylist.deletePlList(playlistName);
      System.out.println("Deleted playlist: " + playlistName);
    } else {
      System.out.println("Cannot delete playlist " + playlistName +": Playlist does not exist");
    }
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