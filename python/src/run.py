"""A youtube terminal simulator."""

from command import command_helper

command = command_helper.Command()

if __name__ == "__main__":
  print("""Hello and welcome to YouTube, what would you like to do?
    Enter HELP for list of available commands or EXIT to terminate.""")
  while True:
    command.execute_command(input().split())
