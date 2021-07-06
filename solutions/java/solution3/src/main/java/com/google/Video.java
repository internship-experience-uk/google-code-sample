package com.google;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/** A class used to represent a video. */
class Video {

  private final String title;
  private final String videoId;
  private final List<String> tags;
  private boolean flagged = false;
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

  public boolean isFlagged() {
    return flagged;
  }

  public void markFlagged(String reason) {
    flagged = true;
    flaggedReason = reason;
  }

  public void unFlag() {
    flagged = false;
    flaggedReason = null;
  }
  public String getFlaggedReason() {
    return flaggedReason;
  }

  public String getFormattedReason() {
    StringBuilder reasonString = new StringBuilder();
    reasonString.append("(reason: ");
    if(flaggedReason==null) {
      reasonString.append("Not supplied");
    } else {
      reasonString.append(flaggedReason);
    }
    reasonString.append(")");
    return reasonString.toString();
  }

  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();
    output.append(
        this.getTitle() + " (" + this.getVideoId() + ") [" + this.getTags().stream()
            .map(Object::toString)
            .collect(Collectors.joining(" ")) + "]");

    if(flagged) {
      output.append(" - FLAGGED (reason: ");
      if(flaggedReason==null) {
        output.append("Not supplied");
      } else {
        output.append(flaggedReason);
      }
      output.append(")");
    }
    return output.toString();
  }
}