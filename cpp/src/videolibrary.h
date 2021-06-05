#pragma once

#include "video.h"

#include <string>
#include <unordered_map>
#include <vector>

/**
 * A class used to represent a Video Library.
 */
class VideoLibrary {
    private:
        std::unordered_map<std::string, Video> videos;

    public:
        VideoLibrary();
        std::vector<Video> getVideos() const;
        Video const & getVideo(std::string videoId) const;
};
