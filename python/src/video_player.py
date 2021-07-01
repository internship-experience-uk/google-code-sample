"""A video player class."""

from .video_library import VideoLibrary
from .video_playlist import Playlist
import random
import re

class VideoPlayer:
    """A class used to represent a Video Player."""

    def __init__(self):
        self._video_library = VideoLibrary()
        self._video_playing = None
        self._video_paused = False
        self._playlists = list()

    def get_video_library(self):
        return self._video_library

    def number_of_videos(self):
        num_videos = len(self._video_library.get_all_videos())
        print(f"{num_videos} videos in the library")

    def show_all_videos(self):
        """Returns all videos."""
        print("Here's a list of all available videos:")
        videos = self._video_library.get_all_videos()
        for vid in sorted(videos, key=lambda v: v.title[0]):
            # Part 4 FLAG_VIDEO task: add flag when showing flagged videos
            print("\t{0}{1}".format(vid, " - FLAGGED (reason: {0})".format(vid.flag) if vid.flag else ""))
        # print("show_all_videos needs implementation")

    def play_video(self, video_id):
        """Plays the respective video.

        Args:
            video_id: The video_id to be played.
        """
        # print("play_video needs implementation")
        # 1. Search video by id
        video_result = self._video_library.get_video(video_id)
        if video_result is None:
            print("Cannot play video: Video does not exist")
            return
        # 2. Part 4 FLAG_VIDEO task: Check if the video is flagged
        if video_result.flag:
            print("Cannot play video: Video is currently flagged (reason: {0})".format(video_result.flag))
            return
        # 3. Check and Try stop playing video
        if self._video_playing is not None:
            self.stop_video()
        # 4. Play video
        print("Playing video: {0}".format(video_result.title))
        self._video_playing = video_result
        self._video_paused = False

    def stop_video(self):
        """Stops the current video."""
        # print("stop_video needs implementation")
        # 1. Check if any video is playing
        if self._video_playing is None:
            print("Cannot stop video: No video is currently playing")
            return
        # 2. Stop playing video
        print("Stopping video: {0}".format(self._video_playing.title))
        self._video_playing = None
        self._video_paused = False

    def play_random_video(self):
        """Plays a random video from the video library."""
        videos = self._video_library.get_all_videos()
        # 1. Part 4 FLAG_VIDEO task: filter all flagged video(s) out
        videos = list(filter(lambda vid: not vid.flag, videos))
        # 2. Check if any videos available
        if len(videos) == 0:
            print("No videos available")
            return
        # 3. shuffle and play first vid
        random.shuffle(videos)
        self.play_video(videos[0].video_id)
        # print("play_random_video needs implementation")

    def pause_video(self):
        """Pauses the current video."""
        # 1. Check if a video is playing
        if self._video_playing is None:
            print("Cannot pause video: No video is currently playing")
            return
        # 2. Check if the video is already paused
        if self._video_paused:
            print("Video already paused: {0}".format(self._video_playing.title))
            return
        # 3. Pause the video
        print("Pausing video: {0}".format(self._video_playing.title))
        self._video_paused = True
        # print("pause_video needs implementation")

    def continue_video(self):
        """Resumes playing the current video."""
        # 1. Check pause-ability
        if not self._video_playing:
            print("Cannot continue video: No video is currently playing")
            return
        if not self._video_paused:
            print("Cannot continue video: Video is not paused")
            return
        # 2. Continue paused video
        print("Continuing video: {0}".format(self._video_playing.title))
        self._video_paused = False
        # print("continue_video needs implementation")

    def show_playing(self):
        """Displays video currently playing."""
        # 1. Check any video playing
        if not self._video_playing:
            print("No video is currently playing")
            return
        # 2. Show the video playing
        print("Currently playing: {0}{1}".format(self._video_playing, " - PAUSED" if self._video_paused else ""))
        # print("show_playing needs implementation")

    def create_playlist(self, playlist_name):
        """Creates a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        # 1. find if there is any playlist with same name exists
        exist_playlist = self.is_playlist_exist(playlist_name)
        if exist_playlist:
            print("Cannot create playlist: A playlist with the same name already exists")
            return
        # 2. Create playlist and append to the list of playlists
        new_playlist = Playlist(self, playlist_name)
        self._playlists.append(new_playlist)
        print("Successfully created new playlist: {0}".format(new_playlist.get_name()))
        # print("create_playlist needs implementation")

    def add_to_playlist(self, playlist_name, video_id):
        """Adds a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be added.
        """
        # 1. Check if playlist exists
        exist_playlist = self.is_playlist_exist(playlist_name)
        if not exist_playlist:
            print("Cannot add video to {0}: Playlist does not exist".format(playlist_name))
            return
        # 2. Pass the job to the playlist to add
        exist_playlist.add_video(playlist_name, video_id)
        # print("add_to_playlist needs implementation")

    def show_all_playlists(self):
        """Display all playlists."""
        if len(self._playlists) == 0:
            print("No playlists exist yet")
            return

        print("Showing all playlists:")
        for playlist in sorted(self._playlists, key=lambda pl: pl.get_name()):
            print("\t{0}".format(playlist.get_name()))

        # print("show_all_playlists needs implementation")

    def show_playlist(self, playlist_name):
        """Display all videos in a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        # 1. Check if playlist exists
        exist_playlist = self.is_playlist_exist(playlist_name)
        if not exist_playlist:
            print("Cannot show playlist {0}: Playlist does not exist".format(playlist_name))
            return
        # 2. Show videos
        print("Showing playlist: {0}".format(playlist_name))
        exist_playlist_videos = exist_playlist.get_videos()
        if len(exist_playlist_videos) == 0:
            print("\tNo videos here yet")
            return
        for vid in exist_playlist_videos.values():
            # Part 4 FLAG_VIDEO task: add flag when showing flagged videos
            print("\t{0}{1}".format(vid, " - FLAGGED (reason: {0})".format(vid.flag) if vid.flag else ""))
        # print("show_playlist needs implementation")

    def remove_from_playlist(self, playlist_name, video_id):
        """Removes a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be removed.
        """
        # 1. Check if playlist exists
        exist_playlist = self.is_playlist_exist(playlist_name)
        if not exist_playlist:
            print("Cannot remove video from {0}: Playlist does not exist".format(playlist_name))
            return
        # 2. Pass the job to the playlist
        exist_playlist.remove_video(playlist_name, video_id)
        # print("remove_from_playlist needs implementation")

    def clear_playlist(self, playlist_name):
        """Removes all videos from a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        # 1. Check if playlist exists
        exist_playlist = self.is_playlist_exist(playlist_name)
        if not exist_playlist:
            print("Cannot clear playlist {0}: Playlist does not exist".format(playlist_name))
            return
        # 2. Pass the job to the playlist
        exist_playlist.clear(playlist_name)
        # print("clears_playlist needs implementation")

    def delete_playlist(self, playlist_name):
        """Deletes a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        # 1. Check if playlist exists
        exist_playlist = self.is_playlist_exist(playlist_name)
        if not exist_playlist:
            print("Cannot delete playlist {0}: Playlist does not exist".format(playlist_name))
            return
        # 2. Delete playlist
        self._playlists = [i for i in self._playlists if i.get_name() != playlist_name]
        print("Deleted playlist: {0}".format(playlist_name))
        # print("deletes_playlist needs implementation")

    def search_videos(self, search_term):
        """Display all the videos whose titles contain the search_term.

        Args:
            search_term: The query to be used in search.
        """
        # Pass to Helper Function
        self.search_videos_general(search_term, lambda vid: search_term.lower() in vid.title.lower())
        # print("search_videos needs implementation")

    def search_videos_tag(self, video_tag):
        """Display all videos whose tags contains the provided tag.

        Args:
            video_tag: The video tag to be used in search.
        """
        # Pass to Helper Function
        self.search_videos_general(video_tag, lambda vid: video_tag.lower() in ";".join(vid.tags).lower().split(";"))
        # print("search_videos_tag needs implementation")

    def flag_video(self, video_id, flag_reason=""):
        """Mark a video as flagged.

        Args:
            video_id: The video_id to be flagged.
            flag_reason: Reason for flagging the video.
        """
        # 1. Search video by id
        video_result = self._video_library.get_video(video_id)
        if video_result is None:
            print("Cannot flag video: Video does not exist")
            return
        # 2. Check if the video is flagged
        if video_result.flag:
            print("Cannot flag video: Video is already flagged")
            return
        # 3. Stop playing-video and Flag the video
        # 3.1. check if the playing vid is the one to flag
        if self._video_playing and self._video_playing.video_id == video_id:
            self.stop_video()
        if flag_reason == "":
            flag_reason = "Not supplied"
        video_result.action_flag(flag_reason)
        print("Successfully flagged video: {0} (reason: {1})".format(video_result.title, video_result.flag))
        # print("flag_video needs implementation")

    def allow_video(self, video_id):
        """Removes a flag from a video.

        Args:
            video_id: The video_id to be allowed again.
        """
        # 1. Search video by id
        video_result = self._video_library.get_video(video_id)
        if video_result is None:
            print("Cannot remove flag from video: Video does not exist")
            return
        # 2. Check if the video is not flagged
        if not video_result.flag:
            print("Cannot remove flag from video: Video is not flagged")
            return
        # 3. Remove the flag
        video_result.remove_flag()
        print("Successfully removed flag from video: {0}".format(video_result.title))
        # print("allow_video needs implementation")

    # ===========HELPER FUNCTIONS=======================================================================================
    # Custom Helper Function for Part 2
    def is_playlist_exist(self, playlist_name):
        """
        Return if requested playlist exists
        :param playlist_name:
        :return:
        """
        return next((pl for pl in self._playlists if pl.get_name().lower() == playlist_name.lower()), None);

    # Custom Helper Function for Part 3
    def search_videos_general(self, search_keyword, search_filter):
        """
        General Helper Function for search videos
        :param search_keyword:
        :param search_filter:
        :return:
        """
        # 1. Search matching results
        search_results = sorted(
            list(filter(search_filter, self._video_library.get_all_videos())),
            key=lambda vid: vid.title)
        # 2. Part 4 FLAG_VIDEO task: Filter out flagged video
        search_results = list(filter(lambda vid: not vid.flag, search_results))
        # 2. Return if no search results
        if len(search_results) == 0:
            print("No search results for {0}".format(search_keyword))
            return
        # 3. Print results
        print("Here are the results for {0}:".format(search_keyword))
        for i, vid in enumerate(search_results, start=1):
            print("\t{0}) {1}".format(i, vid))
        # 4. Ask user to play or not
        print("Would you like to play any of the above? If yes, specify the number of the video.")
        print("If your answer is not a valid number, we will assume it's a no.")
        # 5. Check and Use regex to get valid number
        is_numeric = re.search(r'^\d+$', input())
        if not is_numeric:
            return
        user_input = int(is_numeric.group())
        if not (1 <= user_input <= len(search_results)):
            return
        # 6. Play the selected video
        self.play_video(search_results[user_input - 1].video_id)