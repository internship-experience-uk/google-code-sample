package com.google;

import java.util.*;

public class VideoPlayer {

  Scanner scanner;

  private final VideoLibrary videoLibrary;
  private Video video;
  private Video playing;
  private Boolean isPaused = false;

  private final List<VideoPlaylist> playlists = new ArrayList<>();
  private List<Video> searchResult; // cannot be final and I need it to always be empty from the start

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  /**
   * DONE
   */
  public void showAllVideos() {
//    System.out.println("showAllVideos needs implementation");
    System.out.println("Here's a list of all available videos:");

    List<Video> tempList = videoLibrary.getVideos();
    tempList.sort(Comparator.naturalOrder());
    for (Video video : tempList) {
      System.out.printf("%s (%s) %s %s\n", video.getTitle(), video.getVideoId(),
          video.getTags().toString().replaceAll(",", ""), video.getIsFlagged() ? "- FLAGGED " +
              "(reason: " + flagReasonHelper(video.getVideoId()) + ")" : "");
    }
  }

  /**
   * DONE
   *
   * @param videoId
   */
  public void playVideo(String videoId) {
//    System.out.println("playVideo needs implementation");
    if (getVideo(videoId) == null) {
      System.out.println("Cannot play video: Video does not exist.");
      return;
    }

    if (getVideo(videoId).getIsFlagged()) {
      System.out.printf("Cannot play video: Video is currently flagged (reason: %s)\n",
          getVideo(videoId).getFlaggedReason() == null ? "Not supplied" : getVideo(videoId).getFlaggedReason());
      return;
    }

    if (isPaused)
      isPaused = false; //if the video exists then it will set isPaused to false as a new video will start playing
    if (playing != null) {
      System.out.printf("Stopping video: %s\n", playing.getTitle());
    }
    playing = getVideo(videoId);
    System.out.printf("Playing video: %s\n", playing.getTitle());
  }

  /**
   * DONE
   */
  public void stopVideo() {
//    System.out.println("stopVideo needs implementation");
    if (playing != null) {
      System.out.printf("Stopping video: %s\n", playing.getTitle());
      playing = null;
      return;
    }

    System.out.println("Cannot stop video: No video is currently playing");
  }

  /**
   * DONE
   */
  public void playRandomVideo() {
//    System.out.println("playRandomVideo needs implementation");

    if (isAllFlaggedHelper()) {
      System.out.println("No videos available");
      return;
    }

    video = videoLibrary.getVideos().get((int)
        Math.floor(Math.random() * videoLibrary.getVideos().size()));


//    if(video.getIsFlagged()) {
//      System.out.printf("Cannot play video: Video is currently flagged (reason: %s)",
//          video.getFlaggedReason() == null ? "Not supplied" : video.getFlaggedReason());
//      return;
//    }

    playVideo(video.getVideoId());
  }

  /**
   * DONE
   */
  public void pauseVideo() {
//    System.out.println("pauseVideo needs implementation");
    if (playing == null) {
      System.out.println("Cannot pause video: No video is currently playing.");
      return;
    }

    if (!isPaused) {
      System.out.printf("Pausing video: %s\n", playing.getTitle());
      isPaused = true;
      return;
    }

    System.out.printf("Video already paused: %s\n", playing.getTitle());
  }

  /**
   * DONE
   */
  public void continueVideo() {
//    System.out.println("continueVideo needs implementation");
    if (playing == null) {
      System.out.println("Cannot continue video: No video is currently playing");
      return;
    }

    if (!isPaused) {
      System.out.println("Cannot continue video: Video is not paused");
      return;
    }

    isPaused = false;
    System.out.printf("Continuing video: %s", playing.getTitle());
  }

  /**
   * DONE
   */
  public void showPlaying() {
//    System.out.println("showPlaying needs implementation");
    if (playing == null) {
      System.out.println("No video is currently playing");
      return;
    }

    System.out.printf("Currently playing: %s (%s) %s %s\n",
        playing.getTitle(), playing.getVideoId(),
        playing.getTags().toString().replaceAll(",", ""),
        (isPaused) ? "- PAUSED" : "");
  }

  /**
   * DONE
   *
   * @param playlistName
   */
  public void createPlaylist(String playlistName) {
//    System.out.println("createPlaylist needs implementation");
    //check for repeated playlists
    if (getPlaylist(playlistName) != null) {
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
      return;
    }

    playlists.add(new VideoPlaylist(playlistName));
    System.out.printf("Successfully created new playlist: %s\n", playlistName);
  }

