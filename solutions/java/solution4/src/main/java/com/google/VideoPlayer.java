package com.google;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toUnmodifiableList;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collector;

public class VideoPlayer {

  /**
   * String joiner which joins strings together into a single string, by inserting a new-line after
   * each string and indents each string by one tab character.
   */
  private static final Collector<CharSequence, ?, String> INDENTED_NEW_LINE_JOINER =
      joining("\n\t", "\t", "\n");
  /** Comparator which can be used to sort videos by title. */
  private static final Comparator<Video> VIDEO_ORDER = comparing(Video::getTitle);

  private final VideoLibrary videoLibrary;
  private final VideoPlaylistStore playlistStore;
  private final FlaggingStore flaggingStore;

  /**
   * The currently playing video.
   *
   * <p>If this variable is set to {@code null}, no video is currently playing.
   */
  private Video currentVideo;

  /** Indicate if the currently playing video is paused or not. */
  private boolean paused = false;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.playlistStore = new VideoPlaylistStore();
    this.flaggingStore = new FlaggingStore();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    var videos = videoLibrary.getVideos();

    // Create an indented string containing all videos in the library, this will simplify output.
    var allVideosString =
        videos.stream()
            .sorted(VIDEO_ORDER)
            .map(this::videoToDisplayString)
            .collect(INDENTED_NEW_LINE_JOINER);

