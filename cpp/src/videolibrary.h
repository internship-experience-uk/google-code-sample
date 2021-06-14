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
  std::unordered_map<std::string, Video> videos_;

 public:
  VideoLibrary();

  // This class is not copyable to avoid expensive copies.
  VideoLibrary(VideoLibrary const&) = delete;
  VideoLibrary& operator=(VideoLibrary const&) = delete;

  // This class is movable.
  VideoLibrary(VideoLibrary&&) = default;
  VideoLibrary& operator=(VideoLibrary&&) = default;

  std::vector<Video> getVideos() const;
  Video const *getVideo(std::string const& video_id) const;
};
