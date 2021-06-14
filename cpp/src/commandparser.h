#pragma once

#include <string>
#include <vector>

#include "videoplayer.h"

/**
 * A class used to parse and execute a user Command.
 */
class CommandParser {
 private:
  VideoPlayer video_player_;

  void getHelp() const;

 public:
  CommandParser(VideoPlayer&& vp);

  // This class is not copyable to avoid expensive copies.
  CommandParser(CommandParser const&) = delete;
  CommandParser& operator=(CommandParser const&) = delete;

  // This class is movable.
  CommandParser(CommandParser&&) = default;
  CommandParser& operator=(CommandParser&&) = default;

  // Executes the given user command.
  void executeCommand(std::vector<std::string> const& command);
};