  /**
   * DONE
   *
   * @param playlistName
   * @param videoId
   */
  public void addVideoToPlaylist(String playlistName, String videoId) {
//    System.out.println("addVideoToPlaylist needs implementation");
//    playlist = getPlaylist(playlistName);
    if (getPlaylist(playlistName) == null) {
      System.out.printf("Cannot add video to %s: Playlist does not exist\n", playlistName);
      return;
    }

    if (getVideo(videoId) == null) {
      System.out.printf("Cannot add video to %s: Video does not exist\n", playlistName);
      return;
    }

    if (getVideo(videoId).getIsFlagged()) {
      System.out.printf("Cannot add video to %s: Video is currently flagged (reason: %s)\n",
          playlistName,
          getVideo(videoId).getFlaggedReason() == null ? "Not supplied" : getVideo(videoId).getFlaggedReason());
      return;
    }

    if (getPlaylist(playlistName).getVideos().contains(getVideo(videoId))) {
      System.out.printf("Cannot add video to %s: Video already added\n", playlistName);
      return;
    }

    getPlaylist(playlistName).getVideos().add(getVideo(videoId));
    System.out.printf("Added video to %s: %s\n", playlistName, getVideo(videoId).getTitle());

  }

  /**
   * DONE
   */
  public void showAllPlaylists() {
//    System.out.println("showAllPlaylists needs implementation");
    if (playlists.size() == 0) {
      System.out.println("No playlists exist yet");
      return;
    }

    System.out.println("Showing all playlists:");
    List<VideoPlaylist> tempList = playlists;
    tempList.sort(Comparator.naturalOrder());
    for (VideoPlaylist videoPlaylist : playlists) {
      System.out.println(videoPlaylist.getName());
    }
  }

  /**
   * DONE
   *
   * @param playlistName
   */
  public void showPlaylist(String playlistName) {
//    System.out.println("showPlaylist needs implementation");
    if (getPlaylist(playlistName) == null) {
      System.out.printf("Cannot show playlist %s: Playlist does not exist\n", playlistName);
      return;
    }

    System.out.printf("Showing playlist: %s (%s videos)\n", playlistName,
        getPlaylist(playlistName).getVideos().size());
    if (getPlaylist(playlistName).getVideos().size() == 0) {
      System.out.println("No videos here yet");
      return;
    }

    for (Video video : getPlaylist(playlistName).getVideos()) {
      System.out.printf("%s (%s) %s %s\n", video.getTitle(), video.getVideoId(),
          video.getTags().toString().replaceAll(",", ""), video.getIsFlagged() ? "- FLAGGED " +
              "(reason: " + flagReasonHelper(video.getVideoId()) + ")" : "");
    }
  }

  /**
   * DONE
   *
   * @param playlistName
   * @param videoId
   */
  public void removeFromPlaylist(String playlistName, String videoId) {
//    System.out.println("removeFromPlaylist needs implementation");
    if (getPlaylist(playlistName) == null) {
      System.out.printf("Cannot remove video from %s: Playlist does not exist\n", playlistName);
      return;
    }

    if (getVideo(videoId) == null) {
      System.out.printf("Cannot remove video from %s: Video does not exist\n", playlistName);
      return;
    }

    if (!getPlaylist(playlistName).getVideos().contains(getVideo(videoId))) {
      System.out.printf("Cannot remove video from %s: Video is not in playlist\n", playlistName);
      return;
    }

    getPlaylist(playlistName).getVideos().remove(getVideo(videoId));
    System.out.printf("Removed video from %s: %s\n", playlistName, getVideo(videoId).getTitle());

  }

  /**
   * DONE
   *
   * @param playlistName
   */
  public void clearPlaylist(String playlistName) {
//    System.out.println("clearPlaylist needs implementation");
    if (getPlaylist(playlistName) == null) {
      System.out.printf("Cannot clear playlist %s: Playlist does not exist\n", playlistName);
      return;
    }

    getPlaylist(playlistName).getVideos().clear();
    System.out.printf("Successfully removed all videos from %s\n", playlistName);
  }

  /**
   * DONE
   *
   * @param playlistName
   */
  public void deletePlaylist(String playlistName) {
//    System.out.println("deletePlaylist needs implementation");
    if (getPlaylist(playlistName) == null) {
      System.out.printf("Cannot delete playlist %s: Playlist does not exist\n", playlistName);
      return;
    }

    playlists.remove(getPlaylist(playlistName));
    System.out.printf("Deleted playlist: %s\n", playlistName);
  }

  /**
   * DONE
   *
   * @param searchTerm
   */
  public void searchVideos(String searchTerm) {
//    System.out.println("searchVideos needs implementation");

    scanner = new Scanner(System.in);
    searchResult = new ArrayList<>();

    for (Video video : videoLibrary.getVideos()) {
      if (video.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) && !video.getIsFlagged()) {
        searchResult.add(video);
      }
    }

    if (searchResult.size() == 0) {
      System.out.printf("No search results for %s", searchTerm);
      return;
    }

    printSearchResult(searchTerm, searchResult);

    System.out.println("Would you like to play any of the above? If yes, specify the number of the video.\n" +
        "If your answer is not a valid number, we will assume it's a no.");

    String input = scanner.next();

