"""A video library class."""

from .video import Video
from pathlib import Path
import csv


# Helper Wrapper around CSV reader to strip whitespace from around
# each item.
def _csv_reader_with_strip(reader):
    yield from ((item.strip() for item in line) for line in reader)


class VideoLibrary:
    """A class used to represent a Video Library."""

    def __init__(self):
        """The VideoLibrary class is initialized."""
        self._is_playing = None
        self._is_paused = False
        self._videos = {}
        with open(Path(__file__).parent / "videos.txt") as video_file:
            reader = _csv_reader_with_strip(
                csv.reader(video_file, delimiter="|"))
            for video_info in reader:
                title, url, tags = video_info
                self._videos[url] = Video(
                    title,
                    url,
                    [tag.strip() for tag in tags.split(",")] if tags else [],
                )

    def get_all_videos(self):
        """Returns all available video information from the video library."""
        return list(self._videos.values())

    def get_video(self, video_id):
        """Returns the video object (title, url, tags) from the video library.

        Args:
            video_id: The video url.

        Returns:
            The Video object for the requested video_id. None if the video
            does not exist.
        """
        return self._videos.get(video_id, None)

    def get_video_playing(self):
        """Returns the playing video id."""
        return self._is_playing

    def is_video_paused(self):
        """Returns the paused video id."""
        return self._is_paused

    def set_video_playing(self, video_id):
        """Sets self._is_palying the respective video_id.

        Args:
            video_id: The video_id to be played.
        """
        self._is_playing = video_id
    
    def set_video_paused(self, status):
        """Swiches self._is_paused."""
        self._is_paused = status