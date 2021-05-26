"""A youtube terminal simulator."""

import command_parser

if __name__ == "__main__":
    print("""Hello and welcome to YouTube, what would you like to do?
    Enter HELP for list of available commands or EXIT to terminate.""")
    parser = command_parser.CommandParser()
    while True:
        parser.execute_command(input().split())
