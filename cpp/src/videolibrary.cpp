#include "helper.h"
#include "video.h"
#include "videolibrary.h"

#include <fstream>
#include <iostream>
#include <sstream>
#include <unordered_map>
#include <vector>

VideoLibrary::VideoLibrary()
{
    std::ifstream file("videos.txt");
    if(file.is_open()){
        std::string line;
        while (std::getline(file, line)) {
            std::stringstream linestream(line);
            std::string title;
            std::string id;
            std::string tag;
            std::vector<std::string> tags;
            std::getline(linestream, title, '|');
            std::getline(linestream, id, '|');
            while(std::getline(linestream, tag, ',')){
                tags.emplace_back(trim(std::move(tag)));
            }
            Video video = Video(trim(std::move(title)), trim(id), std::move(tags));
            this->videos.emplace(trim(std::move(id)), std::move(video));
        }
    }
    else{
        std::cout << "Couldn't find videos.txt" << std::endl;
    }
};

std::vector<Video> VideoLibrary::getVideos() const {
    // lots of copying, but would need a bigger redesign to avoid
    std::vector<Video> result;
    for (auto const & video : this->videos) {
        result.emplace_back(video.second);
    }
    return result;
};

Video const & VideoLibrary::getVideo(std::string videoId) const {
    // the way it was before would do map lookup twice! first for find(),
    // then for at()
    auto const found = this->videos.find(videoId);
    if(found == this->videos.end()) {
        // design-wise this is bad. Throwing an exception is expensive in C++,
        // and should be only for exceptional situations, like system blowing up.
        // User looking for a non-existing video is not an exception, but something
        // that will happen a lot.
        throw std::runtime_error("Video not found in video library");
    } else {
        return found->second;
    }
};
