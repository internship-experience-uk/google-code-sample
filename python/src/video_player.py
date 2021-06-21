"""A video player class."""
import random

from .video_library import VideoLibrary
from .video_playlist import Playlist


class VideoPlayer:
    """A class used to represent a Video Player."""

    def __init__(self):
        self._video_library = VideoLibrary()
        self._current_video = None
        self._is_paused = False
        # A mapping from id (lowercase) to playlist object
        self._playlists = {}

    def number_of_videos(self):
        num_videos = len(self._video_library.get_all_videos())
        print(f"{num_videos} videos in the library")

    def show_all_videos(self):
        """Returns all videos."""
        print("Here's a list of all available videos:")
        video_list = sorted(self._video_library.get_all_videos(), key=lambda v: v.title)
        for video in video_list:
            print(f"  {video}")

    def play_video(self, video_id):
        """Plays the respective video.

        Args:
            video_id: The video_id to be played.
        """
        video = self._video_library.get_video(video_id)
        if not video:
            print("Cannot play video: Video does not exist")
            return

        if self._current_video:
            self._current_video.stop()
            self._is_paused = False
        self._current_video = video
        video.play()

    def stop_video(self):
        """Stops the current video."""
        if not self._current_video:
            print("Cannot stop video: No video is currently playing")
            return
        self._current_video.stop()
        self._current_video = None

    def play_random_video(self):
        """Plays a random video from the video library."""
        if self._current_video:
            self._current_video.stop()
        video = random.choice(self._video_library.get_all_videos())
        self._current_video = video
        video.play()

    def show_playing(self):
        """Displays video currently playing."""
        if self._current_video:
            currently_playing_str = f"Currently playing: {self._current_video}"
            if self._is_paused:
                currently_playing_str += " - PAUSED"
            print(currently_playing_str)
        else:
            print("No video is currently playing")

    def pause_video(self):
        """Pauses the current video."""
        if not self._current_video:
            print("Cannot pause video: No video is currently playing")
            return
        if self._is_paused:
            print(f"Video already paused: {self._current_video.title}")
        else:
            print(f"Pausing video: {self._current_video.title}")
            self._is_paused = True

    def continue_video(self):
        """Resumes playing the current video."""
        if not self._current_video:
            print("Cannot continue video: No video is currently playing")
            return
        if not self._is_paused:
            print(f"Cannot continue video: Video is not paused")
        else:
            print(f"Continuing video: {self._current_video.title}")
            self._is_paused = False

    def create_playlist(self, playlist_name):
        """Creates a playlist with a given name.
    Args:
        name: The playlist name.
    """
        if playlist_name.lower() in self._playlists:
            print("Cannot create playlist: A playlist with the same name already exists")
        else:
            print(f"Successfully created new playlist: {playlist_name}")
            self._playlists[playlist_name.lower()] = Playlist(playlist_name)

    def add_to_playlist(self, playlist_name, video_id):
        """Adds a video to a playlist with a given name.

    Args:
        name: The playlist name.
        video_id: The video_id to be added.
    """
        playlist = self._playlists.get(playlist_name.lower(), None)
        if not playlist:
            print(f"Cannot add video to {playlist_name}: Playlist does not exist")
            return

        video = self._video_library.get_video(video_id)
        if not video:
            print(f"Cannot add video to {playlist_name}: Video does not exist")
            return

        playlist.add_video(playlist_name, video)

    def remove_from_playlist(self, playlist_name, video_id):
        """Removes a video to a playlist with a given name.

    Args:
        name: The playlist name.
        video_id: The video_id to be removed.
    """
        playlist = self._playlists.get(playlist_name.lower())
        if not playlist:
            print(f"Cannot remove video from {playlist_name}: Playlist does not exist")
            return

        video = self._video_library.get_video(video_id)
        if not video:
            print(f"Cannot remove video from {playlist_name}: Video does not exist")
            return

        playlist.remove_video(playlist_name, video)

    def clear_playlist(self, playlist_name):
        """Removes all videos from a playlist with a given name.

     Args:
        name: The playlist name.
    """
        playlist = self._playlists.get(playlist_name.lower())
        if not playlist:
            print(f"Cannot clear playlist {playlist_name}: Playlist does not exist")
        else:
            playlist.clear(playlist_name)

    def delete_playlist(self, playlist_name):
        """Deletes a playlist with a given name.

    Args:
        name: The playlist name.
    """
        if playlist_name.lower() not in self._playlists:
            print(f"Cannot delete playlist {playlist_name}: Playlist does not exist")
        else:
            del self._playlists[playlist_name.lower()]
            print(f"Deleted playlist: {playlist_name}")

    def show_playlist(self, playlist_name):
        """Display all videos in a playlist with a given name.

    Args:
        name: The playlist name.
    """
        playlist = self._playlists.get(playlist_name.lower())
        if not playlist:
            print(f"Cannot show playlist {playlist_name}: Playlist does not exist")
        else:
            playlist.show(playlist_name)

    def show_all_playlists(self):
        """Display all playlists."""
        if not self._playlists:
            print("No playlists exist yet")
            return
        print("Showing all playlists:")
        playlist_names = sorted([playlist.name for playlist in self._playlists.values()])
        for playlist_name in playlist_names:
            print(f"  {playlist_name}")

    def search_videos(self, search_term):
        """Display all the videos whose titles contain the search_term.

     Args:
        search_term: The query to be used in search.
    """
        results = []
        for video in self._video_library.get_all_videos():
            if search_term.lower() in video.title.lower() and not video.flagged:
                results.append(video)
        if not results:
            print(f"No search results for {search_term}")
            return

        print(f"Here are the results for {search_term}:")
        for i, video in enumerate(results, 1):
            print(f"  {i}) {video}")
        print("Would you like to play any of the above? If yes, "
              "specify the number of the video.\nIf your answer is not a valid "
              "number, we will assume it's a no.")
        answer = input()
        if answer.strip().isdigit() and int(answer.strip()) <=len(results):
            results[int(answer)-1].play()

    def search_videos_tag(self, video_tag):
        """Display all videos whose tags contains the provided tag.

    Args:
        video_tag: The video tag to be used in search.
    """
        results = []
        for video in self._video_library.get_all_videos():
            if video_tag.lower() in [tag.lower() for tag in video.tags]:
                results.append(video)

        if not results:
            print(f"No search results for {video_tag}")
            return

        print(f"Here are the results for {video_tag}:")
        for i, video in enumerate(results, 1):
            print(f"  {i}) {video}")
        print("Would you like to play any of the above? If yes, "
              "specify the number of the video.\nIf your answer is not a valid "
              "number, we will assume it's a no.")
        answer = input()
        if answer.strip().isdigit() and int(answer.strip()) <= len(results):
            results[int(answer)-1].play()

    def flag_video(self, video_id, flag_reason=None):
        """Mark a video as flagged.

    Args:
        video_id: The video_id to be flagged.
        flag_reason: Reason for flagging the video.
    """
        video = self._video_library.get_video(video_id)
        if not video:
            print("Cannot flag video: Video does not exist")
            return

        if self._current_video == video:
            self._current_video = None
        video.flag(flag_reason)

    def allow_video(self, video_id):
        """Removes a flag from a video.

    Args:
        video_id: The video_id to be allowed again.
    """
        video = self._video_library.get_video(video_id)
        if not video:
            print("Cannot remove flag from video: Video does not exist")
            return
        video.allow()
