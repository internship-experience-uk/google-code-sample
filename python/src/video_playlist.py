"""A video playlist class."""


class Playlist:
    """A class used to represent a Playlist."""
    def __init__(self, name):
        self._name = name
        self._videos = []

    def __str__(self):
        return self._name

    def add_video(self, video):
        if video.flagged:
            print(f"Cannot add video to playlist {self._name}: "
                  f"Video is currently flagged (reason: {video.flag_reason})")

        if video in self._videos:
            print(f"Cannot add video to {self._name}: Video already added")
        else:
            print(f"Added video to {self._name}: {video.title}")
            self._videos.append(video)

    def remove_video(self, video):
        if video in self._videos:
            print(f"Removed video from {self._name}: {video.title}")
            self._videos.remove(video)
        else:
            print(f"Cannot remove video from {self._name}: "
                  f"Video is not in playlist")

    def clear(self):
        print(f"Successfully removed all videos from {self._name}")
        self._videos.clear()

    def show(self):
        print(f"Showing Playlist: {self._name}")
        if not self._videos:
            print("  No videos here yet")
        for video in self._videos:
            print(f"  {video}")

    @property
    def name(self):
        return self._name
