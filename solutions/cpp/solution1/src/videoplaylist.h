#pragma once

/** A class used to represent a Playlist */

class VideoPlaylist {
 private:
  std::string mTitle;
  mutable std::vector<std::string> mVideoIds;

 public:
  VideoPlaylist(
  	std::string&& title, std::vector<std::string>&& videoIds);

  // Returns the title of the playlist.
  const std::string& getTitle() const;

  // Returns the video ids in the playlist.
  const std::vector<std::string>& getVideoIds() const;

  // Checks if a video is present in a playlist.
  bool hasVideo(const std::string& videoId) const;

  // Adds a new video id to playlist.
  void addVideo(const std::string& videoId) const;

  // Removes a video id from a playlist.
  void removeVideo(const std::string& videoId) const;

  // Clears the playlist.
  void clear() const;
};

