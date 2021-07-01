"""A video playlist class."""

class VideoPlaylistError(Exception):
    pass


class VideoPlaylist:
    """A class used to represent a Playlist."""

    def __init__(self, name:str):
        self._name = name
        # Keep the videos as a list
        self._videos = []

    @property
    def name(self):
        return self._name

    @property
    def videos(self):
        return tuple(self._videos)

    def __contains__(self, video):
        """Overloading this method will allow us to use the python "in"
        operator. So now we can do `if video in playlist` like it was a list."""
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
        """Overloading __str__ allows us to use print(..) with this object.
                When we do print(playlist) we just want to print the name."""
        return self._name
