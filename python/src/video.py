"""A video class."""

from typing import List


class Video:
    """A class used to represent a Video."""

    def __init__(self, video_title: str, video_id: str, video_tags: List[str]):
        """Video constructor."""
        self._title = video_title
        self._video_id = video_id
        self._tags = video_tags

    @property
    def title(self):
        """Returns the title of a video."""
        return self._title

    @property
    def video_id(self):
        """Returns the video id of a video."""
        return self._video_id

    @property
    def tags(self):
        """Returns the list of tags of a video."""
        return self._tags.copy()
