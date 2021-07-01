#include "video.h"

#include <iostream>
#include <utility>
#include <vector>

Video::Video(std::string&& title, std::string&& videoId,
             std::vector<std::string>&& tags) :
  mTitle(std::move(title)),
  mVideoId(std::move(videoId)),
  mTags(std::move(tags)) {
}

const std::string& Video::getTitle() const { return mTitle; }

const std::string& Video::getVideoId() const { return mVideoId; }

const std::vector<std::string>& Video::getTags() const { return mTags; }

const std::string Video::getTagsAsStr() const {
    std::string tag_str = "";
    for (const auto& tag : mTags) {
        tag_str = tag_str + tag + " ";
    }
    tag_str = tag_str.substr(0, tag_str.size() - 1);
    return tag_str;
}
