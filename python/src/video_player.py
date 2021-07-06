"""A video player class."""

from .video_library import VideoLibrary
from .video import Video
from .video_playlist import Playlist

import enum


class Messages(enum.Enum):
    NO_VIDEO_PLAYING = "No video is currently playing"
    VIDEO_NOT_PAUSED = "Video is not paused"
    VIDEO_NOT_EXISTS = "Video does not exist"
    VIDEOS_NOT_AVAILABLE = "No videos available"
    PLAYLIST_NAME_EXISTS = "A playlist with the same name already exists"
    PLAYLIST_NOT_EXIST = "Playlist does not exist"
    VIDEO_IN_PLAYLIST = "Video already added"
    VIDEO_NOT_IN_PLAYLIST = "Video is not in playlist"
    NO_VIDEOS_IN_PLAYLIST = "No videos here yet"


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
        self.playlists = {}

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
            print("Cannot play video:", Messages.VIDEO_NOT_EXISTS.value)
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
            print("Cannot stop video:", Messages.NO_VIDEO_PLAYING.value)
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
            print(Messages.VIDEOS_NOT_AVAILABLE.value)

    def pause_video(self):
        """Pauses the current video."""
        if self.playing_status == PlayingStatus.PLAYING:
            print("Pausing video:", self.currently_playing.title)
            self.playing_status = PlayingStatus.PAUSED
        elif self.playing_status == PlayingStatus.PAUSED:
            print("Video already paused:", self.currently_playing.title)
        else:
            print("Cannot pause video:", Messages.NO_VIDEO_PLAYING.value)

    def continue_video(self):
        """Resumes playing the current video."""
        if self.playing_status == PlayingStatus.PAUSED:
            print("Continuing video:", self.currently_playing.title)
            self.playing_status = PlayingStatus.PLAYING
        elif self.playing_status == PlayingStatus.STOPPED or not self.currently_playing:
            print("Cannot continue video:", Messages.NO_VIDEO_PLAYING.value)
        else:
            print("Cannot continue video:", Messages.VIDEO_NOT_PAUSED.value)

    def show_playing(self):
        """Displays video currently playing."""
        if self.playing_status == PlayingStatus.STOPPED or not self.currently_playing:
            print(Messages.NO_VIDEO_PLAYING.value)

        if self.playing_status == PlayingStatus.PLAYING:
            print("Currently playing:", self.currently_playing.__str__())

        if self.playing_status == PlayingStatus.PAUSED:
            print("Currently playing:", self.currently_playing.__str__(), "-", PlayingStatus.PAUSED.value)

    def create_playlist(self, playlist_name):
        """Creates a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        name = playlist_name.lower()
        if not name in self.playlists:
            self.playlists[name] = Playlist(playlist_name)
            print("Successfully created new playlist:", self.playlists[name])
        else:
            print("Cannot create playlist:", Messages.PLAYLIST_NAME_EXISTS.value)

    def add_to_playlist(self, playlist_name, video_id):
        """Adds a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be added.
        """
        video = self._video_library.get_video(video_id)
        name = playlist_name.lower()
        if name in self.playlists and video:
            if self.playlists[name].add_video(video):
                print(f"Added video to {playlist_name}:", video.title)
            else:
                print(f"Cannot add video to {playlist_name}:", Messages.VIDEO_IN_PLAYLIST.value )
        elif name not in self.playlists:
            print(f"Cannot add video to {playlist_name}:", Messages.PLAYLIST_NOT_EXIST.value)
        elif not video:
            print(f"Cannot add video to {playlist_name}:", Messages.VIDEO_NOT_EXISTS.value)
        else:
            pass

    def show_all_playlists(self):
        """Display all playlists."""
        if self.playlists:
            playlist_names = list(self.playlists)
            playlist_names.sort()
            print("Showing all playlists:")
            for name in playlist_names:
                print(self.playlists[name])
        else:
            print("No playlists exist yet")

    def show_playlist(self, playlist_name):
        """Display all videos in a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        name = playlist_name.lower()
        if name in self.playlists:
            playlist = self.playlists[name]
            print("Showing playlist:", playlist_name)
            if len(playlist.videos) > 0:
                for video in playlist.videos:
                    print(video)
            else:
                print(Messages.NO_VIDEOS_IN_PLAYLIST.value)
        else:
            print(f"Cannot show playlist {playlist_name}:", Messages.PLAYLIST_NOT_EXIST.value)

    def remove_from_playlist(self, playlist_name, video_id):
        """Removes a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be removed.
        """
        name = playlist_name.lower()
        video = self._video_library.get_video(video_id)
        if name in self.playlists:
            playlist = self.playlists[name]
            if video:
                if self.playlists[name].remove_video(video):
                    print(f"Removed video from {playlist_name}:", video)
                else:
                    print(f"Cannot remove video from {playlist}:", Messages.VIDEO_NOT_IN_PLAYLIST.value )
            else:
                print(f"Cannot remove video from {playlist_name}:", Messages.VIDEO_NOT_EXISTS.value)
        else:
            print(f"Cannot remove video from {playlist_name}:", Messages.PLAYLIST_NOT_EXIST.value)

    def clear_playlist(self, playlist_name):
        """Removes all videos from a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        name = playlist_name.lower()
        if name in self.playlists:
            playlist = self.playlists[name]
            if len(playlist.videos) == 0:
                print(Messages.NO_VIDEOS_IN_PLAYLIST.value)
            else:
                playlist.clear_videos()
                print(f"Successfully removed all videos from {playlist_name}")
        else:
            print(f"Cannot clear playlist {playlist_name}:", Messages.PLAYLIST_NOT_EXIST.value)

    def delete_playlist(self, playlist_name):
        """Deletes a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        name  = playlist_name.lower()
        if name in self.playlists:
            self.playlists.pop(name)
            print("Deleted playlist:", name)
        else:
            print(f"Cannot delete playlist {playlist_name}:", Messages.PLAYLIST_NOT_EXIST.value)

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
