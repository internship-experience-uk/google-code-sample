"""A video playlist class."""

from .video import Video

class Playlist:
    """A class used to represent a Playlist."""

    def __init__(self, playlist_name: str):
        self.name = playlist_name
        self.videos = []

    def __str__(self) -> str:
        return self.name

    def add_video(self, video: Video) -> bool:
        """Adds a video to the playlist"""
        if video in self.videos:
            return False
        self.videos.append(video)
        return True

    def remove_video(self, video: Video) -> bool:
        """Removes a video from the playlist"""
        if video in self.videos:
            self.videos.remove(video)
            return True
        return False

    def clear_videos(self):
        """Removes all videos from playlist"""
        self.videos.clear()
