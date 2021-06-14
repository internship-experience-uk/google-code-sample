#include "video.h"

#include <iostream>
#include <utility>
#include <vector>

Video::Video(std::string&& title, std::string&& video_id,
             std::vector<std::string>&& tags) :
  title_(std::move(title)),
  video_id_(std::move(video_id)),
  tags_(std::move(tags)) {
}

std::string const& Video::getTitle() const { return title_; }

std::string const& Video::getVideoId() const { return video_id_; }

std::vector<std::string> const& Video::getTags() const { return tags_; }
