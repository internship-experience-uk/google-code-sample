"""A command helper class."""

from video import video_player

player = video_player.VideoPlayer()


class Command:
  """A class used to represent a user Command."""

  def __init__(self):
    """The Command class is initialized."""

  def execute_command(self, command):
    """Executes the user command.

    Args:
        command: The command and respective argumments.
    """
    if command[0].upper() == "SHOW_ALL_VIDEOS":
      player.show_all_videos()

    elif command[0].upper() == "PLAY":
      try:
        player.play_video(command[1])
      except IndexError:
        print("Please enter PLAY command followed by video_id.")

    elif command[0].upper() == "PLAY_RANDOM":
      player.play_random_video()
    elif command[0].upper() == "STOP":
      player.stop_video()

    elif command[0].upper() == "PAUSE":
      player.pause_video()

    elif command[0].upper() == "CONTINUE":
      player.continue_video()

    elif command[0].upper() == "SHOW_PLAYING":
      player.show_playing()

    elif command[0].upper() == "CREATE_PLAYLIST":
      try:
        player.create_playlist(command[1])
      except IndexError:
        print(
            "Please enter CREATE_PLAYLIST command followed by a playlist name.")

    elif command[0].upper() == "ADD_TO_PLAYLIST":
      try:
        player.add_to_playlist(command[1], command[2])
      except IndexError:
        print(
            """Please enter ADD_TO_PLAYLIST command followed by a playlist name
            and video_id to add.""")

    elif command[0].upper() == "REMOVE_FROM_PLAYLIST":
      try:
        player.remove_from_playlist(command[1], command[2])
      except IndexError:
        print(
            """Please enter REMOVE_FROM_PLAYLIST command followed by a playlist
            name and video_id to remove.""")

    elif command[0].upper() == "CLEAR_PLAYLIST":
      try:
        player.clear_playlist(command[1])
      except IndexError:
        print(
            "Please enter CLEAR_PLAYLIST command followed by a playlist name.")

    elif command[0].upper() == "DELETE_PLAYLIST":
      try:
        player.delete_playlist(command[1])
      except IndexError:
        print(
            "Please enter DELETE_PLAYLIST command followed by a playlist name.")

    elif command[0].upper() == "SHOW_PLAYLIST":
      try:
        player.show_playlist(command[1])
      except IndexError:
        print("Please enter SHOW_PLAYLIST command followed by a playlist name.")

    elif command[0].upper() == "SHOW_ALL_PLAYLISTS":
      player.show_all_playlists()

    elif command[0].upper() == "SEARCH_VIDEOS":
      try:
        player.search_videos(command[1])
      except IndexError:
        print("Please enter SEARCH_VIDEOS command followed by a search term.")

    elif command[0].upper() == "SEARCH_VIDEOS_WITH_TAG":
      try:
        player.search_videos_tag(command[1])
      except IndexError:
        print(
            "Please enter SEARCH_VIDEOS_WITH_TAG command followed by a video tag."
        )

    elif command[0].upper() == "FLAG_VIDEO":
      try:
        player.flag_video(command[1], command[2])
      except IndexError:
        try:
          player.flag_video(command[1])
        except IndexError:
          print("""Please enter FLAG_VIDEO command followed by a video_id
                and an optional flag reason.""")

    elif command[0].upper() == "ALLOW_VIDEO":
      try:
        player.allow_video(command[1])
      except IndexError:
        print("Please enter ALLOW_VIDEO command followed by a video_id.")

    elif command[0].upper() == "HELP":
      self._get_help()

    elif command[0].upper() == "EXIT":
      exit("YouTube has now terminated it's execution. Thank you and goodbye!")

    else:
      print(
          "Please enter a valid command, type HELP for a list of available commands."
      )

  def _get_help(self):
    """Displays all available commands to the user."""

    print("""Available commands:
        SHOW_ALL_VIDEOS - Lists all videos from the library.
        PLAY <video_id> - Plays specified video.
        PLAY_RANDOM - Plays a random video from the library.
        STOP - Stop the current video.
        PAUSE - Pause the current video.
        CONTINUE - Resume the current paused video.
        SHOW_PLAYING - Displays the title, url and paused status of the video that is currently playing (or paused).
        CREATE_PLAYLIST <playlist_name> - Creates a new (empty) playlist with the provided name.
        ADD_TO_PLAYLIST <playlist_name> <video_id> - Adds the requested video to the playlist.
        REMOVE_FROM_PLAYLIST <playlist_name> <video_id> - Removes the specified video from the specified playlist
        CLEAR_PLAYLIST <playlist_name> - Removes all the videos from the playlist.
        DELETE_PLAYLIST <playlist_name> - Deletes the playlist.
        SHOW_PLAYLIST <playlist_name> - List all the videos in this playlist.
        SHOW_ALL_PLAYLISTS - Display all the available playlists.
        SEARCH_VIDEOS <search_term> - Display all the videos whose titles contain the search_term.
        SEARCH_VIDEOS_WITH_TAG <tag_name> -Display all videos whose tags contains the provided tag.
        FLAG_VIDEO <video_id> <flag_reason> - Mark a video as flagged.
        ALLOW_VIDEO <video_id> - Removes a flag from a video.
        HELP - Displays help.
        EXIT - Terminates the program execution.
        """)
