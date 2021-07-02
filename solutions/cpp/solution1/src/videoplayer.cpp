#include "videoplayer.h"

#include "video.h"
#include "videoplaylist.h"
#include "helper.h"

#include <iostream>

// A custom sort function to sort video objects using title.
bool SortByTitle(Video v1, Video v2) {
 // if str1.compare(str2) < 0 => str1 comes before str2 lexicographically.
 return v1.getTitle().compare(v2.getTitle()) < 0;
}

void VideoPlayer::numberOfVideos() {
  std::cout << mVideoLibrary.getVideos().size() << " videos in the library"
            << std::endl;
}

void VideoPlayer::showAllVideos() {
  std::cout << "Here's a list of all available videos:" << std::endl;
  // Get all available videos.
  auto videos = mVideoLibrary.getVideos();
  // Sort videos by title.
  std::sort(videos.begin(), videos.end(), SortByTitle);
  // Iterate over all videos and display the title, id and tags.
  // If the video is flagged, display the reason of being flagged as well.
  for (const auto& video : videos) {
    std::cout << video.getTitle() << " ";
    std::cout << "(" << video.getVideoId() << ") ";
    std::cout << "[" << video.getTagsAsStr() << "]";
    if (mVideoLibrary.isFlagged(video.getVideoId())){
      std::cout << " - FLAGGED (reason: ";
      std::cout << mVideoLibrary.getFlaggedReason(video.getVideoId()) << ")";
    }
    std::cout << std::endl;
  }
}

void VideoPlayer::playVideo(const std::string& videoId) {
  // Get the video using video id.
  const auto* video = mVideoLibrary.getVideo(videoId);
  // If video is nullptr, it denotes that no such video exists.
  if (video == nullptr) {
    std::cout << "Cannot play video: Video does not exist" << std::endl;
    return;
  }
  // If video is flagged, display that flagged video cannot be played.
  if (mVideoLibrary.isFlagged(video->getVideoId())) {
    std::cout << "Cannot play video: Video is currently flagged (reason: " ;
    std::cout << mVideoLibrary.getFlaggedReason(video->getVideoId()) <<")" << std::endl;
    return;
  }
  // If any video is already playing, stop it before playing the new video.
  if (!playingId.empty()) {
    stopVideo();
  }
  // Set the playingId to video id and play the video.
  playingId = video->getVideoId();
  // pausedId is emptied since no video is paused once we play a new video.
  pausedId = "";
  std::cout << "Playing video: " << video->getTitle() << std::endl;
}

void VideoPlayer::stopVideo() {
  // If playingId is empty, then no video is playing and
  // there is nothing to stop.
  if (playingId.empty()) {
    std::cout << "Cannot stop video: No video is currently playing" << std::endl;
    return;
  }
  // Get the video object which is currently playing using playingId.
  const auto& video = mVideoLibrary.getVideo(playingId);
  // Empty the playingId and stop the video.
  playingId = "";
  std::cout << "Stopping video: " << video->getTitle() << std::endl;
}

void VideoPlayer::playRandomVideo() {
  // Get all available videos.
  const auto& videos = mVideoLibrary.getVideos();

  // Obtain all unflagged videos since we cannot play a flagged video.
  std::vector<Video> unflagged_videos;
  for (const auto& video : videos) {
    if (mVideoLibrary.isFlagged(video.getVideoId())) {
      continue;
    }
    unflagged_videos.push_back(video);
  }

  // If there are no unflagged videos, display appropriate message.
  if (unflagged_videos.empty()) {
    std::cout << "No videos available" << std::endl;
    return;
  }
  
  // Choose a random integer and restrict it between
  // [0, size of unflagged videos - 1].
  // rand() returns a random integer.
  // Dividing it with unflagged_videos.size() returns a
  // remainder which will be between
  // [0, size of unflagged videos - 1].
  // So, modulus % operator is used here to get the remainder.
  const auto& index = rand() % unflagged_videos.size();
  // Use the chosen integer as the index of random video to be played.
  playVideo(unflagged_videos[index].getVideoId());
}

void VideoPlayer::pauseVideo() {
  // If playingId is empty, then no video is playing and
  // there is nothing to be paused.
  if (playingId.empty()) {
    std::cout << "Cannot pause video: No video is currently playing" << std::endl;
    return;
  }
  // If pausedId is not empty, then the video is already paused.
  if (!pausedId.empty()) {
    std::cout << "Video already paused: ";
    std::cout << mVideoLibrary.getVideo(pausedId)->getTitle() << std::endl;
    return;
  }
  // Copy playingId to pausedId since the currently playing video
  // will be paused.
  pausedId = playingId;
  std::cout << "Pausing video: ";
  std::cout << mVideoLibrary.getVideo(pausedId)->getTitle() << std::endl;

}

