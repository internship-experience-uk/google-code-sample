#include "videolibrary.h"

#include <fstream>
#include <iostream>
#include <sstream>
#include <unordered_map>
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
      this->videos.emplace(trim(std::move(id)), std::move(video));
    }
  } else {
    std::cout << "Couldn't find videos.txt" << std::endl;
  }
}

std::vector<Video> VideoLibrary::getVideos() const {
  std::vector<Video> result;
  for (auto const &video : this->videos) {
    result.emplace_back(video.second);
  }
  return result;
}

Video const *VideoLibrary::getVideo(std::string videoId) const {
  auto const found = this->videos.find(videoId);
  if (found == this->videos.end()) {
    std::cout << "Video not found in video library" << std::endl;
    return nullptr;
  } else {
    return &(found->second);
  }
}
