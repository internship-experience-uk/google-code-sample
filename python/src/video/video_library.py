"""A video library class."""

from ..video import video


class VideoLibrary:
    """A class used to represent a Video Library.

    Methods
    -------
    get_videos(name)
        Return all available video information from the video library.
    get_video(name, video_id)
        Returns a video object from the video library.
  """

    def __init__(self):
        """The VideoLibrary class is initialized."""
        self._videos = {}
        with open("videos.txt", "r") as video_file:
            for video_info in video_file.readlines():
                title, url, tags = video_info.split("|")
                self._videos[url.strip()] = video.Video(
                    title.strip(), url.strip(),
                    [tag.strip() for tag in tags.split(",")])

    def get_videos(self):
        """Returns all available video information from the video library."""
        return self._videos.values()

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
