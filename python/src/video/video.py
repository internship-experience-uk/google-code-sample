"""A video class."""

from typing import List


class Video:
  """A class used to represent a Video."""

  def __init__(self, video_title: str, video_id: str, video_tags: List[str]):
    """Video constructor.

    Args:
      video_title: The video title.
      video_id: The video url.
      video_tags: The list of tags of a video.
    """
    self._title = video_title
    self._url = video_id
    self._tags = video_tags

  @property
  def title(self):
    """Returns the title of a video."""
    return self._title

  @property
  def url(self):
    """Returns the video url of a video."""
    return self._url

  @property
  def tags(self):
    """Returns the list of tags of a video."""
    return self._tags
