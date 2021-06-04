#ifndef COMMANDPARSER_H
#define COMMANDPARSER_H

#include <iostream>
#include <vector>
#include "videoplayer.h"

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

#endif