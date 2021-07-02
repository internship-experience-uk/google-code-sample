#pragma once

#include <string>
#include <unordered_map>
#include <vector>

#include "video.h"
#include "videoplaylist.h"

/**
 * A class used to represent a Video Library.
 */
class VideoLibrary {
 private:
  // Map of available video <id, Video object>.
  std::unordered_map<std::string, Video> mVideos;
  // Map of available playlists <id, Playlist object>
  // Note, playlist id = playlist name in lowercase.
  mutable std::unordered_map<std::string, VideoPlaylist> mPlaylists;
  // Map of flagged video ids <video id, reason due to which video is flagged>.
  mutable std::unordered_map<std::string, std::string> mFlagged;

 public:
  VideoLibrary();

  // This class is not copyable to avoid expensive copies.
  VideoLibrary(const VideoLibrary&) = delete;
  VideoLibrary& operator=(const VideoLibrary&) = delete;

  // This class is movable.
  VideoLibrary(VideoLibrary&&) = default;
  VideoLibrary& operator=(VideoLibrary&&) = default;

  // Returns a vector of all available videos.
  std::vector<Video> getVideos() const;

  // Returns a specific video corresponding to the input videoId
  // if found, else nullptr.
  const Video *getVideo(const std::string& videoId) const;

  // Returns list of all playlists in the library.
  std::vector<VideoPlaylist> getPlaylists() const;

  // Returns a specific playlist corresponding to the input name if found,
  // else nullptr.
  const VideoPlaylist* getPlaylist(const std::string& playlistName) const;

  // Creates and adds a new playlist to video library.
  void addNewPlaylist(const std::string& playlistName) const;

  // Adds a video to a playlist.
  void addVideoToPlaylist(
    const std::string& playlistName, const std::string& videoId) const;

  // Removes a video from a playlist.
  void removeVideoFromPlaylist(
    const std::string& playlistName, const std::string& videoId) const;

  // Removes all videos from a playlist.
  void clearPlaylist(const std::string& playlistName) const;

  // Deletes a playlist from video library.
  void deletePlaylist(const std::string& playlistName) const;

  // Checks if video corresponding to a id is flagged in the library.
  bool isFlagged(const std::string& id) const;

  // Sets a video as flagged in the video library.
  void setFlagged(const std::string& id, const std::string& reason) const;

  // Unflags a video in video library.
  void unsetFlagged(const std::string& id) const;

  // Returns the reason associated with a video being flagged.
  const std::string getFlaggedReason(const std::string& id) const;
};
