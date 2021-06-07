"""A video library class."""

from .video import Video
from pathlib import Path
import csv


# Helper Wrapper around CSV reader to strip whitespace from around
# each item.
def _csv_reader_with_strip(reader):
    yield from ((item.strip() for item in line) for line in reader)


def _split_tags_ignoring_whitespace(tags):
    for tag in tags.split(","):
        tag = tag.strip()
        if tag:
            yield tag


class VideoLibrary:
    """A class used to represent a Video Library."""

    def __init__(self):
        """The VideoLibrary class is initialized."""
        self._videos = {}
        with open(Path(__file__).parent / "videos.txt") as video_file:
            reader = _csv_reader_with_strip(
                csv.reader(video_file, delimiter="|"))
            for video_info in reader:
                title, url, tags = video_info
                self._videos[url] = Video(
                    title,
                    url,
                    tuple(_split_tags_ignoring_whitespace(tags)),
                )

    def get_all_videos(self):
        """Returns all available video information from the video library."""
        return list(self._videos.values())

    def get_video(self, video_id):
        """Returns the a video object (title, url, tags) from the video library.

    Args:
        video_id: The video url.

    Returns:
        The Video object for the requested video_id. None if the video doesn't
        exist
    """
        return self._videos.get(video_id, None)
