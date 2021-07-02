"""A video player class."""

from .video_library import VideoLibrary
from .video_playlist import Playlist
from random import choice

class VideoPlayer:
    """A class used to represent a Video Player."""

    def __init__(self):
        self._video_library = VideoLibrary()
        self._current_video = None
        self._paused = False
        self._playlists = {}

    def number_of_videos(self):
        video_count = len(self._video_library.get_all_videos())
        print(f"{video_count} videos in the library")

    def show_all_videos(self):
        """Returns all videos."""
        print("Here's a list of all available videos:")
        for video in sorted(self._video_library.get_all_videos(), key=lambda x: x.title):
          print(video)

    def play_video(self, video_id):
        """Plays the respective video.

        Args:
            video_id: The video_id to be played.
        """
        video = self._video_library.get_video(video_id)
        if video is None:
          print("Cannot play video: Video does not exist")
          return
        if video.flag is not None:
          print(f"Cannot play video: Video is currently flagged (reason: {video.flag})")
          return
        if self._current_video:
          self.stop_video()
        print(f"Playing video: {video.title}")
        self._current_video = video

    def stop_video(self):
        """Stops the current video."""
        if self._current_video is None:
          print("Cannot stop video: No video is currently playing")
          return
        print(f"Stopping video: {self._current_video.title}")
        self._current_video = None
        self._paused = False

    def play_random_video(self):
        """Plays a random video from the video library."""
        if self._current_video:
          self.stop_video()
        videos = [v for v in self._video_library.get_all_videos() if v.flag is None]
        if not videos:
          print("No videos available")
          return
        self.play_video(choice(videos).video_id)

    def pause_video(self):
        """Pauses the current video."""
        if self._paused:
          print(f"Video already paused: {self._current_video.title}")
          return
        elif self._current_video is None:
          print("Cannot pause video: No video is currently playing")
          return
        print(f"Pausing video: {self._current_video.title}")
        self._paused = True


    def continue_video(self):
        """Resumes playing the current video."""
        if self._current_video is None:
          print("Cannot continue video: No video is currently playing")
          return
        elif not self._paused:
          print("Cannot continue video: Video is not paused")
          return
        print(f"Continuing video: {self._current_video.title}")
        self._paused = False

    def show_playing(self):
        """Displays video currently playing."""
        if not self._current_video:
          print("No video is currently playing")
          return
        print(f"Currently playing: {self._current_video}{' - PAUSED' if self._paused else ''}")

    def create_playlist(self, playlist_name):
        """Creates a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        if playlist_name.lower() in self._playlists:
          print("Cannot create playlist: A playlist with the same name already exists")
          return
        print(f"Successfully created new playlist: {playlist_name}")
        self._playlists[playlist_name.lower()] = Playlist(playlist_name)

    def add_to_playlist(self, playlist_name, video_id):
        """Adds a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be added.
        """
        video = self._video_library.get_video(video_id)
        if playlist_name.lower() not in self._playlists:
            print(f"Cannot add video to {playlist_name}: Playlist does not exist")
            return
        if not video:
            print(f"Cannot add video to {playlist_name}: Video does not exist")
            return
        if video.flag is not None:
            print(f"Cannot add video to {playlist_name}: Video is currently flagged (reason: {video.flag})")
            return
        playlist = self._playlists[playlist_name.lower()]
        if video in playlist.videos:
            print(f"Cannot add video to {playlist_name}: Video already added")
            return
        playlist.videos.append(self._video_library.get_video(video_id))
        print(f"Added video to {playlist_name}: {video.title}")

    def show_all_playlists(self):
        """Display all playlists."""
        if not self._playlists:
            print("No playlists exist yet")
            return
        print("Showing all playlists:")
        for playlist in sorted(self._playlists):
          print(f"{self._playlists[playlist].name}")

    def show_playlist(self, playlist_name):
        """Display all videos in a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        if playlist_name.lower() not in self._playlists:
            print(f"Cannot show playlist {playlist_name}: Playlist does not exist")
            return
        playlist = self._playlists[playlist_name.lower()]
        print(f"Showing playlist: {playlist_name}")
        if not playlist.videos:
            print("No videos here yet")
        for video in playlist.videos:
            print(video)

    def remove_from_playlist(self, playlist_name, video_id):
        """Removes a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be removed.
        """
        if playlist_name.lower() not in self._playlists:
            print(f"Cannot remove video from {playlist_name}: Playlist does not exist")
            return
        playlist = self._playlists[playlist_name.lower()]
        video = self._video_library.get_video(video_id)
        if not video:
            print(f"Cannot remove video from {playlist_name}: Video does not exist")
            return
        if video not in playlist.videos:
            print(f"Cannot remove video from {playlist_name}: Video is not in playlist")
            return
        print(f"Removed video from {playlist_name}: {video.title}")
        playlist.videos.remove(video)

    def clear_playlist(self, playlist_name):
        """Removes all videos from a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        if playlist_name.lower() not in self._playlists:
            print(f"Cannot clear playlist {playlist_name}: Playlist does not exist")
            return
        print(f"Successfully removed all videos from {playlist_name}")
        self._playlists[playlist_name.lower()].videos = []

    def delete_playlist(self, playlist_name):
        """Deletes a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        if playlist_name.lower() not in self._playlists:
            print(f"Cannot delete playlist {playlist_name}: Playlist does not exist")
            return
        print(f"Deleted playlist: {playlist_name}")
        self._playlists.pop(playlist_name.lower())

    def output_search_results(self, results, value):
        """Displays search results.

        Args:
            results: List of Videos, the search results.
            value: The value used to search.
        """
        if not results:
            print(f"No search results for {value}")
            return
        results = sorted(results, key=lambda x: x.title)
        print(f"Here are the results for {value}:")
        for i, result in enumerate(results):
            print(f"{i+1}) {result}")
        print("Would you like to play any of the above? If yes, specify the number of the video.")
        print("If your answer is not a valid number, we will assume it's a no.")
        num = input()
        if num.isnumeric() and 0 <= int(num) <= len(results):
            self.play_video(results[int(num)-1].video_id)

    def search_videos(self, search_term):
        """Display all the videos whose titles contain the search_term.

        Args:
            search_term: The query to be used in search.
        """
        results = []
        for video in self._video_library.get_all_videos():
            if search_term.lower() in video.title.lower() and video.flag is None:
                results.append(video)
        self.output_search_results(results, search_term)

    def search_videos_tag(self, video_tag):
        """Display all videos whose tags contains the provided tag.

        Args:
            video_tag: The video tag to be used in search.
        """
        results = []
        for video in self._video_library.get_all_videos():
            if video_tag.lower() in video.tags and video.flag is None:
                results.append(video)
        self.output_search_results(results, video_tag)

    def flag_video(self, video_id, flag_reason="Not supplied"):
        """Mark a video as flagged.

        Args:
            video_id: The video_id to be flagged.
            flag_reason: Reason for flagging the video.
        """
        video = self._video_library.get_video(video_id)
        if not video:
            print("Cannot flag video: Video does not exist")
            return
        if video.flag is not None:
            print("Cannot flag video: Video is already flagged")
            return
        video.set_flag(flag_reason)
        if self._current_video and self._current_video.video_id == video.video_id:
            self.stop_video()
        print(f"Successfully flagged video: {video._title} (reason: {flag_reason})")

    def allow_video(self, video_id):
        """Removes a flag from a video.

        Args:
            video_id: The video_id to be allowed again.
        """
        video = self._video_library.get_video(video_id)
        if not self._video_library.get_video(video_id):
            print("Cannot remove flag from video: Video does not exist")
            return
        if not video.flag:
            print("Cannot remove flag from video: Video is not flagged")
            return
        print(f"Successfully removed flag from video: {video.title}")
        video.set_flag(None)
