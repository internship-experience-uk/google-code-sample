"""A youtube terminal simulator."""

import command_parser
import sys
import os

if __name__ == "__main__":
    print("""Hello and welcome to YouTube, what would you like to do?
    Enter HELP for list of available commands or EXIT to terminate.""")
    parser = command_parser.CommandParser()
    while True:
        command = input().upper()
        if command == "EXIT":
            print("YouTube has now terminated its execution. "
                  "Thank you and goodbye!")
            sys.exit(os.EX_OK)

        parser.execute_command(command.split())