    try {
      int option = Integer.parseInt(input);
      if (option > 0 && option <= searchResult.size()) {
        playVideo(searchResult.get(option - 1).getVideoId());
      }
    } catch (NumberFormatException ignore) {
    }

  }

  /**
   * DONE
   *
   * @param videoTag
   */
  public void searchVideosWithTag(String videoTag) {
//    System.out.println("searchVideosWithTag needs implementation");
    scanner = new Scanner(System.in);
    searchResult = new ArrayList<>();

    if (videoTag.startsWith("#")) {
      for (Video video : videoLibrary.getVideos()) {
        for (String tag : videoLibrary.getVideo(video.getVideoId()).getTags()) {
          if (tag.equalsIgnoreCase(videoTag) && !video.getIsFlagged()) {
            searchResult.add(video);
          }
        }
      }
    }

    if (searchResult.size() == 0) {
      System.out.printf("No search results for %s", videoTag);
      return;
    }

    printSearchResult(videoTag, searchResult);

    System.out.println("Would you like to play any of the above? If yes, specify the number of the video.\n" +
        "If your answer is not a valid number, we will assume it's a no.");

    String input = scanner.next();

    try {
      int option = Integer.parseInt(input);
      if (option > 0 && option <= searchResult.size()) {
        playVideo(searchResult.get(option - 1).getVideoId());
      }
    } catch (NumberFormatException ignore) {
    }

  }

  /**
   * DONE
   *
   * @param videoId
   */
  public void flagVideo(String videoId) {
//        System.out.println("flagVideo needs implementation");

    // a return different than 0 will print an error message set on the helper method
    if (flagHelper(videoId) != 0) {
      return;
    }

    if (playing != null && playing.getVideoId().equals(videoId)) {
      stopVideo();
    }

    getVideo(videoId).setIsFlagged(true);
    System.out.printf("Successfully flagged video: %s (reason: Not supplied)\n",
        getVideo(videoId).getTitle());
  }

  /**
   * DONE
   *
   * @param videoId
   * @param reason
   */
  public void flagVideo(String videoId, String reason) {
//        System.out.println("flagVideo needs implementation");
    if (flagHelper(videoId) != 0) {
      return;
    }

    if (playing != null && playing.getVideoId().equals(videoId)) {
      stopVideo();
    }

    getVideo(videoId).setIsFlagged(true);
    getVideo(videoId).setFlaggedReason(reason);

    System.out.printf("Successfully flagged video: %s (reason: %s)\n",
        getVideo(videoId).getTitle(), getVideo(videoId).getFlaggedReason());
  }

  /**
   * DONE
   *
   * @param videoId
   */
  public void allowVideo(String videoId) {
//        System.out.println("allowVideo needs implementation");
    if (getVideo(videoId) == null) {
      System.out.println("Cannot remove flag from video: Video does not exist");
      return;
    }

    if (!getVideo(videoId).getIsFlagged()) {
      System.out.println("Cannot remove flag from video: Video is not flagged");
      return;
    }

    getVideo(videoId).setIsFlagged(false);
    getVideo(videoId).setFlaggedReason(null);

    System.out.printf("Successfully removed flag from video: %s\n", getVideo(videoId).getTitle());
  }


  /**
   * HELPER METHODS
   */
  private Video getVideo(String videoId) {
    for (Video video : videoLibrary.getVideos()) {
      if (video.getVideoId().equals(videoId)) {
        return video;
      }
    }
    return null;
  }

  private VideoPlaylist getPlaylist(String playlistName) {
    for (VideoPlaylist videoPlaylist : playlists) {
      if (playlistName.equalsIgnoreCase(videoPlaylist.getName())) {
        return videoPlaylist;
      }
    }
    return null;
  }

  private void printSearchResult(String term, List<Video> list) {
    searchResult.sort(Comparator.naturalOrder()); //sort the list
    System.out.printf("Here are the results for %s:\n", term);
    for (Video video : list) {
      System.out.printf("%s) %s (%s) %s\n",
          searchResult.indexOf(video) + 1, video.getTitle(), video.getVideoId(),
          video.getTags().toString().replaceAll(",", ""));
    }
  }

  private int flagHelper(String videoId) {
    if (getVideo(videoId) == null) {
      System.out.println("Cannot flag video: Video does not exist");
      return 1;
    }

    if (getVideo(videoId).getIsFlagged()) {
      System.out.println("Cannot flag video: Video is already flagged");
      return 1;
    }

    return 0;
  }

  private Boolean isAllFlaggedHelper() {
    int count = 0;
    for (Video video : videoLibrary.getVideos()) {
      if (!video.getIsFlagged())
        count++;
    }
    return count == 0;
  }

  private String flagReasonHelper(String videoId) {
    if (getVideo(videoId).getFlaggedReason() != null) {
      return getVideo(videoId).getFlaggedReason();
    }

    return "no reason";
  }

  private int sortByTitle(Video v1, Video v2) {
    return v1.getTitle().compareTo(v2.getTitle());
  }
}