#pragma once

#include "videoplayer.h"

#include <string>
#include <vector>


/**
 * A class used to parse and execute a user Command.
*/
class CommandParser {
    private:
        VideoPlayer videoPlayer;
        void getHelp();
    public:
        CommandParser();
        // Executes the given user command.
        void executeCommand(std::vector<std::string> command);
};