void VideoPlayer::continueVideo() {
  // If playingId is empty, then no video is playing and
  // there is nothing to be continued.
  if (playingId.empty()) {
    std::cout << "Cannot continue video: No video is currently playing" << std::endl;
    return;
  }
  // If playingId is empty, then no video is paused and
  // there is nothing to be continued.
  if (pausedId.empty()) {
    std::cout << "Cannot continue video: Video is not paused" << std::endl;
    return;
  }
  // Clear the pausedId since video is played now.
  pausedId = "";
  std::cout << "Continuing video: ";
  std::cout << mVideoLibrary.getVideo(playingId)->getTitle() << std::endl;
}

void VideoPlayer::showPlaying() {
  // If playingId is empty, then no video is playing and
  // there is nothing to be shown.
  if (playingId.empty()) {
    std::cout << "No video is currently playing" << std::endl;
    return;
  }
  // Get the video currently playing using playingId.
  const auto& video = mVideoLibrary.getVideo(playingId);
  std::cout << "Currently playing: " << video->getTitle() << " ";
  std::cout << "(" << video->getVideoId() << ") ";
  std::cout << "[" << video->getTagsAsStr() << "]";
  // If pausedId is not empty, display the information
  // that video is paused.
  if (!pausedId.empty()) {
    std::cout << " - PAUSED" << std::endl;
  } else {
    std::cout << std::endl;
  }
}

void VideoPlayer::createPlaylist(const std::string& playlistName) {
  // Get the playlist with the input playlistName.
  const VideoPlaylist* playlist = mVideoLibrary.getPlaylist(playlistName);
  // If the returned playlist is not nullptr, a playlist with the
  // input name already exists and a new one cannot be created.
  if (playlist != nullptr) {
    std::cout << "Cannot create playlist: ";
    std::cout << "A playlist with the same name already exists" << std::endl;
    return;
  }
  mVideoLibrary.addNewPlaylist(playlistName);
  std::cout << "Successfully created new playlist: " << playlistName << std::endl;
}

void VideoPlayer::addVideoToPlaylist(const std::string& playlistName,
                                     const std::string& videoId) {
  // Get the playlist with the input playlistName.
  const VideoPlaylist* playlist = mVideoLibrary.getPlaylist(playlistName);
  // If returned playlist is nullptr, then no playlist with the name exists
  // and a video cannot be added.
  if (playlist == nullptr) {
    std::cout << "Cannot add video to " << playlistName;
    std::cout << ": Playlist does not exist" << std::endl;
    return;
  }

  // Get the video with the input videoId.
  const Video* video = mVideoLibrary.getVideo(videoId);
  // If returned video is nullptr, then no such video exists and
  // cannot be added to playlist.
  if (video == nullptr) {
    std::cout << "Cannot add video to " << playlistName;
    std::cout << ": Video does not exist" << std::endl;
    return;
  }

  // Flagged video cannot be added to a playlist.
  if (mVideoLibrary.isFlagged(video->getVideoId())) {
    std::cout << "Cannot add video to " << playlistName;
    std::cout << ": Video is currently flagged (reason: " ;
    std::cout << mVideoLibrary.getFlaggedReason(video->getVideoId()) <<")" << std::endl;
    return;
  }

  // A video already present in playlist cannot be added again.
  if (playlist->hasVideo(videoId)) {
    std::cout << "Cannot add video to " << playlistName;
    std::cout << ": Video already added" << std::endl;
    return;
  }

  mVideoLibrary.addVideoToPlaylist(playlistName, videoId);
  std::cout << "Added video to " << playlistName << ": ";
  std::cout << video->getTitle() << std::endl;
}

void VideoPlayer::showAllPlaylists() {
  // Fetch all playlists and iterate over thme to display the names.
  const auto& playlists = mVideoLibrary.getPlaylists();
  if (!playlists.size()) {
    std::cout << "No playlists exist yet" << std::endl;
    return;
  }
  std::cout << "Showing all playlists:" << std::endl;
  for (const auto& playlist : playlists) {
    std::cout << playlist.getTitle() << std::endl;
  }
}