    System.out.printf("Here's a list of all available videos:%n%s%n", allVideosString);
  }

  public void playVideo(String videoId) {
    var video = videoLibrary.getVideo(videoId);

    if (video == null) {
      System.out.println("Cannot play video: Video does not exist");
    } else {
      var flagReason = flaggingStore.getFlagReason(videoId);

      if (flagReason.isEmpty()) {
        if (currentVideo != null) {
          stopVideo();
        }

        System.out.printf("Playing video: %s%n", video.getTitle());
        currentVideo = video;
        paused = false;
      } else {
        System.out.printf(
            "Cannot play video: Video is currently flagged (reason: %s)%n", flagReason.get());
      }
    }
  }

  public void stopVideo() {
    if (currentVideo == null) {
      System.out.println("Cannot stop video: No video is currently playing");
    } else {
      System.out.printf("Stopping video: %s%n", currentVideo.getTitle());
      currentVideo = null;
    }
  }

  public void playRandomVideo() {
    // Create a list of all unflagged videos.
    var videos =
        videoLibrary.getVideos().stream()
            .filter(video -> !isFlagged(video.getVideoId()))
            .collect(toUnmodifiableList());

    if (videos.isEmpty()) {
      System.out.println("No videos available");
    } else {
      // Choose a random video from the list of unflagged videos.
      var random = ThreadLocalRandom.current();
      var video = videos.get(random.nextInt(videos.size()));

      playVideo(video.getVideoId());
    }
  }

  public void pauseVideo() {
    if (currentVideo == null) {
      System.out.println("Cannot pause video: No video is currently playing");
    } else if (paused) {
      System.out.printf("Video already paused: %s%n", currentVideo.getTitle());
    } else {
      System.out.printf("Pausing video: %s%n", currentVideo.getTitle());
      paused = true;
    }
  }

  public void continueVideo() {
    if (currentVideo == null) {
      System.out.println("Cannot continue video: No video is currently playing");
    } else if (paused) {
      System.out.printf("Continuing video: %s%n", currentVideo.getTitle());
      paused = false;
    } else {
      System.out.println("Cannot continue video: Video is not paused");
    }
  }

  public void showPlaying() {
    if (currentVideo == null) {
      System.out.println("No video is currently playing");
    } else {
      System.out.printf(
          "Currently playing: %s" + (paused ? " - PAUSED" : ""), currentVideo.toString());
    }
  }

  public void createPlaylist(String playlistName) {
    if (playlistStore.getPlaylist(playlistName).isPresent()) {
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    } else {
      var playlist = new VideoPlaylist(playlistName);
      playlistStore.savePlaylist(playlist);
      System.out.printf("Successfully created new playlist: %s%n", playlistName);
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    playlistStore
        .getPlaylist(playlistName)
        .ifPresentOrElse(
            playlist -> {
              // The playlist exists.
              var video = videoLibrary.getVideo(videoId);

              if (video == null) {
                System.out.printf("Cannot add video to %s: Video does not exist%n", playlistName);
              } else if (playlist.getVideos().contains(videoId)) {
                System.out.printf("Cannot add video to %s: Video already added%n", playlistName);
              } else {
                var flagReason = flaggingStore.getFlagReason(videoId);

                if (flagReason.isEmpty()) {
                  // The video is not flagged.
                  playlist.getVideos().add(videoId);

                  System.out.printf("Added video to %s: %s%n", playlistName, video.getTitle());
                } else {
                  // The video was flagged.
                  System.out.printf(
                      "Cannot add video to %s: Video is currently flagged (reason: %s)%n",
                      playlistName, flagReason.get());
                }
              }
            },
            () -> // The playlist does not exist
            System.out.printf("Cannot add video to %s: Playlist does not exist", playlistName));
  }

  public void showAllPlaylists() {
    var playlists = playlistStore.getPlaylists();

    if (playlists.isEmpty()) {
      System.out.println("No playlists exist yet");
    } else {
      // Create an indented string containing all playlists for easier output.
      var allPlaylistsString =
          playlists.stream()
              .map(VideoPlaylist::getPlaylistName)
              .sorted()
              .collect(INDENTED_NEW_LINE_JOINER);

      System.out.printf("Showing all playlists:%n%s", allPlaylistsString);
    }
  }

  public void showPlaylist(String playlistName) {
    playlistStore
        .getPlaylist(playlistName)
        .ifPresentOrElse(
            playlist -> {
              // The playlist exists.

              var videosInPlaylistString =
                  playlist.getVideos().isEmpty()
                      ? "\tNo videos here yet\n"
                      : playlist.getVideos().stream()
                          .map(videoLibrary::getVideo)
                          .map(this::videoToDisplayString)
                          .collect(INDENTED_NEW_LINE_JOINER);

              System.out.printf("Showing playlist: %s%n%s", playlistName, videosInPlaylistString);
            },
            () -> // The playlist does not exist.
            System.out.printf("Cannot show playlist %s: Playlist does not exist%n", playlistName));
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    playlistStore
        .getPlaylist(playlistName)
        .ifPresentOrElse(
            playlist -> {
              // The playlist exists.
              var video = videoLibrary.getVideo(videoId);

              if (video == null) {
                System.out.printf(
                    "Cannot remove video from %s: Video does not exist%n", playlistName);
              } else if (!playlist.getVideos().contains(videoId)) {
                System.out.printf(
                    "Cannot remove video from %s: Video is not in playlist%n", playlistName);
              } else {
                playlist.getVideos().remove(videoId);
                System.out.printf("Removed video from %s: %s%n", playlistName, video.getTitle());
              }
            },
            () -> // The playlist does not exist.
            System.out.printf(
                    "Cannot remove video from %s: Playlist does not exist%n", playlistName));
  }

  public void clearPlaylist(String playlistName) {
    playlistStore
        .getPlaylist(playlistName)
        .ifPresentOrElse(
            playlist -> {
              // The playlist exists.
              playlist.getVideos().clear();

              System.out.printf("Successfully removed all videos from %s%n", playlistName);
            },
            () -> // The playlist does not exist.
            System.out.printf("Cannot clear playlist %s: Playlist does not exist%n", playlistName));
  }

  public void deletePlaylist(String playlistName) {
    playlistStore
        .getPlaylist(playlistName)
        .ifPresentOrElse(
            playlist -> {
              // The playlist exists.
              playlistStore.deletePlaylist(playlistName);

              System.out.printf("Deleted playlist: %s%n", playlistName);
            },
            () -> // The playlist does not exist.
            System.out.printf(
                    "Cannot delete playlist %s: Playlist does not exist%n", playlistName));
  }

  public void searchVideos(String searchTerm) {
    var videos = videoLibrary.search(searchTerm);

    displaySearchResults(searchTerm, videos);
  }

  public void searchVideosWithTag(String videoTag) {
    displaySearchResults(videoTag, videoLibrary.searchByTag(videoTag));
  }

  private void displaySearchResults(String searchTerm, List<Video> videos) {
    // Sort all unflagged videos and store them in a list.
    var sortedVideos =
        videos.stream()
            .filter(video -> flaggingStore.getFlagReason(video.getVideoId()).isEmpty())
            .sorted(VIDEO_ORDER)
            .collect(toUnmodifiableList());

    if (sortedVideos.isEmpty()) {
      System.out.printf("No search results for %s%n", searchTerm);
    } else {
      System.out.printf("Here are the results for %s:%n", searchTerm);

      for (int i = 0; i < sortedVideos.size(); i++) {
        System.out.printf("\t%d) %s%n", i + 1, sortedVideos.get(i).toString());
      }

      System.out.println(
          "Would you like to play any of the above? If yes, specify the number of the video.\n"
              + "If your answer is not a valid number, we will assume it's a no.");

      var scanner = new Scanner(System.in);

      // Check if the next characters on the input are an integer number.
      if (scanner.hasNextInt()) {
        // Read the integer and convert it to a list index by subtracting 1.
        int videoNumber = scanner.nextInt() - 1;

        // If the video number is within the range of the list, play the video at that index.
        if (videoNumber >= 0 && videoNumber < sortedVideos.size()) {
          playVideo(sortedVideos.get(videoNumber).getVideoId());
        }
      }

      // Finish reading the rest of the line.
      scanner.nextLine();
    }
  }

  public void flagVideo(String videoId) {
    flagVideo(videoId, "Not supplied");
  }

  public void flagVideo(String videoId, String reason) {
    var video = videoLibrary.getVideo(videoId);
    if (video == null) {
      System.out.println("Cannot flag video: Video does not exist");
    } else if (isFlagged(videoId)) {
      System.out.println("Cannot flag video: Video is already flagged");
    } else {
      if (isCurrentlyPlaying(videoId)) {
        stopVideo();
        currentVideo = null;
      }

      flaggingStore.flag(videoId, reason);

      System.out.printf("Successfully flagged video: %s (reason: %s)%n", video.getTitle(), reason);
    }
  }

  public void allowVideo(String videoId) {
    var video = videoLibrary.getVideo(videoId);
    if (video == null) {
      System.out.println("Cannot remove flag from video: Video does not exist");
    } else if (isFlagged(videoId)) {
      flaggingStore.removeFlag(videoId);

      System.out.printf("Successfully removed flag from video: %s%n", video.getTitle());
    } else {
      System.out.println("Cannot remove flag from video: Video is not flagged");
    }
  }

  /**
   * Return a display string for a video.
   *
   * <p>If the video is not flagged, this will directly return {@link Video#toString()}. If the
   * video is currently flagged, the flagging reason will be appended to the output.
   *
   * @param video the video for which to generate the display string.
   * @return the display string for the video.
   */
  private String videoToDisplayString(Video video) {
    // flaggingStore.getFlagReason() returns an Optional that contains the flag reason, if the video
    // was flagged, or an empty Optional if the video was not flagged.
    //
    // If the Optional contains a value, then the .map() call executes the function passed to it and
    // returns the result of this function wrapped in another Optional. In this case we use this to
    // generate the display string for when a video was flagged. If the first Optional was empty,
    // .map() will just return an emtpy Optional again.
    //
    // Finally, the orElseGet() call also behaves differently based on if it is called on an empty
    // Optional or an Optional containing a value. If the Optional contains a value, .orElseGet()
    // just returns this value directly and ignores the function that was passed to it. If the
    // Optional was empty instead, the function passed to it will be called and the result of this
    // function will be returned. In the case below, the function just calls video.toString() in
    // case no flag reason was found.
    return flaggingStore
        .getFlagReason(video.getVideoId())
        .map(reason -> String.format("%s - FLAGGED (reason: %s)", video.toString(), reason))
        .orElseGet(video::toString);
  }

  private boolean isCurrentlyPlaying(String videoId) {
    return currentVideo != null && currentVideo.getVideoId().equals(videoId);
  }

  private boolean isFlagged(String videoId) {
    return flaggingStore.getFlagReason(videoId).isPresent();
  }
}
