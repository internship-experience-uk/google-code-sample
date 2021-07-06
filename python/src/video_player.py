"""A video player class."""

from .video_library import VideoLibrary
from .video import Video
import enum


class Messages(enum.Enum):
    NOT_PLAYING = "No video is currently playing"
    NOT_PAUSED = "Video is not paused"
    NOT_EXISTS = "Video does not exist"
    NOT_AVAILABLE = "No videos available"


class PlayingStatus(enum.Enum):
    PLAYING = "PLAYING"
    PAUSED = "PAUSED"
    STOPPED = "STOPPED"


class VideoPlayer:
    """A class used to represent a Video Player."""

    def __init__(self):
        self._video_library = VideoLibrary()
        self.currently_playing = None
        self.playing_status = None

    def number_of_videos(self):
        num_videos = len(self._video_library.get_all_videos())
        print(f"{num_videos} videos in the library")

    def show_all_videos(self):
        """Returns all videos."""
        print("Here's a list of all available videos:")
        library = []
        for video in self._video_library.get_all_videos():
            library.append(video.__str__())

        library.sort()
        for video in library:
            print(video)

    def play_video(self, video_id):
        """Plays the respective video.

        Args:
            video_id: The video_id to be played.
        """
        video = self._video_library.get_video(video_id)
        if not video:
            print("Cannot play video:", Messages.NOT_EXISTS.value)
        else:
            self.play_known_video(video)

    def play_known_video(self, video: Video):
        """Plays a known video that exists in the Video Library

        Args:
            video: An already known video that exists in the video library
        """
        if self.playing_status in [PlayingStatus.PLAYING, PlayingStatus.PAUSED]:
            self.stop_video()

        print("Playing video:", video.title)
        self.currently_playing = video
        self.playing_status = PlayingStatus.PLAYING

    def stop_video(self):
        """Stops the current video."""
        if self.playing_status == PlayingStatus.STOPPED or not self.currently_playing:
            print("Cannot stop video:", Messages.NOT_PLAYING.value)
        else:
            print("Stopping video:", self.currently_playing.title)
            self.playing_status = PlayingStatus.STOPPED

    def play_random_video(self):
        """Plays a random video from the video library."""
        import random

        try:
            video = random.choice(self._video_library.get_all_videos())
            self.play_known_video(video)
        except IndexError:
            print(Messages.NOT_AVAILABLE.value)

    def pause_video(self):
        """Pauses the current video."""
        if self.playing_status == PlayingStatus.PLAYING:
            print("Pausing video:", self.currently_playing.title)
            self.playing_status = PlayingStatus.PAUSED
        elif self.playing_status == PlayingStatus.PAUSED:
            print("Video already paused:", self.currently_playing.title)
        else:
            print("Cannot pause video:", Messages.NOT_PLAYING.value)

    def continue_video(self):
        """Resumes playing the current video."""
        if self.playing_status == PlayingStatus.PAUSED:
            print("Continuing video:", self.currently_playing.title)
            self.playing_status = PlayingStatus.PLAYING
        elif self.playing_status == PlayingStatus.STOPPED or not self.currently_playing:
            print("Cannot continue video:", Messages.NOT_PLAYING.value)
        else:
            print("Cannot continue video:", Messages.NOT_PAUSED.value)

    def show_playing(self):
        """Displays video currently playing."""
        if self.playing_status == PlayingStatus.STOPPED or not self.currently_playing:
            print(Messages.NOT_PLAYING.value)

        if self.playing_status == PlayingStatus.PLAYING:
            print("Currently playing:", self.currently_playing.__str__())

        if self.playing_status == PlayingStatus.PAUSED:
            print("Currently playing:", self.currently_playing.__str__(), "-", PlayingStatus.PAUSED.value)

    def create_playlist(self, playlist_name):
        """Creates a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        print("create_playlist needs implementation")

    def add_to_playlist(self, playlist_name, video_id):
        """Adds a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be added.
        """
        print("add_to_playlist needs implementation")

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
        print("remove_from_playlist needs implementation")

    def clear_playlist(self, playlist_name):
        """Removes all videos from a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        print("clears_playlist needs implementation")

    def delete_playlist(self, playlist_name):
        """Deletes a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        print("deletes_playlist needs implementation")

    def search_videos(self, search_term):
        """Display all the videos whose titles contain the search_term.

        Args:
            search_term: The query to be used in search.
        """
        print("search_videos needs implementation")

    def search_videos_tag(self, video_tag):
        """Display all videos whose tags contains the provided tag.

        Args:
            video_tag: The video tag to be used in search.
        """
        print("search_videos_tag needs implementation")

    def flag_video(self, video_id, flag_reason=""):
        """Mark a video as flagged.

        Args:
            video_id: The video_id to be flagged.
            flag_reason: Reason for flagging the video.
        """
        print("flag_video needs implementation")

    def allow_video(self, video_id):
        """Removes a flag from a video.

        Args:
            video_id: The video_id to be allowed again.
        """
        print("allow_video needs implementation")
