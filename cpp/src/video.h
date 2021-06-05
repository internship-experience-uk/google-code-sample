#pragma once

#include <string>
#include <vector>

/** 
 * A class used to represent a video. 
 */
class Video {
    private:
        std::string title;
        std::string videoId;
        std::vector<std::string> tags;
    public:
        Video(std::string&& title, std::string&& videoId, std::vector<std::string>&& tags);
        
        // Returns the title of the video.
        std::string const & getTitle() const;
        
        // Returns the video id of the video.
        std::string const & getVideoId() const;
        
        // Returns a readonly collection of the tags of the video.
        std::vector<std::string> const & getTags() const;
};

