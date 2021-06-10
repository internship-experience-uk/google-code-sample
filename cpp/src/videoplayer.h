#pragma once

#include <string>

#include "videolibrary.h"

/**
 * A class used to represent a Video Player.
 */
class VideoPlayer {
 private:
  VideoLibrary videoLibrary;

 public:
  void numberOfVideos();
  void showAllVideos();
  void playVideo(std::string videoId);
  void playRandomVideo();
  void stopVideo();
  void pauseVideo();
  void continueVideo();
  void showPlaying();
  void createPlaylist(std::string playlistName);
  void addVideoToPlaylist(std::string playlistName, std::string videoId);
  void showPlaylist(std::string playlistName);
  void showAllPlaylists();
  void removeFromPlaylist(std::string playlistName, std::string videoId);
  void clearPlaylist(std::string playlistName);
  void deletePlaylist(std::string playlistName);
  void searchVideos(std::string searchTerm);
  void searchVideosWithTag(std::string videoTag);
  void flagVideo(std::string videoId);
  void flagVideo(std::string videoId, std::string reason);
  void allowVideo(std::string videoId);
};
