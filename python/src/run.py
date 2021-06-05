"""A youtube terminal simulator."""

from command_parser import CommandParser
from video_player import VideoPlayer
import sys
import os

if __name__ == "__main__":
    print("""Hello and welcome to YouTube, what would you like to do?
    Enter HELP for list of available commands or EXIT to terminate.""")
    video_player = VideoPlayer()
    parser = CommandParser(video_player)
    while True:
        command = input().upper()
        if command == "EXIT":
            print("YouTube has now terminated its execution. "
                  "Thank you and goodbye!")
            sys.exit(os.EX_OK)

        parser.execute_command(command.split())
