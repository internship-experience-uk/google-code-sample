"""A command parser class."""

import textwrap
from typing import Sequence


class CommandException(Exception):
    """A class used to represent a wrong command exception."""
    pass


class CommandParser:
    """A class used to parse and execute a user Command."""

    def __init__(self, video_player):
        self._player = video_player

    def execute_command(self, command: Sequence[str]):
        """Executes the user command. Expects the command to be upper case.
           Raises CommandException if a command cannot be parsed.
        """
        if not command:
            raise CommandException(
                "Please enter a valid command, "
                "type HELP for a list of available commands.")

        if command[0].upper() == "NUMBER_OF_VIDEOS":
            self._player.number_of_videos()

        elif command[0].upper() == "SHOW_ALL_VIDEOS":
            self._player.show_all_videos()

        elif command[0].upper() == "PLAY":
            if len(command) != 2:
                raise CommandException(
                    "Please enter PLAY command followed by video_id.")
            self._player.play_video(command[1])

        elif command[0].upper() == "PLAY_RANDOM":
            self._player.play_random_video()

        elif command[0].upper() == "STOP":
            self._player.stop_video()

        elif command[0].upper() == "PAUSE":
            self._player.pause_video()

        elif command[0].upper() == "CONTINUE":
            self._player.continue_video()

        elif command[0].upper() == "SHOW_PLAYING":
            self._player.show_playing()

        elif command[0].upper() == "CREATE_PLAYLIST":
            if len(command) != 2:
                raise CommandException(
                    "Please enter CREATE_PLAYLIST command followed by a "
                    "playlist name.")
            self._player.create_playlist(command[1])

        elif command[0].upper() == "ADD_TO_PLAYLIST":
            if len(command) != 3:
                raise CommandException(
                    "Please enter ADD_TO_PLAYLIST command followed by a "
                    "playlist name and video_id to add.")
            self._player.add_to_playlist(command[1], command[2])

        elif command[0].upper() == "REMOVE_FROM_PLAYLIST":
            if len(command) != 3:
                raise CommandException(
                    "Please enter REMOVE_FROM_PLAYLIST command followed by a "
                    "playlist name and video_id to remove.")
            self._player.remove_from_playlist(command[1], command[2])

        elif command[0].upper() == "CLEAR_PLAYLIST":
            if len(command) != 2:
                raise CommandException(
                    "Please enter CLEAR_PLAYLIST command followed by a "
                    "playlist name.")
            self._player.clear_playlist(command[1])

        elif command[0].upper() == "DELETE_PLAYLIST":
            if len(command) != 2:
                raise CommandException(
                    "Please enter DELETE_PLAYLIST command followed by a "
                    "playlist name.")
            self._player.delete_playlist(command[1])

        elif command[0].upper() == "SHOW_PLAYLIST":
            if len(command) != 2:
                raise CommandException(
                    "Please enter SHOW_PLAYLIST command followed by a "
                    "playlist name.")
            self._player.show_playlist(command[1])

        elif command[0].upper() == "SHOW_ALL_PLAYLISTS":
            self._player.show_all_playlists()

        elif command[0].upper() == "SEARCH_VIDEOS":
            if len(command) != 2:
                raise CommandException(
                    "Please enter SEARCH_VIDEOS command followed by a "
                    "search term.")
            self._player.search_videos(command[1])

        elif command[0].upper() == "SEARCH_VIDEOS_WITH_TAG":
            if len(command) != 2:
                raise CommandException(
                    "Please enter SEARCH_VIDEOS_WITH_TAG command followed by a "
                    "video tag.")
            self._player.search_videos_tag(command[1])

        elif command[0].upper() == "FLAG_VIDEO":
            if len(command) == 3:
                self._player.flag_video(command[1], command[2])
            elif len(command) == 2:
                self._player.flag_video(command[1])
            else:
                raise CommandException(
                    "Please enter FLAG_VIDEO command followed by a "
                    "video_id and an optional flag reason.")

        elif command[0].upper() == "ALLOW_VIDEO":
            if len(command) != 2:
                raise CommandException(
                    "Please enter ALLOW_VIDEO command followed by a "
                    "video_id.")
            self._player.allow_video(command[1])

        elif command[0].upper() == "HELP":
            self._get_help()
        else:
            print(
                "Please enter a valid command, type HELP for a list of "
                "available commands.")

    def _get_help(self):
        """Displays all available commands to the user."""
        help_text = textwrap.dedent("""
        Available commands:
            NUMBER_OF_VIDEOS - Shows how many videos are in the library.
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
        print(help_text)
