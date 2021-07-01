"""A video class."""

from typing import Sequence

class FlagError(Exception):
    pass

class Video:
    """A class used to represent a Video."""

    def __init__(self, video_title: str, video_id: str, video_tags: Sequence[str]):
        """Video constructor."""
        self._title = video_title
        self._video_id = video_id

        # Turn the tags into a tuple here so it's unmodifiable,
        # in case the caller changes the 'video_tags' they passed to us
        self._tags = tuple(video_tags)
        self._flag_reason = None

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
    def tags_string(self) -> str:
        return ' '.join(self.tags)

    def __str__(self):
        result = f'{self.title} ({self.video_id}) [{self.tags_string}]'
        if self.is_flagged:
            result += f' - FLAGGED {self.formatted_flag_reason}'
        return result

    def flag(self, flag_reason: str):
        if self.is_flagged:
            raise FlagError("Video is already flagged")
        self._flag_reason = flag_reason

    def unflag(self):
        if not self.is_flagged:
            raise FlagError("Video is not flagged")
        self._flag_reason = None

    @property
    def is_flagged(self):
        return self._flag_reason is not None

    def check_allowed(self):
        if self.is_flagged:
            raise FlagError(f"Video is currently flagged {self.formatted_flag_reason}")

    @property
    def formatted_flag_reason(self):
        if self.is_flagged:
            return f'(reason: {self._flag_reason})'
        else:
            return ''
