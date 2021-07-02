"""A video library class."""

from typing import Sequence, Optional

import csv
import random
from pathlib import Path

from .video import Video


# Helper Wrapper around CSV reader to strip whitespace from around
# each item.
def _csv_reader_with_strip(reader):
    yield from ((item.strip() for item in line) for line in reader)


class VideoLibraryError(Exception):
    pass


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
                    [tag.strip() for tag in tags.split(",")] if tags else [],
                )

    def get_all_videos(self) -> Sequence[Video]:
        """Returns all available video information from the video library."""
        return list(sorted(self._videos.values(), key=str))

    def get_allowed_videos(self) -> Sequence[Video]:
        """Returns all allowed videos in the library."""
        return [v for v in self.get_all_videos() if not v.is_flagged]

    def __getitem__(self, video_id):
        """This is a way to make the Video library behave like a python
        dictionary. So now we can do video_library[video_id] and it will
        return the video if it exists ot throw a VideoLibraryError.

        See also: https://www.kite.com/python/answers/how-to-override-the-[]-operator-in-python
        """
        try:
            return self._videos[video_id]
        except KeyError:
            raise VideoLibraryError("Video does not exist")

    def get_video(self, video_id: str) -> Optional[Video]:
        """Returns the video object (title, url, tags) from the video library.

        Args:
            video_id: The video url.

        Returns:
            The Video object for the requested video_id. None if the video
            does not exist.
        """
        return self._videos.get(video_id, None)

    def get_random_video_id(self) -> Optional[str]:
        """Returns a Random Video id from the list of allowed videos.
        If there are no videos available (e.g. all of them are flagged or
        something else happened) we return None.
        """
        try:
            return random.choice([video.video_id for video in self.get_allowed_videos()])
        except IndexError:
            return None

    def search_videos(self, search_term: str):
        """Search through all the titles (in lower case) and return if the title
        contains the search term."""
        search_term = search_term.lower()
        return [v for v in self.get_allowed_videos() if search_term in v.title.lower()]

    def get_videos_with_tag(self, tag: str):
        """Search through all the tags and return all videos whose tags
        contain the search tag."""
        return [v for v in self.get_allowed_videos() if tag in v.tags]
