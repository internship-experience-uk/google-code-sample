"""A video playlist class."""


class Playlist:
    """A class used to represent a Playlist."""
    def __init__(self, name):
        self._name = name
        self._videos = []

    def __str__(self):
        return self._name

    def add_video(self, name, video):
        if video.flagged:
            print(f"Cannot add video to {name}: "
                  f"Video is currently flagged (reason: {video.flag_reason})")
            return

        if video in self._videos:
            print(f"Cannot add video to {name}: Video already added")
        else:
            print(f"Added video to {name}: {video.title}")
            self._videos.append(video)

    def remove_video(self, name, video):
        if video in self._videos:
            print(f"Removed video from {name}: {video.title}")
            self._videos.remove(video)
        else:
            print(f"Cannot remove video from {name}: "
                  f"Video is not in playlist")

    def clear(self, name):
        print(f"Successfully removed all videos from {name}")
        self._videos.clear()

    def show(self, name):
        print(f"Showing playlist: {name}")
        if not self._videos:
            print("  No videos here yet")
        for video in self._videos:
            print(f"  {video}")

    @property
    def name(self):
        return self._name
