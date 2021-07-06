package com.google;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class VideoPlayer {

  enum VideoPlayerState {
    PLAYING,
    PAUSED,
    IDLE
  }

  private final VideoLibrary videoLibrary;
  private Video currentlyPlayingVideo;
  private VideoPlayerState state;

  private HashMap<String, VideoPlaylist> playlists = new HashMap<>();

  public VideoPlayer() {
    // The VideoPlayer constructor
    currentlyPlayingVideo = null;
    state = VideoPlayerState.IDLE;
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {

    var allVideos = videoLibrary.getVideos();

    System.out.println("Here's a list of all available videos:");

    allVideos.stream()
        .sorted(Comparator.comparing(Video::getTitle))
        .collect(Collectors.toList())
        .forEach(video -> System.out.println(video));
  }

  public void playVideo(String videoId) {
    Video v = videoLibrary.getVideo(videoId);

    if(v==null) {
      System.out.println("Cannot play video: Video does not exist");
      return;
    }

    if(v.isFlagged()) {
      StringBuilder reasonString = new StringBuilder();
      reasonString.append("Cannot play video: Video is currently flagged");
      reasonString.append(" "+v.getFormattedReason());
      System.out.println(reasonString);
      return;
    }

    if (currentlyPlayingVideo != null) {
      System.out.println("Stopping video: " + currentlyPlayingVideo.getTitle());
    }
    System.out.println("Playing video: " + v.getTitle());
    currentlyPlayingVideo = v;
    state = VideoPlayerState.PLAYING;
  }

  public void stopVideo() {
    if(currentlyPlayingVideo!=null) {
      System.out.println("Stopping video: "+ currentlyPlayingVideo.getTitle());
      currentlyPlayingVideo = null;
      state = VideoPlayerState.IDLE;
    } else {
      System.out.println("Cannot stop video: No video is currently playing");
    }

  }

  public void playRandomVideo() {
    if(currentlyPlayingVideo!=null) {
      stopVideo();
    }
    Random r = new Random();
    var videos = videoLibrary.getVideos().stream().filter(x -> !x.isFlagged()).collect(Collectors.toList());
    if(videos.size()==0) {
      System.out.println("No videos available");
    } else {
      playVideo(videos.get(r.nextInt(videos.size())).getVideoId());
    }
  }

  public void pauseVideo() {
    switch (state) {
      case PLAYING:
        System.out.println("Pausing video: "+ currentlyPlayingVideo.getTitle());
        state = VideoPlayerState.PAUSED;
        break;
      case PAUSED:
        System.out.println("Video already paused: "+ currentlyPlayingVideo.getTitle());
        break;
      case IDLE:
        System.out.println("Cannot pause video: No video is currently playing");
        break;
      default:
        System.out.println("Unknown State Found!");
        break;
    }
  }

  public void continueVideo() {
    switch (state) {
      case PLAYING:
        System.out.println("Cannot continue video: Video is not paused");
        break;
      case PAUSED:
        System.out.println("Continuing video: "+ currentlyPlayingVideo.getTitle());
        break;
      case IDLE:
        System.out.println("Cannot continue video: No video is currently playing");
        break;
      default:
        System.out.println("Unknown State Found!");
        break;
    }
  }

  public void showPlaying() {
    if(currentlyPlayingVideo!=null) {
      StringBuilder currentlyPlayingString = new StringBuilder();
      currentlyPlayingString.append("Currently playing: "+currentlyPlayingVideo);
      if(state == VideoPlayerState.PAUSED) {
        currentlyPlayingString.append(" - PAUSED");
      }
      System.out.println(currentlyPlayingString);
    } else {
      System.out.println("No video is currently playing");
    }
  }

  public void createPlaylist(String playlistName) {
    String playlistLookupKey = playlistName.toLowerCase(Locale.ROOT);
    if(playlists.containsKey(playlistLookupKey)) {
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    } else {
      VideoPlaylist newPlaylist = new VideoPlaylist(playlistName);
      System.out.println("Successfully created new playlist: " +playlistName);
      playlists.put(playlistLookupKey, newPlaylist);
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    String playlistLookupKey = playlistName.toLowerCase(Locale.ROOT);
    Video videoToAdd = videoLibrary.getVideo(videoId);

    if(!playlists.containsKey(playlistLookupKey)) {
      System.out.println("Cannot add video to "+playlistName+": Playlist does not exist");
      return;
    }

    if(videoToAdd==null) {
      System.out.println("Cannot add video to "+playlistName+": Video does not exist");
      return;
    }

    if(videoToAdd.isFlagged()) {
      StringBuilder reasonString = new StringBuilder();
      reasonString.append("Cannot add video to ");
      reasonString.append(playlistName);
      reasonString.append(": Video is currently flagged (reason: ");
      if(videoToAdd.getFlaggedReason()==null) {
        reasonString.append("Not supplied");
      } else {
        reasonString.append(videoToAdd.getFlaggedReason());
      }
      reasonString.append(")");
      System.out.println(reasonString);
      return;
    }

    VideoPlaylist playlist = playlists.get(playlistLookupKey);

    if(playlist.containsVideo(videoId)) {
      System.out.println("Cannot add video to "+playlistName+": Video already added");
      return;
    } else {
      playlist.addVideo(videoToAdd);
      System.out.println("Added video to "+playlistName+": " +videoToAdd.getTitle());
    }

  }

  public void showPlaylist(String playlistName) {
    String playlistLookupKey = playlistName.toLowerCase(Locale.ROOT);
    if (!playlists.containsKey(playlistLookupKey)) {
      System.out.println("Cannot show playlist "+playlistName+": Playlist does not exist");
    } else {
      var playlist = playlists.get(playlistLookupKey);
      System.out.println("Showing playlist: "+playlistName);
      if (playlist.getVideos().size() == 0) {
        System.out.println("No videos here yet");
      }
      for(Video v : playlist.getVideos()) {
        System.out.println(v);
      }
    }
  }
  public void showAllPlaylists() {
    if (playlists.size() == 0) {
      System.out.println("No playlists exist yet");
      return;
    } else {
      System.out.println("Showing all playlists:");
    }

    playlists.entrySet().stream()
        .map(Map.Entry::getValue)
        .map(v -> v.getName())
        .forEach(name -> System.out.println(name));
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    String playlistLookupKey = playlistName.toLowerCase(Locale.ROOT);

    if (!playlists.containsKey(playlistLookupKey)) {
      System.out.println("Cannot remove video from "+playlistName+": Playlist does not exist");
    } else {
      var playlist = playlists.get(playlistLookupKey);

      Video videoToRemove = videoLibrary.getVideo(videoId);
      if(videoToRemove==null) {
        System.out.println("Cannot remove video from "+playlistName+": Video does not exist");
        return;
      }

      if(playlist.removeVideo(videoToRemove)) {
        System.out.println("Removed video from "+playlistName+": " + videoToRemove.getTitle());
      } else {
        System.out.println("Cannot remove video from "+playlistName+": Video is not in playlist");
      }
    }
  }

  public void clearPlaylist(String playlistName) {
    String playlistLookupKey = playlistName.toLowerCase(Locale.ROOT);
    if (!playlists.containsKey(playlistLookupKey)) {
      System.out.println("Cannot clear playlist "+playlistName+": Playlist does not exist");
    } else {
      var playlist = playlists.get(playlistLookupKey);
      playlist.clear();
      System.out.println("Successfully removed all videos from "+playlistName);
    }
  }

  public void deletePlaylist(String playlistName) {
    String playlistLookupKey = playlistName.toLowerCase(Locale.ROOT);
    if (playlists.containsKey(playlistLookupKey)) {
      playlists.remove(playlistLookupKey);
      System.out.println("Deleted playlist: " +playlistName);
    } else {
      System.out.println("Cannot delete playlist "+playlistName+": Playlist does not exist");
    }
  }

  public void displaySearchResults(String searchTerm, List<Video> results) {
    if(results.size()==0) {
      System.out.println("No search results for "+searchTerm);
    } else {
      System.out.println("Here are the results for "+searchTerm+":");
      int count = 1;
      for(Video v : results) {
        System.out.println(count + ") " + v);
        count++;
      }
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video. \n"
          + "If your answer is not a valid number, we will assume it's a no.");
      var scanner = new Scanner(System.in);
      if(scanner.hasNextInt()) {
        var option = scanner.nextInt();
        if(results.size()>=option) {
          Video videoToPlay = results.get(option-1);
          playVideo(videoToPlay.getVideoId());
        }
      }
    }
  }

  public void searchVideos(String searchTerm) {
    List<Video> results = videoLibrary.getVideos().stream()
        .filter(v -> v.getTitle().toLowerCase(Locale.ROOT).contains(searchTerm.toLowerCase(Locale.ROOT)))
        .filter(v -> !v.isFlagged())
        .sorted(Comparator.comparing(Video::getTitle))
        .collect(Collectors.toList());

    displaySearchResults(searchTerm, results);
  }

  public void searchVideosWithTag(String videoTag) {
    List<Video> results = videoLibrary.getVideos().stream()
        .filter(v -> v.getTags().toString().toLowerCase(Locale.ROOT).contains(videoTag.toLowerCase(Locale.ROOT)))
        .filter(v -> !v.isFlagged())
        .sorted(Comparator.comparing(Video::getTitle))
        .collect(Collectors.toList());

    displaySearchResults(videoTag, results);
  }

  public void flagVideo(String videoId) {
    flagVideo(videoId, null);
  }

  public void flagVideo(String videoId, String reason) {
    Video videoToFlag = videoLibrary.getVideo(videoId);

    if(videoToFlag==null) {
      System.out.println("Cannot flag video: Video does not exist");
      return;
    }
    if(videoToFlag.isFlagged()) {
      System.out.println("Cannot flag video: Video is already flagged");
      return;
    }

    if(currentlyPlayingVideo!=null && currentlyPlayingVideo.getVideoId().equals(videoId)) {
      stopVideo();
    }

    videoToFlag.markFlagged(reason);

    StringBuilder reasonString = new StringBuilder();
    reasonString.append("Successfully flagged video: ");
    reasonString.append(videoToFlag.getTitle());
    reasonString.append(" "+videoToFlag.getFormattedReason());
    System.out.println(reasonString);
  }

  public void allowVideo(String videoId) {
    Video videoToUnFlag = videoLibrary.getVideo(videoId);

    if(videoToUnFlag==null) {
      System.out.println("Cannot remove flag from video: Video does not exist");
      return;
    }
    if(!videoToUnFlag.isFlagged()) {
      System.out.println("Cannot remove flag from video: Video is not flagged");
      return;
    }
    videoToUnFlag.unFlag();
    System.out.println("Successfully removed flag from video: " + videoToUnFlag.getTitle());
  }
}