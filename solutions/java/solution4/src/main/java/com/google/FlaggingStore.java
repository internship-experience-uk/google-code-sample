package com.google;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** Store for {@link Video} flagging data. */
public class FlaggingStore {

  /**
   * Map containing all flagged videos and the flagging reason.
   *
   * <p>The key of the map is the video ID and the value is the flagging reason.
   */
  private final Map<String, String> flagReason = new HashMap<>();

  /**
   * Gets the flag reason for a video.
   *
   * @param videoId the ID of the video to fetch the flag reason for.
   * @return an optional containing the flag reason if one is present and an empty optional
   *     otherwise.
   */
  public Optional<String> getFlagReason(String videoId) {
    return Optional.ofNullable(flagReason.get(videoId));
  }

  /**
   * Flags a video with a given reason.
   *
   * <p>If the video is already flagged, this existing flag reason will be overwritten with the new
   * flag reason.
   *
   * @param videoId the ID of the video to flag.
   * @param reason the reason the video is being flagged.
   */
  public void flag(String videoId, String reason) {
    flagReason.put(videoId, reason);
  }

  /**
   * Removes a flag for a video.
   *
   * <p>If the video is not flagged, calling this method will have no effect.
   *
   * @param videoId the ID of the video to remove the flag for.
   */
  public void removeFlag(String videoId) {
    flagReason.remove(videoId);
  }
}
