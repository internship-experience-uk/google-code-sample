package com.google;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video. */

/**
 * USER NOTES:
 * Implemented Comparable and compareTo in order to lexicographically order the videos by title.
 */
class Video implements Comparable<Video> {

  private final String title;
  private final String videoId;
  private final List<String> tags;
  private Boolean isFlagged = false;
  private String flaggedReason = null;

  Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);
  }

  /** Returns the title of the video. */
  String getTitle() {
    return title;
  }

  /** Returns the video id of the video. */
  String getVideoId() {
    return videoId;
  }

  /** Returns a readonly collection of the tags of the video. */
  List<String> getTags() {
    return tags;
  }

  /** Sets the isFlagged state of the video **/
  void setIsFlagged(Boolean isFlagged) {
    this.isFlagged = isFlagged;
  }

  /** Returns the isFlagged state of a video **/
  Boolean getIsFlagged() {
    return isFlagged;
  }

  /** Sets the flagging reason for the video **/
  void setFlaggedReason(String flaggedReason) {
    this.flaggedReason = flaggedReason;
  }

  /** Returns the flagging reason for the video **/
  String getFlaggedReason() {
    return flaggedReason;
  }

  @Override
  public int compareTo(Video video) {
//    if(this.getTitle().compareTo(video.getTitle()) > 0) {
//      return 1;
//    } else if(this.getTitle().compareTo(video.getTitle()) < 0) {
//      return -1;
//    } else {
//      return 0;
//    }
    return Integer.compare(this.title.compareTo(video.title), 0);
  }
}
