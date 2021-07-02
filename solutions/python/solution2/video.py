"""A video class."""

from typing import Sequence


class Video:
    """A class used to represent a Video."""

    def __init__(self, video_title: str, video_id: str, video_tags: Sequence[str]):
        """Video constructor."""
        self._title = video_title
        self._video_id = video_id

        # Turn the tags into a tuple here so it's unmodifiable,
        # in case the caller changes the 'video_tags' they passed to us
        self._tags = tuple(video_tags)
        self._flag = None

    @property
    def title(self) -> str:
        """Returns the title of a video."""
        return self._title

    @property
    def video_id(self) -> str:
        """Returns the video id of a video."""
        return self._video_id

    @property
    def tags(self) -> Sequence[str]:
        """Returns the list of tags of a video."""
        return self._tags

    @property
    def flag(self) -> str:
        """Returns the list of flag of a video."""
        return self._flag

    def set_flag(self, value):
        """Sets the flag of a video."""
        self._flag = value

    def __str__(self):
      flagged = ""
      if self._flag:
        flagged = f" - FLAGGED (reason: {self._flag})"
      return f"{self._title} ({self._video_id}) [{' '.join(self._tags)}]{flagged}"
