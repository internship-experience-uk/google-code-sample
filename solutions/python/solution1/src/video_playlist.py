"""A video playlist class."""

class VideoPlaylistError(Exception):
    pass


class VideoPlaylist:
    """A class used to represent a Playlist."""

    def __init__(self, name:str):
        self._name = name
        self._videos = []

    @property
    def name(self):
        return self._name

    @property
    def videos(self):
        return tuple(self._videos)

    def __contains__(self, video):
        return video in self._videos

    def add_video(self, video):
        if video in self:
            raise VideoPlaylistError("Video already added")
        self._videos.append(video)

    def remove_video(self, video):
        if video not in self:
            raise VideoPlaylistError("Video is not in playlist")
        self._videos.remove(video)

    def clear(self):
        self._videos.clear()

    def __str__(self):
        return self._name
