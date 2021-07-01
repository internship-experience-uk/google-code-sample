"""A video player class."""

from random import choice
from typing import Optional
from .video import Video
from .video_library import VideoLibrary
from .video_playlist import Playlist


class VideoPlayer:
    """A class used to represent a Video Player."""

    def __init__(self) -> None:
        self._library: VideoLibrary = VideoLibrary()
        self._current: Optional[Video] = None
        self._paused: bool = False
        self._playlists: dict[str, Playlist] = {}
        self._flags: dict[str, str] = {}

    def number_of_videos(self) -> None:
        num_videos = len(self._library.get_all_videos())
        print(f"{num_videos} videos in the library")

    def show_all_videos(self) -> None:
        """Returns all videos."""
        print("Here's a list of all available videos:")

        videos = self._get_sorted_videos()
        self._print_videos(videos)

    def play_video(self, video_id: str) -> None:
        """Plays the respective video.

        Args:
            video_id: The video_id to be played.
        """
        video = self._library.get_video(video_id)
        if video is None:
            print("Cannot play video: Video does not exist")
            return

        flag_reason = self._flags.get(video_id, "")
        if flag_reason:
            print(f"Cannot play video: "
                  f"Video is currently flagged (reason: {flag_reason})")
            return

        if self._current is not None:
            print(f"Stopping video: {self._current.title}")

        print(f"Playing video: {video.title}")
        self._current = video
        self._paused = False

    def stop_video(self) -> None:
        """Stops the current video."""
        if self._current is None:
            print("Cannot stop video: No video is currently playing")
            return

        print(f"Stopping video: {self._current.title}")
        self._current = None
        self._paused = False

    def play_random_video(self) -> None:
        """Plays a random video from the video library."""
        videos = self._filter_flagged_videos(self._library.get_all_videos())
        if not videos:
            print("No videos available")
            return

        video = choice(videos)
        self.play_video(video.video_id)

    def pause_video(self) -> None:
        """Pauses the current video."""
        if self._current is None:
            print("Cannot pause video: No video is currently playing")
            return

        if self._paused:
            print(f"Video already paused: {self._current.title}")
            return

        print(f"Pausing video: {self._current.title}")
        self._paused = True

    def continue_video(self) -> None:
        """Resumes playing the current video."""
        if self._current is None:
            print("Cannot continue video: No video is currently playing")
            return

        if not self._paused:
            print("Cannot continue video: Video is not paused")
            return

        print(f"Continuing video: {self._current.title}")
        self._paused = False

    def show_playing(self) -> None:
        """Displays video currently playing."""
        if self._current is None:
            print("No video is currently playing")
            return

        if self._paused:
            print(f"Currently playing: {self._current} - PAUSED")
        else:
            print(f"Currently playing: {self._current}")

    def create_playlist(self, playlist_name: str) -> None:
        """Creates a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        if playlist_name.lower() in self._playlists:
            print("Cannot create playlist: "
                  "A playlist with the same name already exists")
            return

        self._playlists[playlist_name.lower()] = Playlist(playlist_name)
        print(f"Successfully created new playlist: {playlist_name}")

    def add_to_playlist(self, playlist_name: str, video_id: str) -> None:
        """Adds a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be added.
        """
        playlist = self._playlists.get(playlist_name.lower(), None)
        if playlist is None:
            print(f"Cannot add video to {playlist_name}: "
                  f"Playlist does not exist")
            return

        video = self._library.get_video(video_id)
        if video is None:
            print(f"Cannot add video to {playlist_name}: Video does not exist")
            return

        flag_reason = self._flags.get(video_id, "")
        if flag_reason:
            print(f"Cannot add video to {playlist_name}: "
                  f"Video is currently flagged (reason: {flag_reason})")
            return

        if video in playlist:
            print(f"Cannot add video to {playlist_name}: Video already added")
            return

        playlist.add(video)
        print(f"Added video to {playlist_name}: {video.title}")

    def show_all_playlists(self) -> None:
        """Display all playlists."""
        if not self._playlists:
            print("No playlists exist yet")
            return

        playlists = sorted(self._playlists.values(), key=lambda p: p.name)

        print("Showing all playlists:")
        for playlist in playlists:
            print(f"  {playlist}")

    def show_playlist(self, playlist_name: str) -> None:
        """Display all videos in a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        playlist = self._playlists.get(playlist_name.lower(), None)
        if playlist is None:
            print(f"Cannot show playlist {playlist_name}: "
                  f"Playlist does not exist")
            return

        print(f"Showing playlist: {playlist_name}")

        videos = playlist.get_all_videos()
        if not videos:
            print("  No videos here yet")
            return

        self._print_videos(videos)


    def remove_from_playlist(self, playlist_name: str, video_id: str) -> None:
        """Removes a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be removed.
        """
        playlist = self._playlists.get(playlist_name.lower(), None)
        if playlist is None:
            print(f"Cannot remove video from {playlist_name}: "
                  f"Playlist does not exist")
            return

        video = self._library.get_video(video_id)
        if video is None:
            print(f"Cannot remove video from {playlist_name}: "
                  f"Video does not exist")
            return

        if video not in playlist:
            print(f"Cannot remove video from {playlist_name}: "
                  f"Video is not in playlist")
            return

        playlist.remove(video)
        print(f"Removed video from {playlist_name}: {video.title}")

    def clear_playlist(self, playlist_name: str) -> None:
        """Removes all videos from a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        playlist = self._playlists.get(playlist_name.lower(), None)
        if playlist is None:
            print(f"Cannot clear playlist {playlist_name}: "
                  f"Playlist does not exist")
            return

        self._playlists[playlist_name.lower()] = Playlist(playlist.name)
        print(f"Successfully removed all videos from {playlist_name}")

    def delete_playlist(self, playlist_name: str) -> None:
        """Deletes a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        playlist = self._playlists.get(playlist_name.lower(), None)
        if playlist is None:
            print(f"Cannot delete playlist {playlist_name}: "
                  f"Playlist does not exist")
            return

        del self._playlists[playlist_name.lower()]
        print(f"Deleted playlist: {playlist_name}")

    def search_videos(self, search_term: str) -> None:
        """Display all the videos whose titles contain the search_term.

        Args:
            search_term: The query to be used in search.
        """
        videos = self._get_sorted_videos()
        videos = [v for v in videos if search_term.lower() in v.title.lower()]

        self._print_search_results(search_term, videos)

    def search_videos_tag(self, video_tag: str) -> None:
        """Display all videos whose tags contains the provided tag.

        Args:
            video_tag: The video tag to be used in search.
        """
        videos = self._get_sorted_videos()
        videos = [v for v in videos if video_tag.lower() in v.tags]

        self._print_search_results(video_tag, videos)

    def flag_video(self, video_id: str, flag_reason: str = "") -> None:
        """Mark a video as flagged.

        Args:
            video_id: The video_id to be flagged.
            flag_reason: Reason for flagging the video.
        """
        if video_id in self._flags:
            print("Cannot flag video: Video is already flagged")
            return

        video = self._library.get_video(video_id)
        if video is None:
            print("Cannot flag video: Video does not exist")
            return

        if video is self._current:
            self.stop_video()

        if not flag_reason:
            flag_reason = "Not supplied"

        self._flags[video_id] = flag_reason
        print(f"Successfully flagged video: {video.title} "
              f"(reason: {flag_reason})")

    def allow_video(self, video_id: str) -> None:
        """Removes a flag from a video.

        Args:
            video_id: The video_id to be allowed again.
        """
        video = self._library.get_video(video_id)
        if video is None:
            print("Cannot remove flag from video: Video does not exist")
            return

        if video_id not in self._flags:
            print("Cannot remove flag from video: Video is not flagged")
            return

        del self._flags[video_id]
        print(f"Successfully removed flag from video: {video.title}")

    def _filter_flagged_videos(self, videos: list[Video]) -> list[Video]:
        return [v for v in videos if v.video_id not in self._flags]

    def _get_sorted_videos(self) -> list[Video]:
        return sorted(self._library.get_all_videos(), key=lambda v: v.title)

    def _print_search_results(self, search: str, videos: list[Video]) -> None:
        """Prints search results and suggests to play a video."""
        videos = self._filter_flagged_videos(videos)

        if not videos:
            print(f"No search results for {search}")
            return

        print(f"Here are the results for {search}:")
        for idx, video in enumerate(videos, start=1):
            print(f"  {idx}) {video}")

        print("Would you like to play any of the above? "
              "If yes, specify the number of the video. ")
        print("If your answer is not a valid number, we will assume it's a no.")

        try:
            idx = int(input())
            if idx < 1 or idx > len(videos):
                raise ValueError()
        except ValueError:
            return

        self.play_video(videos[idx - 1].video_id)

    def _print_videos(self, videos: list[Video]) -> None:
        """Prints a list of videos."""
        for video in videos:
            flag_reason = self._flags.get(video.video_id, "")
            if flag_reason:
                print(f"  {video} - FLAGGED (reason: {flag_reason})")
            else:
                print(f"  {video}")
