"""A video playlist class."""

from collections import deque

class Playlist:
    """A class used to represent a Playlist."""

    def __init__(self, name):
        self._name = name
        self._videos = deque([])
    
    def get_name(self):
        """Returns the name of playlist."""
        return self._name
    
    def get_all_videos(self):
        """Returns all the videos."""
        return self._videos

    def add_video(self, video_id):
        self._videos.append(video_id)

    def remove_video(self, video_id):
        self._videos.remove(video_id)

    def clear_videos(self):
        self._videos.clear()