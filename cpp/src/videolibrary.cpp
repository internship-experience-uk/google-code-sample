#include <iostream>
#include <unordered_map>
#include <vector>
#include <fstream>
#include <sstream>
#include "video.h"
#include "videolibrary.h"
#include "helper.h"

VideoLibrary::VideoLibrary(){
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
                tags.emplace_back(trim(tag));
             }
             Video video = Video(trim(title), trim(id), tags);
             this->videos.insert(std::make_pair(trim(id), video));
        }
    }
    else{
        std::cout << "Couldn't find videos.txt" << std::endl;
    }
      
};

std::vector<Video> VideoLibrary::getVideos() {
 std::vector<Video> videos;
 std::unordered_map<std::string, Video>::iterator it;
 for(it = this->videos.begin(); it != this->videos.end(); it++){
     videos.emplace_back(it->second);
 }
 return videos;
};

Video VideoLibrary::getVideo(std::string videoId) {
   if(this->videos.find(videoId) == this->videos.end()){
       throw std::runtime_error("Video not found in video library");
   } else {
       return this->videos.at(videoId);
   }
};  