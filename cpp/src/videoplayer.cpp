#include "videoplayer.h"

#include <iostream>

void VideoPlayer::numberOfVideos() {
  std::cout << video_library_.getVideos().size() << " videos in the library"
            << std::endl;
}

void VideoPlayer::showAllVideos() {
  std::cout << "showAllVideos needs implementation" << std::endl;
}

void VideoPlayer::playVideo(std::string const& video_id) {
  std::cout << "playVideo needs implementation" << std::endl;
}

void VideoPlayer::playRandomVideo() {
  std::cout << "playRandomVideo needs implementation" << std::endl;
}

void VideoPlayer::stopVideo() {
  std::cout << "stopVideo needs implementation" << std::endl;
}

void VideoPlayer::pauseVideo() {
  std::cout << "pauseVideo needs implementation" << std::endl;
}

void VideoPlayer::continueVideo() {
  std::cout << "continueVideo needs implementation" << std::endl;
}

void VideoPlayer::showPlaying() {
  std::cout << "showPlaying needs implementation" << std::endl;
}

void VideoPlayer::createPlaylist(std::string const& playlist_name) {
  std::cout << "createPlaylist needs implementation" << std::endl;
}

void VideoPlayer::addVideoToPlaylist(std::string const& playlist_name,
                                     std::string const& video_id) {
  std::cout << "addVideoToPlaylist needs implementation" << std::endl;
}

void VideoPlayer::showPlaylist(std::string const& playlist_name) {
  std::cout << "showPlaylist needs implementation" << std::endl;
}

void VideoPlayer::showAllPlaylists() {
  std::cout << "showAllPlaylists needs implementation" << std::endl;
}

void VideoPlayer::removeFromPlaylist(std::string const& playlist_name,
                                     std::string const& video_id) {
  std::cout << "removeFromPlaylist needs implementation" << std::endl;
}

void VideoPlayer::clearPlaylist(std::string const& playlist_name) {
  std::cout << "clearPlaylist needs implementation" << std::endl;
}

void VideoPlayer::deletePlaylist(std::string const& playlist_name) {
  std::cout << "deletePlaylist needs implementation" << std::endl;
}

void VideoPlayer::searchVideos(std::string const& search_term) {
  std::cout << "searchVideos needs implementation" << std::endl;
}

void VideoPlayer::searchVideosWithTag(std::string const& video_tag) {
  std::cout << "searchVideosWithTag needs implementation" << std::endl;
}

void VideoPlayer::flagVideo(std::string const& video_id) {
  std::cout << "flagVideo needs implementation" << std::endl;
}

void VideoPlayer::flagVideo(std::string const& video_id, std::string const& reason) {
  std::cout << "flagVideo needs implementation" << std::endl;
}

void VideoPlayer::allowVideo(std::string const& video_id) {
  std::cout << "allowVideo needs implementation" << std::endl;
}
