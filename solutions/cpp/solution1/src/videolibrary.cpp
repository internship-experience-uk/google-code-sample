#include "videolibrary.h"

#include <fstream>
#include <iostream>
#include <sstream>
#include <unordered_map>
#include <utility>
#include <vector>

#include "helper.h"
#include "video.h"

VideoLibrary::VideoLibrary() {
  std::ifstream file("./src/videos.txt");
  if (file.is_open()) {
    std::string line;
    while (std::getline(file, line)) {
      std::stringstream linestream(line);
      std::string title;
      std::string id;
      std::string tag;
      std::vector<std::string> tags;
      std::getline(linestream, title, '|');
      std::getline(linestream, id, '|');
      while (std::getline(linestream, tag, ',')) {
        tags.emplace_back(trim(std::move(tag)));
      }
      Video video = Video(trim(std::move(title)), trim(id), std::move(tags));
      mVideos.emplace(trim(std::move(id)), std::move(video));
    }
  } else {
    std::cout << "Couldn't find videos.txt" << std::endl;
  }
}

std::vector<Video> VideoLibrary::getVideos() const {
  std::vector<Video> result;
  for (const auto& video : mVideos) {
    result.emplace_back(video.second);
  }
  return result;
}

const Video* VideoLibrary::getVideo(const std::string& videoId) const {
  const auto found = mVideos.find(videoId);
  if (found == mVideos.end()) {
    return nullptr;
  } else {
    return &(found->second);
  }
}

void VideoLibrary::addNewPlaylist(const std::string& playlistName) const {
  std::string id = toLower(playlistName);
  if (getPlaylist(id) != nullptr) {
    return;
  }
  std::string title = playlistName;
  std::vector<std::string> videoIds;
  VideoPlaylist playlist = VideoPlaylist(
    std::move(title), std::move(videoIds));
  mPlaylists.emplace(trim(std::move(id)), std::move(playlist));
}

void VideoLibrary::addVideoToPlaylist(
  const std::string& playlistName, const std::string& videoId) const {
  std::string id = toLower(playlistName);
  const VideoPlaylist* playlist = getPlaylist(id);
  if (playlist == nullptr) {
    return;
  }
  playlist->addVideo(videoId);
  mPlaylists.emplace(trim(std::move(id)), std::move(*playlist));
}

void VideoLibrary::removeVideoFromPlaylist(
  const std::string& playlistName, const std::string& videoId) const {
  std::string id = toLower(playlistName);
  const VideoPlaylist* playlist = getPlaylist(id);
  if (playlist == nullptr) {
    return;
  }
  playlist->removeVideo(videoId);
  mPlaylists.emplace(trim(std::move(id)), std::move(*playlist));
}

void VideoLibrary::clearPlaylist(const std::string& playlistName) const {
  std::string id = toLower(playlistName);
  const VideoPlaylist* playlist = getPlaylist(id);
  if (playlist == nullptr) {
    return;
  }
  playlist->clear();
  mPlaylists.emplace(trim(std::move(id)), std::move(*playlist));
}

void VideoLibrary::deletePlaylist(const std::string& playlistName) const {
  std::string id = toLower(playlistName);
  const VideoPlaylist* playlist = getPlaylist(id);
  if (playlist == nullptr) {
    return;
  }
  mPlaylists.erase(id);
}

std::vector<VideoPlaylist> VideoLibrary::getPlaylists() const {
  std::vector<VideoPlaylist> result;
  for (const auto& playlist : mPlaylists) {
    result.emplace_back(playlist.second);
  }
  return result;
}

const VideoPlaylist* VideoLibrary::getPlaylist(const std::string& playlistName) const {
  std::string id = toLower(playlistName);
  const auto found = mPlaylists.find(id);
  if (found == mPlaylists.end()) {
    return nullptr;
  } else {
    return &(found->second);
  }
}

bool VideoLibrary::isFlagged(const std::string& id) const {
  const auto found = mFlagged.find(id);
  if (found == mFlagged.end()) {
    return false;
  }
  return true;
}

void VideoLibrary::setFlagged(
  const std::string& id, const std::string& reason) const {
  mFlagged.emplace(trim(std::move(id)), std::move(reason));
}

void VideoLibrary::unsetFlagged(const std::string& id) const {
  mFlagged.erase(id);
};

const std::string VideoLibrary::getFlaggedReason(
  const std::string& id) const {
  const auto found = mFlagged.find(id);
  if (found == mFlagged.end()) {
    return "";
  }
  return found->second;
}
