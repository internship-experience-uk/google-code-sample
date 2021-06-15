#include "videolibrary.h"

#include <fstream>
#include <iostream>
#include <sstream>
#include <unordered_map>
#include <utility>
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
      mVideos.emplace(trim(std::move(id)), std::move(video));
    }
  } else {
    std::cout << "Couldn't find videos.txt" << std::endl;
  }
}

std::vector<Video> VideoLibrary::getVideos() const {
  std::vector<Video> result;
  for (const auto& video : mVideos) {
    result.emplace_back(video.second);
  }
  return result;
}

const Video* VideoLibrary::getVideo(const std::string& videoId) const {
  const auto found = mVideos.find(videoId);
  if (found == mVideos.end()) {
    std::cout << "Video not found in video library" << std::endl;
    return nullptr;
  } else {
    return &(found->second);
  }
}