void VideoPlayer::showPlaylist(const std::string& playlistName) {
  // Fetch the playlist using the input playlistName.
  const VideoPlaylist* playlist = mVideoLibrary.getPlaylist(playlistName);
  // If returned playlist is nullptr, then no such playlist exists.
  if (playlist == nullptr) {
    std::cout << "Cannot show playlist " << playlistName;
    std::cout << ": Playlist does not exist" << std::endl;
    return;
  }
  std::cout << "Showing playlist: " << playlistName << std::endl;

  // Obtain all videos in the playlist and iterate over them to
  // display video info.
  const auto& videoIds = playlist->getVideoIds();
  if (videoIds.empty()) {
    std::cout << "No videos here yet" << std::endl;
    return;
  }
  for (const auto& videoId : videoIds) {
    const Video* video = mVideoLibrary.getVideo(videoId);
    std::cout << video->getTitle() << " ";
    std::cout << "(" << video->getVideoId() << ") ";
    std::cout << "[" << video->getTagsAsStr() << "]";
    if (mVideoLibrary.isFlagged(video->getVideoId())){
      std::cout << " - FLAGGED (reason: ";
      std::cout << mVideoLibrary.getFlaggedReason(video->getVideoId()) << ")";
    }
    std::cout << std::endl;
  }
}

void VideoPlayer::removeFromPlaylist(const std::string& playlistName,
                                     const std::string& videoId) {
  // Get the playlist with the input playlistName.
  const VideoPlaylist* playlist = mVideoLibrary.getPlaylist(playlistName);
  // If returned playlist is nullptr, then no playlist with the name exists
  // and a video cannot be removed.
  if (playlist == nullptr) {
    std::cout << "Cannot remove video from " << playlistName;
    std::cout << ": Playlist does not exist" << std::endl;
    return;
  }

  // Get the video with the input videoId.
  const Video* video = mVideoLibrary.getVideo(videoId);
  // If returned video is nullptr, then no such video exists and
  // cannot be added to playlist.
  if (video == nullptr) {
    std::cout << "Cannot remove video from " << playlistName;
    std::cout << ": Video does not exist" << std::endl;
    return;
  }

  // If video is not present in playlist, it cannot be removed.
  if (!playlist->hasVideo(videoId)) {
    std::cout << "Cannot remove video from " << playlistName;
    std::cout << ": Video is not in playlist" << std::endl;
    return;
  }

  mVideoLibrary.removeVideoFromPlaylist(playlistName, videoId);
  std::cout << "Removed video from " << playlistName << ": ";
  std::cout << video->getTitle() << std::endl;
}

void VideoPlayer::clearPlaylist(const std::string& playlistName) {
  // Get the playlist with the input playlistName.
  const VideoPlaylist* playlist = mVideoLibrary.getPlaylist(playlistName);
  // If returned playlist is nullptr, then no playlist with the name exists
  // and cannot be cleared.
  if (playlist == nullptr) {
    std::cout << "Cannot clear playlist " << playlistName;
    std::cout << ": Playlist does not exist" << std::endl;
    return;
  }
  mVideoLibrary.clearPlaylist(playlistName);
  std::cout << "Successfully removed all videos from " << playlistName << std::endl;
}

void VideoPlayer::deletePlaylist(const std::string& playlistName) {
  // Get the playlist with the input playlistName.
  const VideoPlaylist* playlist = mVideoLibrary.getPlaylist(playlistName);
  // If returned playlist is nullptr, then no playlist with the name exists
  // and cannot be deleted.
  if (playlist == nullptr) {
    std::cout << "Cannot delete playlist " << playlistName;
    std::cout << ": Playlist does not exist" << std::endl;
    return;
  }
  mVideoLibrary.deletePlaylist(playlistName);
  std::cout << "Deleted playlist: " << playlistName << std::endl; 
}

