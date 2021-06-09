#include "video.h"

#include <iostream>
#include <vector>

Video::Video(std::string&& title, std::string&& videoId,
             std::vector<std::string>&& tags) {
  this->title = title;
  this->videoId = videoId;
  this->tags = tags;
}

std::string const& Video::getTitle() const { return this->title; }

std::string const& Video::getVideoId() const { return this->videoId; }

std::vector<std::string> const& Video::getTags() const { return this->tags; }
