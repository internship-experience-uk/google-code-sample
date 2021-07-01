import enum

class VideoPlaybackError(Exception):
    pass


class PlaybackState(enum.Enum):
    STOPPED = 0
    PAUSED = 1
    PLAYING = 2


class VideoPlayback:
    """A class to keep track of the currently playing video and it's state
    (PAUSED, STOPPED, PLAYING).

    We need to make sure we keep the two together because when no video is
    currently playing, it can also not be paused.
    """
    def __init__(self):
        self._video = None
        self._state = PlaybackState.STOPPED

    def play(self, video):
        self._video = video
        self._state = PlaybackState.PLAYING

    def pause(self):
        self._check_video()
        self._state = PlaybackState.PAUSED

    def resume(self):
        self._check_video()

        if self._state != PlaybackState.PAUSED:
            raise VideoPlaybackError("Video is not paused")

        self._state = PlaybackState.PLAYING

    def stop(self):
        self._check_video()
        self._video = None
        self._state = PlaybackState.STOPPED

    def get_video(self):
        self._check_video()
        return self._video

    @property
    def state(self):
        return self._state

    def _check_video(self):
        """Check to make sure that there is a video currently playing."""
        if self._video is None:
            raise VideoPlaybackError("No video is currently playing")
