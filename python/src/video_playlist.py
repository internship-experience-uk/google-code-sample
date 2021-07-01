"""A video playlist class."""

from .video_library import VideoLibrary

class Playlist:
    """A class used to represent a Playlist."""
    def __init__(self, _player, _name):
        self._player = _player
        self._name = _name
        self._videos = {}

    def get_name(self):
        """
        Returns name of the playlist
        :return: playlist_name
        """
        return self._name

    def get_videos(self):
        """
        Returns videos in the playlist
        :return: playlist_videos
        """
        return self._videos

    def add_video(self, playlist_name, video_id):
        """
        Custom method to add video to the playlist
        :param playlist_name:
        :param video_id:
        :return:
        """
        # 1. Check if video exists
        video_result = self._player.get_video_library().get_video(video_id)
        if video_result is None:
            print("Cannot add video to {0}: Video does not exist".format(playlist_name))
            return
        # 2. Part 4 FLAG_VIDEO task: Check if video is flagged
        if video_result.flag:
            print("Cannot add video to {0}: Video is currently flagged (reason: {1})"
                  .format(playlist_name, video_result.flag))
            return
        # 3. Check if video is already added
        if video_id in self._videos:
            print("Cannot add video to {0}: Video already added".format(playlist_name))
            return
        # 4. Add the video
        self._videos[video_id] = video_result
        print("Added video to {0}: {1}".format(playlist_name, video_result.title))

    def remove_video(self, playlist_name, video_id):
        """
        Custom method to remove video from the playlist
        :param playlist_name:
        :param video_id:
        :return:
        """
        # 1. Check if video exists
        exist_video = self._player.get_video_library().get_video(video_id)
        if not exist_video:
            print("Cannot remove video from {0}: Video does not exist".format(playlist_name))
            return
        # 2. Check if video in playlist
        is_video_in_playlist = video_id in self._videos
        if not is_video_in_playlist:
            print("Cannot remove video from {0}: Video is not in playlist".format(playlist_name))
            return
        # 3. Remove the video
        self._videos.pop(video_id)
        print("Removed video from {0}: {1}".format(playlist_name, exist_video.title))

    def clear(self, playlist_name):
        """
        Custom method to remove video from the playlist
        :param playlist_name:
        :return:
        """
        self._videos = {}
        print("Successfully removed all videos from {0}".format(playlist_name))