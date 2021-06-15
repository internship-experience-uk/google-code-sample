#pragma once

#include <string>
#include <unordered_map>
#include <vector>

#include "video.h"

/**
 * A class used to represent a Video Library.
 */
class VideoLibrary {
 private:
  std::unordered_map<std::string, Video> mVideos;

 public:
  VideoLibrary();

  // This class is not copyable to avoid expensive copies.
  VideoLibrary(const VideoLibrary&) = delete;
  VideoLibrary& operator=(const VideoLibrary&) = delete;

  // This class is movable.
  VideoLibrary(VideoLibrary&&) = default;
  VideoLibrary& operator=(VideoLibrary&&) = default;

  std::vector<Video> getVideos() const;
  const Video *getVideo(const std::string& videoId) const;
};
