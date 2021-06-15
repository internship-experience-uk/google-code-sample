#pragma once

#include <string>

#include "videolibrary.h"

/**
 * A class used to represent a Video Player.
 */
class VideoPlayer {
 private:
  VideoLibrary video_library_;

 public:
  VideoPlayer() = default;

  // This class is not copyable to avoid expensive copies.
  VideoPlayer(VideoPlayer const&) = delete;
  VideoPlayer& operator=(VideoPlayer const&) = delete;

  // This class is movable.
  VideoPlayer(VideoPlayer&&) = default;
  VideoPlayer& operator=(VideoPlayer&&) = default;

  void numberOfVideos();
  void showAllVideos();
  void playVideo(std::string const& video_id);
  void playRandomVideo();
  void stopVideo();
  void pauseVideo();
  void continueVideo();
  void showPlaying();
  void createPlaylist(std::string const& playlist_name);
  void addVideoToPlaylist(std::string const& playlist_name, std::string const& video_id);
  void showPlaylist(std::string const& playlist_name);
  void showAllPlaylists();
  void removeFromPlaylist(std::string const& playlist_name, std::string const& video_id);
  void clearPlaylist(std::string const& playlist_name);
  void deletePlaylist(std::string const& playlist_name);
  void searchVideos(std::string const& search_term);
  void searchVideosWithTag(std::string const& video_tag);
  void flagVideo(std::string const& video_id);
  void flagVideo(std::string const& video_id, std::string const& reason);
  void allowVideo(std::string const& video_id);
};
