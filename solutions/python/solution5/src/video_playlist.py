"""A video playlist class."""

from .video import Video


class Playlist:
    """A class used to represent a Playlist."""

    def __init__(self, name: str) -> None:
        """Initializes a playlist."""
        self._name: str = name
        self._videos: dict[str, Video] = {}

    def __str__(self) -> str:
        """Returns a string representation of a playlist."""
        return f"{self._name} ({len(self._videos)} videos)"

    def __contains__(self, video: Video) -> bool:
        """Checks whether the given video is contained within a playlist."""
        return video.video_id in self._videos

    @property
    def name(self) -> str:
        """Returns a name of a playlist."""
        return self._name

    def add(self, video: Video) -> None:
        """Adds the given video to a playlist."""
        self._videos[video.video_id] = video

    def remove(self, video: Video) -> None:
        """Removes the given video from a playlist."""
        self._videos.pop(video.video_id, None)

    def get_all_videos(self) -> list[Video]:
        """Returns a list of all videos from this playlist."""
        return list(self._videos.values())
