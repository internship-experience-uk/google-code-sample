package com.google;

import static java.util.Locale.ROOT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Store for video playlists. */
public class VideoPlaylistStore {

  /**
   * Map containing the playlists.
   *
   * <p>The playlists are stored using a unique playlist ID as key and the playlist itself as value.
   */
  private final Map<String, VideoPlaylist> playlists = new HashMap<>();

  /**
   * Gets the given playlist.
   *
   * @param playlistName the unique name of the playlist to return.
   * @return an optional containing the playlist if a playlist with the given ID exists, or an empty
   *     optional otherwise.
   */
  public Optional<VideoPlaylist> getPlaylist(String playlistName) {
    return Optional.ofNullable(playlists.get(getPlaylistId(playlistName)));
  }

  /**
   * Deletes a playlist.
   *
   * <p>If no playlist with the given playlist name exists, calling this method will have no effect.
   *
   * @param playlistName the unique name of the playlist.
   */
  public void deletePlaylist(String playlistName) {
    playlists.remove(getPlaylistId(playlistName));
  }

  /**
   * Saves a playlist.
   *
   * <p>If a playlist with the given unique playlist name already exists, it will be overwritten.
   *
   * @param playlist the playlist to save.
   */
  public void savePlaylist(VideoPlaylist playlist) {
    playlists.put(getPlaylistId(playlist.getPlaylistName()), playlist);
  }

  /**
   * Return a unique playlist ID based on the playlist name.
   *
   * <p>This method converts the playlist name to uppercase to obtain the playlist ID. This ensures
   * that the playlist name always resolves to the same playlist ID, regardless of the case used in
   * the playlist name.
   *
   * @param playlistName the unique playlist name
   * @return the playlist ID for the given playlist name.
   */
  private String getPlaylistId(String playlistName) {
    return playlistName.toUpperCase(ROOT);
  }

  /**
   * Returns an immutable list of all stored playlists.
   *
   * @return an immutable list of all stored playlists.
   */
  public List<VideoPlaylist> getPlaylists() {
    // List.copyOf() is used to create a defensive, immutable copy of all the playlists.
    return List.copyOf(playlists.values());
  }
}
