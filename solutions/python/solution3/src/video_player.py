"""A video player class."""
import random

from .video_library import VideoLibrary
from .video_playlist import PlaylistLibrary

class PlayState:
    def __init__(self, v=None):
        self.video = v
        self.paused = False

class VideoPlayer:
    """A class used to represent a Video Player."""

    def __init__(self):
        """The VideoPlayer class is initialized."""
        self._video_library = VideoLibrary()
        self._current_playing = None
        self._playlists = PlaylistLibrary()

    def number_of_videos(self):
        num_videos = len(self._video_library.get_all_videos())
        print(f"{num_videos} videos in the library")

    def show_all_videos(self):
        """Returns all videos."""
        print("Here's a list of all available videos:")
        for v in sorted(self._video_library.get_all_videos(), key=lambda v: v.title):
            print(f"  {v.format()}")

    def play_video(self, video_id):
        """Plays the respective video.

        Args:
            video_id: The video_id to be played.
        """
        video = self._video_library.get_video(video_id)
        if video is None:
            print("Cannot play video: Video does not exist")
            return
        if video.flag:
            print(f"Cannot play video: Video is currently flagged (reason: {video.flag})")
            return
        self.stop_video(silent=True)
        print("Playing video:", video.title)
        self._current_playing = PlayState(video)

    def stop_video(self, silent=False, very_silent=False):
        """Stops the current video."""
        if self._current_playing is None:
            if not silent and not very_silent:
                print("Cannot stop video: No video is currently playing")
            return False
        if not very_silent:
            print("Stopping video:", self._current_playing.video.title)
        self._current_playing = None
        self._paused = False
        return True

    def play_random_video(self):
        """Plays a random video from the video library."""
        videos = self._video_library.get_all_unflagged_videos()
        if not videos:
            print("No videos available")
            return
        v = random.choice(videos)
        self.play_video(v.video_id)

    def pause_video(self):
        """Pauses the current video."""
        if self._current_playing is None:
            print("Cannot pause video: No video is currently playing")
            return
        if self._current_playing.paused:
            print("Video already paused:", self._current_playing.video.title)
            return
        self._current_playing.paused = True
        print("Pausing video:", self._current_playing.video.title)

    def continue_video(self):
        """Resumes playing the current video."""
        if self._current_playing is None:
            print("Cannot continue video: No video is currently playing")
            return
        if not self._current_playing.paused:
            print("Cannot continue video: Video is not paused")
            return
        print("Continuing video:", self._current_playing.video.title)
        self._current_playing.paused = False

    def show_playing(self):
        """Displays video currently playing."""
        if self._current_playing is None:
            print("No video is currently playing")
            return
        print("Currently playing:", self._current_playing.video.format(),
              "- PAUSED" * self._current_playing.paused)

    def create_playlist(self, playlist_name):
        """Creates a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        if not self._playlists.create_playlist(playlist_name):
            return
        print("Successfully created new playlist:", playlist_name)

    def add_to_playlist(self, playlist_name, video_id):
        """Adds a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be added.
        """
        p = self._playlists.get_playlist(playlist_name)
        if p is None:
            print(f"Cannot add video to {playlist_name}: Playlist does not exist")
            return
        v = self._video_library.get_video(video_id)
        if v is None:
            print(f"Cannot add video to {playlist_name}: Video does not exist")
            return
        if v.flag:
            print(f"Cannot add video to {playlist_name}: Video is currently flagged (reason: {v.flag})")
            return
        if not p.add_video(v):
            print(f"Cannot add video to {playlist_name}: Video already added")
            return
        print(f"Added video to {playlist_name}: {v.title}")
        

    def show_all_playlists(self):
        """Display all playlists."""

        print("show_all_playlists needs implementation")

    def show_playlist(self, playlist_name):
        """Display all videos in a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        print("show_playlist needs implementation")

    def remove_from_playlist(self, playlist_name, video_id):
        """Removes a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be removed.
        """
        p = self._playlists.get_playlist(playlist_name)
        if p is None:
            print(f"Cannot remove video from {playlist_name}: Playlist does not exist")
            return
        v = self._video_library.get_video(video_id)
        if v is None:
            print(f"Cannot remove video from {playlist_name}: Video does not exist")
            return
        if not p.remove_video(video_id):
            print(f"Cannot remove video from {playlist_name}: Video is not in playlist")
            return
        print(f"Removed video from {playlist_name}: {v.title}")


    def clear_playlist(self, playlist_name):
        """Removes all videos from a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        p = self._playlists.get_playlist(playlist_name)
        if p is None:
            print(f"Cannot clear playlist {playlist_name}: Playlist does not exist")
            return
        p.clear()
        print(f"Successfully removed all videos from {playlist_name}")

    def delete_playlist(self, playlist_name):
        """Deletes a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        self._playlists.delete_playlist(playlist_name)

    def show_playlist(self, playlist_name):
        """Display all videos in a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        p = self._playlists.get_playlist(playlist_name)
        if p is None:
            print(f"Cannot show playlist {playlist_name}: Playlist does not exist")
            return
        print("Showing playlist:", playlist_name)
        if not p.videos:
            print("  No videos here yet")
            return
        for v in p.videos:
            print(f"  {v.format()}")

    def show_all_playlists(self):
        """Display all playlists."""
        ps = self._playlists.get_all_playlists()
        if not ps:
            print("No playlists exist yet.")
            return
        print("Showing all playlists:")
        for p in sorted(ps, key=lambda p: p.playlist_id):
            print(f"  {p.playlist_id} ({len(p.videos)} videos)")

    def _search_selection(self, search_term, res):
        results = sorted(res, key=lambda v: v.title)
        if not results:
            print(f"No search results for {search_term}")
            return
        print(f"Here are the results for {search_term}:")
        for i, v in enumerate(results):
            print(f"  {i+1}) {v.format()}")
        print("Would you like to play any of the above? If yes, specify the number of the video.")
        print("If your answer is not a valid number, we will assume it's a no.")
        selection = input()
        if not selection.isnumeric():
            return
        sel = int(selection)
        if sel < 1 or sel > len(results):
            return
        self.play_video(results[sel - 1].video_id)

    def search_videos(self, search_term):
        """Display all the videos whose titles contain the search_term.

        Args:
            search_term: The query to be used in search.
        """
        self._search_selection(
            search_term,
            filter(
                lambda v: search_term.lower() in v.title.lower(),
                self._video_library.get_all_unflagged_videos()))

    def search_videos_tag(self, video_tag):
        """Display all videos whose tags contains the provided tag.

        Args:
            video_tag: The video tag to be used in search.
        """
        self._search_selection(
            video_tag,
            filter(
                lambda v: video_tag.lower() in {t.lower() for t in v.tags},
                self._video_library.get_all_unflagged_videos()))

    def flag_video(self, video_id, flag_reason="Not supplied"):
        """Mark a video as flagged.

        Args:
            video_id: The video_id to be flagged.
            flag_reason: Reason for flagging the video.
        """
        v = self._video_library.get_video(video_id)
        if v is None:
            print("Cannot flag video: Video does not exist")
            return
        if v.flag:
            print("Cannot flag video: Video is already flagged")
            return
        if self._current_playing is not None and self._current_playing.video.video_id == video_id:
            self.stop_video()
        v.set_flag(flag_reason)
        print(f"Successfully flagged video: {v.title} (reason: {flag_reason})")
        if self._current_playing and self._current_playing.video.video_id == video_id:
            self.stop_video(very_silent=True)

    def allow_video(self, video_id):
        """Removes a flag from a video.

        Args:
            video_id: The video_id to be allowed again.
        """
        v = self._video_library.get_video(video_id)
        if v is None:
            print("Cannot remove flag from video: Video does not exist")
            return
        if not v.flag:
            print("Cannot remove flag from video: Video is not flagged")
            return
        v.set_flag(flag=None)
        print("Successfully removed flag from video:", v.title)

