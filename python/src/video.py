"""A video class."""

from typing import Sequence


class Video:
    """A class used to represent a Video."""

    def __init__(self, video_title: str, video_id: str, video_tags: Sequence[str],
                 video_flagged=False, video_flagged_reason="Not supplied"):
        """Video constructor."""
        self._title = video_title
        self._video_id = video_id
        self._flagged = video_flagged
        self._flagged_reason = video_flagged_reason
        # Turn the tags into a tuple here so it's unmodifiable,
        # in case the caller changes the 'video_tags' they passed to us
        self._tags = tuple(video_tags)

    @property
    def title(self) -> str:
        """Returns the title of a video."""
        return self._title

    @property
    def video_id(self) -> str:
        """Returns the video id of a video."""
        return self._video_id

    @property
    def flagged(self) -> bool:
        """Returns the video flag of a video."""
        return self._flagged

    @flagged.setter
    def flagged(self, flag):
        """Set the video flag of a video."""
        self._flagged = flag

    @property
    def flagged_reason(self) -> str:
        """Returns the video flagged_reason of a video."""
        return self._flagged_reason if self._flagged else None

    @flagged_reason.setter
    def flagged_reason(self, reason="Not supplied"):
        """Set the video flagged_reason of a video."""
        self._flagged_reason = reason

    @property
    def tags(self) -> Sequence[str]:
        """Returns the list of tags of a video."""
        return self._tags

    def __str__(self):
        return f"{self.title} ({self.video_id}) [{' '.join(list(self.tags))}]"
