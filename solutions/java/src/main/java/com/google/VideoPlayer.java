package com.google;

import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;

  // Keep track of all the playlists stored in the this VideoPlayer. We map from
  // playlist name in all lowercase to the actual playlist instance.
  private Map<String, VideoPlaylist> playlistMap;

  // Keep track of the currently playing video. It can be at a pause state or playing state.
  private Video currentPlayingVideo;

  // Keep track of the pause state.
  private boolean isPlaying;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.playlistMap = new HashMap<>();
    isPlaying = false;
    currentPlayingVideo = null;
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");
    List<Video> videos = this.videoLibrary.getVideos();
    // Use a self-implemented comparator to sort videos by their titles.
    Collections.sort(videos, new SortByTitle());
    for (Video video:videos) {
      System.out.print("  " + video);
      if (this.videoLibrary.isFlagged(video.getVideoId())) {
        System.out.print(" - FLAGGED (reason: " + this.videoLibrary.getFlaggedReason(video.getVideoId()) + ")");
      }
      System.out.println();
    }
  }

  public void playVideo(String videoId) {
    if (this.videoLibrary.isFlagged(videoId)) {
      String reason = this.videoLibrary.getFlaggedReason(videoId);
      System.out.println("Cannot play video: Video is currently flagged (reason: " + reason + ")");
    } else {
      Video video = this.videoLibrary.getVideo(videoId);
      if (video == null) {
        System.out.println("Cannot play video: Video does not exist");
      } else {
        Video currentVideo = this.currentPlayingVideo;
        if (currentVideo != null) {
          System.out.println("Stopping video: " + currentVideo.getTitle());
        }
        currentPlayingVideo = video;
        isPlaying = true;
        System.out.println("Playing video: " + video.getTitle());
      }
    }
  }

  public void stopVideo() {
    Video video = this.currentPlayingVideo;
    if (video == null) {
      System.out.println("Cannot stop video: No video is currently playing");
    } else {
      currentPlayingVideo = null;
      isPlaying = false;
      System.out.println("Stopping video: " + video.getTitle());
    }
  }

  public void playRandomVideo() {
    Video video = this.currentPlayingVideo;
    if (video != null) {
      System.out.println("Stopping video: " + video.getTitle());
    }
    // We don't want to play any flagged videos and hence fetch the playable videos from
    // the VideoLibrary.
    List<Video> videos = this.videoLibrary.getPlayableVideos();
    Random random = new Random();
    int randIndex = random.nextInt(videos.size());
    Video newVideo = videos.get(randIndex);
    currentPlayingVideo = newVideo;
    isPlaying = true;    
    System.out.println("Playing video: " + newVideo.getTitle());
  }

  public void pauseVideo() {
    Video video = this.currentPlayingVideo;
    if (video == null) {
      System.out.println("Cannot pause video: No video is currently playing");
    } else {
      if (this.isPlaying) {
        System.out.println("Pausing video: " + video.getTitle());
      } else {
        System.out.println("Video already paused: " + video.getTitle());
      }
      this.isPlaying = false;
    }
  }

  public void continueVideo() {
    if (this.isPlaying) {
      System.out.println("Cannot continue video: Video is not paused");
    } else {
      Video video = this.currentPlayingVideo;
      if (video == null) {
        System.out.println("Cannot continue video: No video is currently playing");
      } else {
        System.out.println("Continuing video: " + video.getTitle());
        this.isPlaying = true;
      }
    }
  }

  public void showPlaying() {
    Video video = this.currentPlayingVideo;
    if (video == null) {
      System.out.println("No video is currently playing");
    } else {
      // Use an empty string so we can use only one print statement to print out the
      // currently playing video regardless of the pause status.
      String pauseStatus = "";
      if (!this.isPlaying) {
        pauseStatus = " - PAUSED";
      }
      System.out.println("Currently playing: " + video + pauseStatus);
    }
  }

  public void createPlaylist(String playlistName) {
    // Compare playlist name in lowercase because playlist name is case insensitive.
    if (this.playlistMap.containsKey(playlistName.toLowerCase(Locale.ROOT))) {
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    } else {
      // We keep track of the playlist in lowercase because we want to be able to tell if a playlist
      // exist in a map in a case insensitive way easily.
      this.playlistMap.put(playlistName.toLowerCase(Locale.ROOT), new VideoPlaylist(playlistName));
      System.out.println("Successfully created new playlist: " + playlistName);
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    if (this.videoLibrary.isFlagged(videoId)) {
      String reason = this.videoLibrary.getFlaggedReason(videoId);
      System.out.println("Cannot add video to " + playlistName + ": " +
              "Video is currently flagged (reason: " + reason + ")");
    } else {
      if (!this.playlistMap.containsKey(playlistName.toLowerCase(Locale.ROOT))) {
        System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
      } else if (this.videoLibrary.getVideo(videoId) == null) {
        System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
      } else {
        VideoPlaylist playlist = this.playlistMap.get(playlistName.toLowerCase(Locale.ROOT));
        if (this.videoLibrary.isFlagged(videoId)) {
          System.out.println("Cannot add video to " + playlistName + ": " +
                  "Video is currently flagged (reason: " + this.videoLibrary.getFlaggedReason(videoId) + ")");
        } else if (playlist.containsVideo(videoId)) {
          System.out.println("Cannot add video to " + playlistName + ": Video already added");
        } else {
          playlist.addVideo(videoId);
          System.out.println("Added video to " + playlistName + ": " + this.videoLibrary.getVideo(videoId).getTitle());
        }
      }
    }
  }

  public void showAllPlaylists() {
    if (this.playlistMap.isEmpty()) {
      System.out.println("No playlists exist yet");
    } else {
      System.out.println("Showing all playlists:");
      for (VideoPlaylist playlist: this.playlistMap.values()) {
        System.out.println("  " + playlist.getName());
      }
    }
  }

  public void showPlaylist(String playlistName) {
   if (!this.playlistMap.containsKey(playlistName.toLowerCase(Locale.ROOT))) {
     System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
   } else {
     System.out.println("Showing playlist: " + playlistName);
     VideoPlaylist playlist = this.playlistMap.get(playlistName.toLowerCase(Locale.ROOT));
     List<String> videoList = playlist.getVideoList();
     if (videoList.isEmpty()) {
       System.out.println("  No videos here yet");
     } else {
       for (String videoID: videoList) {
         Video video = this.videoLibrary.getVideo(videoID);
         System.out.print("  " + video);
         if (this.videoLibrary.isFlagged(video.getVideoId())) {
           System.out.print(" - FLAGGED (reason: " + this.videoLibrary.getFlaggedReason(video.getVideoId()) + ")");
         }
         System.out.println();
       }
     }
   }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    if (!this.playlistMap.containsKey(playlistName.toLowerCase(Locale.ROOT))) {
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
    } else if (this.videoLibrary.getVideo(videoId) == null) {
      System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
    } else {
      VideoPlaylist playlist = this.playlistMap.get(playlistName.toLowerCase(Locale.ROOT));
      if (!playlist.containsVideo(videoId)) {
        System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
      } else {
        playlist.removeVideo(videoId);
        System.out.println("Removed video from " + playlistName + ": " + this.videoLibrary.getVideo(videoId).getTitle());
      }
    }
  }

  public void clearPlaylist(String playlistName) {
    if (!this.playlistMap.containsKey(playlistName.toLowerCase(Locale.ROOT))) {
      System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
    } else {
      this.playlistMap.get(playlistName.toLowerCase(Locale.ROOT)).clearVideoList();
      System.out.println("Successfully removed all videos from " + playlistName);
    }
  }

  public void deletePlaylist(String playlistName) {
    if (!this.playlistMap.containsKey(playlistName.toLowerCase(Locale.ROOT))) {
      System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");
    } else {
      this.playlistMap.remove(playlistName.toLowerCase(Locale.ROOT));
      System.out.println("Deleted playlist: " + playlistName);
    }
  }

  public void searchVideos(String searchTerm) {
    List<Video> toPlay = new ArrayList<>();
    for (Video video: videoLibrary.getPlayableVideos()) {
      if (video.getTitle().toLowerCase(Locale.ROOT).contains(searchTerm.toLowerCase(Locale.ROOT))) {
        toPlay.add(video);
      }
    }
    displaySearchResult(searchTerm, toPlay);
  }

  public void searchVideosWithTag(String videoTag) {
    List<Video> toPlay = new ArrayList<>();
    for (Video video: videoLibrary.getPlayableVideos()) {
      if (video.getTags().contains(videoTag)) {
        toPlay.add(video);
      }
    }
    displaySearchResult(videoTag, toPlay);
  }

  /**
   * Display search result in an interactive way based on given parameters searchInput and searchResult.
   * This method is created to avoid redundancy for searchVideos and searchVideosWithTag.
   * @param searchInput: user input for the search
   * @param searchResult: the list of result based on the searchInput
   */
  private void displaySearchResult(String searchInput, List<Video> searchResult) {
    if (searchResult.isEmpty()) {
      System.out.println("No search results for " + searchInput);
    } else {
      System.out.println("Here are the results for " + searchInput + ":");
      searchResult.sort(new SortByTitle());
      int counter = 0;
      for (Video video: searchResult) {
        counter++;
        System.out.println("  " + counter + ") " + video);
      }
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
      System.out.println("If your answer is not a valid number, we will assume it's a no.");
      Scanner scanner = new Scanner(System.in);
      try { // Attempt to parse the user input.
        var input = scanner.nextInt();
        if (input >= 1 && input <= counter) {
          System.out.println("Playing video: " + searchResult.get(input - 1).getTitle());
        }
      } catch (InputMismatchException e) {
        // We do nothing because the spec said ignore anything that is not an integer.
      }
    }
  }

  public void flagVideo(String videoId) {
    flagVideo(videoId, "Not supplied");
  }

  public void flagVideo(String videoId, String reason) {
    Video video = this.videoLibrary.getVideo(videoId);
    if (video == null) {
      System.out.println("Cannot flag video: Video does not exist");
    } else {
      if (this.videoLibrary.isFlagged(videoId)) {
        System.out.println("Cannot flag video: Video is already flagged");
      } else {
        System.out.println("Successfully flagged video: " + video.getTitle() +
                " (reason: " + reason + ")");
        this.videoLibrary.flagVideo(videoId, reason);
        // If it's currently playing a video and someone has flagged it,
        // the flagging also serves as a STOP command and should stop the video immediately.
        // We need to make sure currently playing video is not null before comparing it with the
        // flagged video to avoid NullPointerExceptions.
        if (this.currentPlayingVideo != null && this.currentPlayingVideo.equals(video)) {
          this.currentPlayingVideo = null;
          this.isPlaying = false;
        }
      }
    }
  }

  public void allowVideo(String videoId) {
    Video video = this.videoLibrary.getVideo(videoId);
    if (video == null) {
      System.out.println("Cannot remove flag from video: Video does not exist");
    } else if (!this.videoLibrary.isFlagged(videoId)) {
      System.out.println("Cannot remove flag from video: Video is not flagged");
    } else {
      this.videoLibrary.allowVideo(videoId);
      System.out.println("Successfully removed flag from video: " + video.getTitle());
    }
  }
}