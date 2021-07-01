#include "video.h"

#include "videoplaylist.h"

#include <iostream>
#include <utility>
#include <vector>

VideoPlaylist::VideoPlaylist(
    std::string&& title, std::vector<std::string>&& videoIds) :
  mTitle(std::move(title)),
  mVideoIds(std::move(videoIds)) {
}

const std::string& VideoPlaylist::getTitle() const { return mTitle; }

const std::vector<std::string>& VideoPlaylist::getVideoIds() const {
    return mVideoIds;
}

bool VideoPlaylist::hasVideo(const std::string& videoId) const {
   auto it = std::find(this->mVideoIds.begin(), this->mVideoIds.end(), videoId);
   return it != this->mVideoIds.end(); 
}

void VideoPlaylist::addVideo(const std::string& videoId) const {
    this->mVideoIds.push_back(videoId);
}

void VideoPlaylist::removeVideo(const std::string& videoId) const {
    auto it = std::find(mVideoIds.begin(), mVideoIds.end(), videoId);
    this->mVideoIds.erase(it);
}

void VideoPlaylist::clear() const {
    this->mVideoIds.clear();
}
