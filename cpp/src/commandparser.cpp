#include "commandparser.h"

#include <iostream>
#include <sstream>
#include <vector>

CommandParser::CommandParser(VideoPlayer&& vp) : videoPlayer{vp} {}

void CommandParser::executeCommand(std::vector<std::string> command) {
  if (command.empty()) {
    std::cout << "No commands passed in to executeCommand, that is unexpected"
              << std::endl;
    return;
  }

  if (command[0] == "SHOW_ALL_VIDEOS") {
    videoPlayer.showAllVideos();
  } else if (command[0] == "PLAY") {
    if (command.size() != 2) {
      std::cout << "Please enter PLAY command followed by video_id."
                << std::endl;
    } else {
      videoPlayer.playVideo(command[1]);
    }
  } else if (command[0] == "PLAY_RANDOM") {
    videoPlayer.playRandomVideo();
  } else if (command[0] == "STOP") {
    videoPlayer.stopVideo();
  } else if (command[0] == "PAUSE") {
    videoPlayer.pauseVideo();
  } else if (command[0] == "CONTINUE") {
    videoPlayer.continueVideo();
  } else if (command[0] == "SHOW_PLAYING") {
    videoPlayer.showPlaying();
  } else if (command[0] == "CREATE_PLAYLIST") {
    if (command.size() != 2) {
      std::cout << "Please enter CREATE_PLAYLIST command followed by video_id."
                << std::endl;
    } else {
      videoPlayer.createPlaylist(command[1]);
    }
  } else if (command[0] == "ADD_TO_PLAYLIST") {
    if (command.size() != 3) {
      std::cout << "Please enter ADD_TO_PLAYLIST command followed by playlist "
                   "name and video_id."
                << std::endl;
    } else {
      videoPlayer.addVideoToPlaylist(command[1], command[2]);
    }
  } else if (command[0] == "REMOVE_FROM_PLAYLIST") {
    if (command.size() != 3) {
      std::cout << "Please enter REMOVE_FROM_PLAYLIST command followed by "
                   "playlist name and video_id."
                << std::endl;
    } else {
      videoPlayer.removeFromPlaylist(command[1], command[2]);
    }
  } else if (command[0] == "CLEAR_PLAYLIST") {
    if (command.size() != 2) {
      std::cout
          << "Please enter CLEAR_PLAYLIST command followed by a playlist name."
          << std::endl;
    } else {
      videoPlayer.clearPlaylist(command[1]);
    }
  } else if (command[0] == "DELETE_PLAYLIST") {
    if (command.size() != 2) {
      std::cout
          << "Please enter DELETE_PLAYLIST command followed by a playlist name."
          << std::endl;
    } else {
      videoPlayer.deletePlaylist(command[1]);
    }
  } else if (command[0] == "SHOW_PLAYLIST") {
    if (command.size() != 2) {
      std::cout
          << "Please enter SHOW_PLAYLIST command followed by a playlist name."
          << std::endl;
    } else {
      videoPlayer.showPlaylist(command[1]);
    }
  } else if (command[0] == "SHOW_ALL_PLAYLISTS") {
    videoPlayer.showAllPlaylists();
  } else if (command[0] == "SEARCH_VIDEOS") {
    if (command.size() != 2) {
      std::cout
          << "Please enter SEARCH_VIDEOS command followed by a search term."
          << std::endl;
    } else {
      videoPlayer.searchVideos(command[1]);
    }
  } else if (command[0] == "SEARCH_VIDEOS_WITH_TAG") {
    if (command.size() != 2) {
      std::cout << "Please enter SEARCH_VIDEOS_WITH_TAG command followed by a "
                   "video tag."
                << std::endl;
    } else {
      videoPlayer.searchVideosWithTag(command[1]);
    }
  } else if (command[0] == "FLAG_VIDEO") {
    switch (command.size()) {
      case 2:
        videoPlayer.flagVideo(command[1]);
        break;
      case 3:
        videoPlayer.flagVideo(command[1], command[2]);
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
      videoPlayer.allowVideo(command[1]);
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
  static std::string const helpText =
      " Available commands:\n"
      "    SHOW_ALL_VIDEOS - Lists all videos from the library.\n"
      "    PLAY <video_id> - Plays specified video.\n"
      "    PLAY_RANDOM - Plays a random video from the library.\n"
      "    STOP - Stop the current video.\n"
      "    PAUSE - Pause the current video.\n"
      "    CONTINUE - Resume the current paused video.\n"
      "    SHOW_PLAYING - Displays the title, url and paused status of the "
      "video that is currently playing (or paused).\n"
      "    CREATE_PLAYLIST <playlist_name> - Creates a new (empty) playlist "
      "with the provided name.\n"
      "    ADD_TO_PLAYLIST <playlist_name> <video_id> - Adds the requested "
      "video to the playlist.\n"
      "    REMOVE_FROM_PLAYLIST <playlist_name> <video_id> - Removes the "
      "specified video from the specified playlist\n"
      "    CLEAR_PLAYLIST <playlist_name> - Removes all the videos from the "
      "playlist.\n"
      "    DELETE_PLAYLIST <playlist_name> - Deletes the playlist.\n"
      "    SHOW_PLAYLIST <playlist_name> - List all the videos in this "
      "playlist.\n"
      "    SHOW_ALL_PLAYLISTS - Display all the available playlists.\n"
      "    SEARCH_VIDEOS <search_term> - Display all the videos whose titles "
      "contain the search_term.\n"
      "    SEARCH_VIDEOS_WITH_TAG <tag_name> -Display all videos whose tags "
      "contains the provided tag.\n"
      "    FLAG_VIDEO <video_id> <flag_reason> - Mark a video as flagged.\n"
      "    ALLOW_VIDEO <video_id> - Removes a flag from a video.\n"
      "    HELP - Displays help.\n"
      "    EXIT - Terminates the program execution.\n";
  std::cout << helpText << std::endl;
}
