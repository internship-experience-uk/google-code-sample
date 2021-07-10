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

        # custom property: flag
        self._flag = None

    def __str__(self):
        """Custom String Representation"""
        return "{title} ({id}) [{tags}]".format(title=self._title, id=self._video_id, tags=" ".join(self._tags))

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

    # Custom property: flag
    @property
    def flag(self) -> str:
        """Returns the flag of a video."""
        return self._flag

    # Custom flag video method
    def action_flag(self, reason):
        self._flag = reason

    # Custom flag removal method
    def remove_flag(self):
        self._flag = None
