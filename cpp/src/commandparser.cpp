#include "commandparser.h"

#include <iostream>
#include <sstream>
#include <utility>
#include <vector>

CommandParser::CommandParser(VideoPlayer&& vp) : mVideoPlayer(std::move(vp)) {}

void CommandParser::executeCommand(const std::vector<std::string>& command) {
  if (command.empty()) {
    std::cout << "No commands passed in to executeCommand, that is unexpected"
              << std::endl;
    return;
  }

  if (command[0] == "NUMBER_OF_VIDEOS") {
    mVideoPlayer.numberOfVideos();
  } else if (command[0] == "SHOW_ALL_VIDEOS") {
    mVideoPlayer.showAllVideos();
  } else if (command[0] == "PLAY") {
    if (command.size() != 2) {
      std::cout << "Please enter PLAY command followed by video_id."
                << std::endl;
    } else {
      mVideoPlayer.playVideo(command[1]);
    }
  } else if (command[0] == "PLAY_RANDOM") {
    mVideoPlayer.playRandomVideo();
  } else if (command[0] == "STOP") {
    mVideoPlayer.stopVideo();
  } else if (command[0] == "PAUSE") {
    mVideoPlayer.pauseVideo();
  } else if (command[0] == "CONTINUE") {
    mVideoPlayer.continueVideo();
  } else if (command[0] == "SHOW_PLAYING") {
    mVideoPlayer.showPlaying();
  } else if (command[0] == "CREATE_PLAYLIST") {
    if (command.size() != 2) {
      std::cout << "Please enter CREATE_PLAYLIST command followed by video_id."
                << std::endl;
    } else {
      mVideoPlayer.createPlaylist(command[1]);
    }
  } else if (command[0] == "ADD_TO_PLAYLIST") {
    if (command.size() != 3) {
      std::cout << "Please enter ADD_TO_PLAYLIST command followed by playlist "
                   "name and video_id."
                << std::endl;
    } else {
      mVideoPlayer.addVideoToPlaylist(command[1], command[2]);
    }
  } else if (command[0] == "REMOVE_FROM_PLAYLIST") {
    if (command.size() != 3) {
      std::cout << "Please enter REMOVE_FROM_PLAYLIST command followed by "
                   "playlist name and video_id."
                << std::endl;
    } else {
      mVideoPlayer.removeFromPlaylist(command[1], command[2]);
    }
  } else if (command[0] == "CLEAR_PLAYLIST") {
    if (command.size() != 2) {
      std::cout
          << "Please enter CLEAR_PLAYLIST command followed by a playlist name."
          << std::endl;
    } else {
      mVideoPlayer.clearPlaylist(command[1]);
    }
  } else if (command[0] == "DELETE_PLAYLIST") {
    if (command.size() != 2) {
      std::cout
          << "Please enter DELETE_PLAYLIST command followed by a playlist name."
          << std::endl;
    } else {
      mVideoPlayer.deletePlaylist(command[1]);
    }
  } else if (command[0] == "SHOW_PLAYLIST") {
    if (command.size() != 2) {
      std::cout
          << "Please enter SHOW_PLAYLIST command followed by a playlist name."
          << std::endl;
    } else {
      mVideoPlayer.showPlaylist(command[1]);
    }
  } else if (command[0] == "SHOW_ALL_PLAYLISTS") {
    mVideoPlayer.showAllPlaylists();
  } else if (command[0] == "SEARCH_VIDEOS") {
    if (command.size() != 2) {
      std::cout
          << "Please enter SEARCH_VIDEOS command followed by a search term."
          << std::endl;
    } else {
      mVideoPlayer.searchVideos(command[1]);
    }
  } else if (command[0] == "SEARCH_VIDEOS_WITH_TAG") {
    if (command.size() != 2) {
      std::cout << "Please enter SEARCH_VIDEOS_WITH_TAG command followed by a "
                   "video tag."
                << std::endl;
    } else {
      mVideoPlayer.searchVideosWithTag(command[1]);
    }
  } else if (command[0] == "FLAG_VIDEO") {
    switch (command.size()) {
      case 2:
        mVideoPlayer.flagVideo(command[1]);
        break;
      case 3:
        mVideoPlayer.flagVideo(command[1], command[2]);
        break;
      default:
        std::cout << "Please enter FLAG_VIDEO command followed by a video_id "
                     "and an optional flag reason."
                  << std::endl;
    }
  } else if (command[0] == "ALLOW_VIDEO") {
    if (command.size() != 2) {
      std::cout << "Please enter ALLOW_VIDEO command followed by a video_id."
                << std::endl;
    } else {
      mVideoPlayer.allowVideo(command[1]);
    }
  } else if (command[0] == "HELP") {
    getHelp();
  } else {
    std::cout << "Please enter a valid command, type HELP for a list of "
                 "available commands."
              << std::endl;
  }
}

void CommandParser::getHelp() const {
  static const char* const helpText = R"(
Available commands:
    NUMBER_OF_VIDEOS - Shows how many videos are in the library.
    SHOW_ALL_VIDEOS - Lists all videos from the library.
    PLAY <video_id> - Plays specified video.
    PLAY_RANDOM - Plays a random video from the library.
    STOP - Stop the current video.
    PAUSE - Pause the current video.
    CONTINUE - Resume the current paused video.
    SHOW_PLAYING - Displays the title, url and paused status of the video that is currently playing (or paused).
    CREATE_PLAYLIST <playlist_name> - Creates a new (empty) playlist with the provided name.
    ADD_TO_PLAYLIST <playlist_name> <video_id> - Adds the requested video to the playlist.
    REMOVE_FROM_PLAYLIST <playlist_name> <video_id> - Removes the specified video from the specified playlist
    CLEAR_PLAYLIST <playlist_name> - Removes all the videos from the playlist.
    DELETE_PLAYLIST <playlist_name> - Deletes the playlist.
    SHOW_PLAYLIST <playlist_name> - List all the videos in this playlist.
    SHOW_ALL_PLAYLISTS - Display all the available playlists.
    SEARCH_VIDEOS <search_term> - Display all the videos whose titles contain the search_term.
    SEARCH_VIDEOS_WITH_TAG <tag_name> -Display all videos whose tags contains the provided tag.
    FLAG_VIDEO <video_id> <flag_reason> - Mark a video as flagged.
    ALLOW_VIDEO <video_id> - Removes a flag from a video.
    HELP - Displays help.
    EXIT - Terminates the program execution.
)";
  std::cout << helpText << std::endl;
}
