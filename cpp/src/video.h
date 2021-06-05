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
        Video(std::string title, std::string videoId, std::vector<std::string> tags);
        
        // Returns the title of the video.
        std::string getTitle();
        
        // Returns the video id of the video.
        std::string getVideoId();
        
        // Returns a readonly collection of the tags of the video.
        std::vector<std::string> getTags();
};

