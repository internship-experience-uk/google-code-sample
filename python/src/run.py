"""A youtube terminal simulator."""
from .video_player import VideoPlayer
from .command_parser import CommandException
from .command_parser import CommandParser


if __name__ == "__main__":
    print("""Hello and welcome to YouTube, what would you like to do?
    Enter HELP for list of available commands or EXIT to terminate.""")
    video_player = VideoPlayer()
    parser = CommandParser(video_player)
    while True:
        command = input("YT> ")
        if command.upper() == "EXIT":
            break
        try:
            parser.execute_command(command.split())
        except CommandException as e:
            print(e)
    print("YouTube has now terminated its execution. "
          "Thank you and goodbye!")
