"""A video playlist class."""
from typing import Sequence

from .video import Video


class Playlist:
    """A class used to represent a Playlist."""

    def __init__(self, playlist_name: str):
        """Video constructor."""
        self._name = playlist_name
        self._contents = []

    @property
    def name(self) -> str:
        """Returns the title of a video."""
        return self._name

    @property
    def content(self) -> Sequence[Video]:
        """Returns the list of video which in this playlist."""
        return self._contents

    def add_video(self, video: Video):
        self._contents.append(video)

    def remove_video(self, video: Video):
        self._contents.remove(video)

    def find_video(self, video: Video):
        if len(self._contents) == 0:
            return False
        else:
            for content in self._contents:
                if video.video_id == content.video_id:
                    return True
                else:
                    return False

    def clear(self):
        self._contents.clear()