#include <iostream>
#include <vector>
#include "video.h"

Video::Video(std::string title, std::string videoId, std::vector<std::string> tags){
    this->title = title;
    this->videoId = videoId;
    this->tags = tags;
};

std::string Video::getTitle() {
 return this->title;
};
        
std::string Video::getVideoId() {
    return this->videoId;
};
        
std::vector<std::string> Video::getTags() {
    return this->tags;
};

