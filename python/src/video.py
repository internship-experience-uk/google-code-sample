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
        self._flagged = False
        self._flag_reason = None

    def __str__(self):
        tags_str = " ".join(self._tags)
        video_str = f"{self._title} ({self._video_id}) [{tags_str}]"
        if self._flagged:
            video_str += f" - FLAGGED (reason: {self._flag_reason})"
        return video_str

    def play(self):
        if self._flagged:
            print(f"Cannot play video: Video is currently flagged (reason: {self._flag_reason})")
        else:
            print(f"Playing video: {self}")

    def stop(self):
        print(f"Stopping video: {self._title}")

    def flag(self, reason):
        if self._flagged:
            print(f"Cannot flag video: Video is already flagged")
        else:
            self._flagged = True
            if not reason:
                reason = 'Not supplied'
            self._flag_reason = reason
            print(f"Successfully flagged video: {self._title} (reason: {reason})")

    def allow(self):
        if self._flagged:
            self._flagged = False
            self._flag_reason = None
            print(f"Successfully removed flag from video: {self._title}")
        else:
            print("Cannot remove flag from video: Video is not flagged")

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
    def flagged(self):
        return self._flagged

    @property
    def flag_reason(self):
        return self._flag_reason