void VideoPlayer::searchVideos(const std::string& searchTerm) {
  // Get list of all available videos.
  const auto& videos = mVideoLibrary.getVideos();
  std::vector<Video> matches;
  // Iterate over videos and skip the flagged ones.
  // If the search term is present in video title,
  // add it to a new vector.
  for (const auto& video : videos) {
    if (mVideoLibrary.isFlagged(video.getVideoId())){
      continue;
    }
    if (contains(video.getTitle(), searchTerm)) {
      matches.push_back(video);
    }
  }
  if (matches.empty()) {
    std::cout << "No search results for " << searchTerm << std::endl;
    return;
  }

  const int num_results = matches.size();
  std::cout << "Here are the results for " << searchTerm << ":" << std::endl;
  sort(matches.begin(), matches.end(), SortByTitle);
  for (int i = 1; i <= num_results; i++) {
    std::cout << i << ") ";
    const auto& video = matches[i - 1];
    std::cout << video.getTitle() << " ";
    std::cout << "(" << video.getVideoId() << ") ";
    std::cout << "[" << video.getTagsAsStr() << "]" << std::endl;
  }
  std::cout << "Would you like to play any of the above? ";
  std::cout << "If yes, specify the number of the video." << std::endl;
  std::cout << "If your answer is not a valid number, we will assume it's a no." << std::endl;

  std::string input;
  std::cin >> input;

  if (!isNumber(input)) {
    return;
  }
  // Converts a string to integer.
  // Note that, if string is not a valid number, this will
  // result in an exception.
  int position = std::stoi(input);
  if (position > num_results || position < 1) {
    return;
  }
  playVideo(matches[position - 1].getVideoId());
}

void VideoPlayer::searchVideosWithTag(const std::string& videoTag) {
  // Get list of all available videos.
  const auto& videos = mVideoLibrary.getVideos();
  std::vector<Video> matches;
  // Iterate over videos and skip the flagged ones.
  // If the search term is present in video tags,
  // add it to a new vector.
  for (const auto& video : videos) {
    if (mVideoLibrary.isFlagged(video.getVideoId())){
      continue;
    }
    if (contains(video.getTagsAsStr(), videoTag)) {
      matches.push_back(video);
    }
  }
  if (matches.empty()) {
    std::cout << "No search results for " << videoTag << std::endl;
    return;
  }

  const int num_results = matches.size();
  std::cout << "Here are the results for " << videoTag << ":" << std::endl;
  sort(matches.begin(), matches.end(), SortByTitle);
  for (int i = 1; i <= num_results; i++) {
    std::cout << i << ") ";
    const auto& video = matches[i - 1];
    std::cout << video.getTitle() << " ";
    std::cout << "(" << video.getVideoId() << ") ";
    std::cout << "[" << video.getTagsAsStr() << "]" << std::endl;
  }
  std::cout << "Would you like to play any of the above? ";
  std::cout << "If yes, specify the number of the video." << std::endl;
  std::cout << "If your answer is not a valid number, we will assume it's a no." << std::endl;

  std::string input;
  std::cin >> input;

  if (!isNumber(input)) {
    return;
  }
  // Converts a string to integer.
  // Note that, if string is not a valid number, this will
  // result in an exception.
  int position = std::stoi(input);
  if (position > num_results || position < 1) {
    return;
  }
  playVideo(matches[position - 1].getVideoId());
}

void VideoPlayer::flagVideo(const std::string& videoId) {
  flagVideo(videoId, "Not supplied");
}

void VideoPlayer::flagVideo(const std::string& videoId, const std::string& reason) {
  // Get the video using video id.
  const auto* video = mVideoLibrary.getVideo(videoId);
  // If video is nullptr, it denotes that no such video exists.
  if (video == nullptr) {
  std::cout << "Cannot flag video: Video does not exist" << std::endl;
  return;
  }
  // A video which is already flagged, cannot be flagged again.
  if (mVideoLibrary.isFlagged(videoId)) {
  std::cout << "Cannot flag video: Video is already flagged" << std::endl;
  return;
  }
  // If the currently playing video is the one requested to be flagged,
  // stop the video first.
  if (videoId == playingId) {
  stopVideo();
  }
  mVideoLibrary.setFlagged(videoId, reason);
  std::cout << "Successfully flagged video: "<< video->getTitle();
  std::cout << " (reason: " << reason << ")" << std::endl;
}

void VideoPlayer::allowVideo(const std::string& videoId) {
  // Get the video using video id.
  const auto* video = mVideoLibrary.getVideo(videoId);
  // If video is nullptr, it denotes that no such video exists.
  if (video == nullptr) {
  std::cout << "Cannot remove flag from video: Video does not exist" << std::endl;
  return;
  }
  // If the video is not flagged already, it cannot be unflagged.
  if (!mVideoLibrary.isFlagged(videoId)) {
  std::cout << "Cannot remove flag from video: Video is not flagged" << std::endl;
  return;
  }
  mVideoLibrary.unsetFlagged(videoId);
  std::cout << "Successfully removed flag from video: "<< video->getTitle() << std::endl;
}
