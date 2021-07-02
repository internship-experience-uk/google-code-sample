#pragma once

#include <string>
#include <vector>

#include "videoplayer.h"

/**
 * A class used to parse and execute a user Command.
 */
class CommandParser {
 private:
  VideoPlayer mVideoPlayer;

  void getHelp() const;

 public:
  CommandParser(VideoPlayer&& vp);

  // This class is not copyable to avoid expensive copies.
  CommandParser(const CommandParser&) = delete;
  CommandParser& operator=(const CommandParser&) = delete;

  // This class is movable.
  CommandParser(CommandParser&&) = default;
  CommandParser& operator=(CommandParser&&) = default;

  // Executes the given user command.
  void executeCommand(const std::vector<std::string>& command);
};
