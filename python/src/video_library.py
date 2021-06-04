"""A video library class."""

from .video import Video
import os


class VideoLibrary:
    """A class used to represent a Video Library."""

    def __init__(self):
        """The VideoLibrary class is initialized."""
        self._videos = {}
        video_file_path = os.path.join(os.path.dirname(__file__), 'videos.txt')
        with open(video_file_path, "r") as video_file:
            for video_info in video_file.readlines():
                title, url, tags = video_info.split("|")
                self._videos[url.strip()] = Video(
                    title.strip(), url.strip(),
                    [tag.strip() for tag in tags.split(",")])

    def get_all_videos(self):
        """Returns all available video information from the video library."""
        return list(self._videos.values())

    def get_video(self, video_id):
        """Returns the a video object (title, url, tags) from the video library.

    Args:
        video_id: The video url.

    Returns:
        The Video object for the requested video_id.
    Raises:
        Exception: if the video is not found.
    """
        if video_id in self._videos:
            return self._videos[video_id]
        else:
            raise Exception("Video not found in video library.")
