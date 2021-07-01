"""A video player class."""
from enum import Enum

from .video_playlist import Playlist
from .video_library import VideoLibrary
import random


class PlayingStatus(Enum):
    PLAYING = 1
    PAUSED = 2
    STOPPED = 3


class PlayStatusException(Exception):
    """A class used to represent a wrong play status exception."""
    pass


class VideoPlayer:
    """A class used to represent a Video Player."""

    def __init__(self):
        self._video_library = VideoLibrary()
        self._playing_status = False
        self._playing_video = ''
        self._list_playlist = []

    def number_of_videos(self):
        num_videos = len(self._video_library.get_all_videos())
        print(f"{num_videos} videos in the library")

    def show_all_videos(self):
        """Returns all videos."""
        videos = self._video_library.get_all_videos()
        videos = sorted(videos, key=lambda x: x.title)

        print("Here's a list of all available videos:")
        for video in videos:
            if video.flagged:
                print(str(video) + f" - FLAGGED (reason: {video.flagged_reason})")
            else:
                print(str(video))

    def play_video(self, video_id):
        """Plays the respective video.

        Args:
            video_id: The video_id to be played.
        """
        videos = self._video_library.get_all_videos()
        for video in videos:
            if video.video_id == video_id:
                if not video.flagged:
                    if not self._playing_status or self._playing_status == PlayingStatus.STOPPED:
                        print(f"Playing video: {video.title}")
                        self._playing_video = video
                        self._playing_status = PlayingStatus.PLAYING
                    elif self._playing_status == PlayingStatus.PLAYING or self._playing_status == PlayingStatus.PAUSED:
                        print(f"Stopping video: {self._playing_video.title}")
                        self._playing_video = video
                        print(f"Playing video: {self._playing_video.title}")
                        self._playing_status = PlayingStatus.PLAYING
                else:
                    print(f"Cannot play video: Video is currently flagged (reason: {video.flagged_reason})")
                break

        else:
            print("Cannot play video: Video does not exist")

    def stop_video(self):
        """Stops the current video."""
        if not self._playing_status or self._playing_status == PlayingStatus.STOPPED:
            print("Cannot stop video: No video is currently playing")
        elif self._playing_status == PlayingStatus.PLAYING or self._playing_status == PlayingStatus.PAUSED:
            print(f"Stopping video: {self._playing_video.title}")
            self._playing_status = PlayingStatus.STOPPED

    def play_random_video(self):
        """Plays a random video from the video library."""
        videos = self._video_library.get_all_videos()
        available_videos = []
        for video in videos:
            if not video.flagged:
                available_videos.append(video)
        if len(available_videos) == 0:
            print("No videos available")
        else:
            rand = random.randint(0, len(available_videos) - 1)
            if self._playing_status == PlayingStatus.PLAYING or self._playing_status == PlayingStatus.PAUSED:
                self.stop_video()
                self.play_video(available_videos[rand].video_id)
            else:
                self.play_video(available_videos[rand].video_id)

    def pause_video(self):
        """Pauses the current video."""
        if not self._playing_status or self._playing_status == PlayingStatus.STOPPED:
            print("Cannot pause video: No video is currently playing")
        elif self._playing_status == PlayingStatus.PLAYING:
            print(f"Pausing video: {self._playing_video.title}")
            self._playing_status = PlayingStatus.PAUSED
        elif self._playing_status == PlayingStatus.PAUSED:
            print(f"Video already paused: {self._playing_video.title}")

    def continue_video(self):
        """Resumes playing the current video."""
        if not self._playing_status or self._playing_status == PlayingStatus.STOPPED:
            print("Cannot continue video: No video is currently playing")
        elif self._playing_status == PlayingStatus.PLAYING:
            print("Cannot continue video: Video is not paused")
        elif self._playing_status == PlayingStatus.PAUSED:
            print(f"Continuing video: {self._playing_video.title}")
            self._playing_status = PlayingStatus.PLAYING

    def show_playing(self):
        """Displays video currently playing."""
        if not self._playing_status or self._playing_status == PlayingStatus.STOPPED:
            print("No video is currently playing")
        elif self._playing_status == PlayingStatus.PLAYING:
            print(f"Currently playing: {str(self._playing_video)}")
        elif self._playing_status == PlayingStatus.PAUSED:
            print(f"Currently playing: {str(self._playing_video)} - PAUSED")

    def create_playlist(self, playlist_name):
        """Creates a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        if len(self._list_playlist) == 0:
            self._list_playlist.append(Playlist(playlist_name=playlist_name))
            print(f"Successfully created new playlist: {playlist_name}")
        else:
            for playlist in self._list_playlist:
                if playlist_name.upper() == playlist.name.upper():
                    print("Cannot create playlist: A playlist with the same name already exists")
                    break
            else:
                self._list_playlist.append(Playlist(playlist_name=playlist_name))
                print(f"Successfully created new playlist: {playlist_name}")

    def add_to_playlist(self, playlist_name, video_id):
        """Adds a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be added.
        """

        video = self._video_library.get_video(video_id)
        for playlist in self._list_playlist:
            if playlist.name.upper() == playlist_name.upper():
                if video is None:
                    print(f"Cannot add video to {playlist_name}: Video does not exist")
                elif playlist.find_video(video):
                    print(f"Cannot add video to {playlist_name}: Video already added")
                elif video.flagged:
                    print(f"Cannot add video to {playlist_name}: "
                          f"Video is currently flagged (reason: {video.flagged_reason})")
                else:
                    playlist.add_video(video)
                    print(f"Added video to {playlist_name}: {video.title}")
            break
        else:
            print(f"Cannot add video to {playlist_name}: Playlist does not exist")

    def show_all_playlists(self):
        """Display all playlists."""

        if len(self._list_playlist) == 0:
            print("No playlists exist yet")
        else:
            print("Showing all playlists:")
            self._list_playlist = sorted(self._list_playlist, key=lambda x: x.name)
            for playlist in self._list_playlist:
                print(playlist.name)

    def show_playlist(self, playlist_name):
        """Display all videos in a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """

        for playlist in self._list_playlist:
            if playlist_name.upper() == playlist.name.upper():
                print(f"Showing playlist: {playlist_name}")
                if len(playlist.content) == 0:
                    print("No videos here yet")
                else:
                    for video in playlist.content:
                        if video.flagged:
                            print(str(video)+f" - FLAGGED (reason: {video.flagged_reason})")
                        else:
                            print(str(video))
                break
        else:
            self._list_playlist.append(Playlist(playlist_name=playlist_name))
            print(f"Cannot show playlist {playlist_name}: Playlist does not exist")

    def remove_from_playlist(self, playlist_name, video_id):
        """Removes a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be removed.
        """
        video = self._video_library.get_video(video_id)
        for playlist in self._list_playlist:
            if playlist.name.upper() == playlist_name.upper():
                if video is None:
                    print(f"Cannot remove video from {playlist_name}: Video does not exist")
                elif not playlist.find_video(video):
                    print(f"Cannot remove video from {playlist_name}: Video is not in playlist")
                else:
                    print(f"Removed video from {playlist_name}: {video.title}")
                    playlist.remove_video(video)
                break
        else:
            print(f"Cannot remove video from {playlist_name}: Playlist does not exist")

    def clear_playlist(self, playlist_name):
        """Removes all videos from a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        for playlist in self._list_playlist:
            if playlist.name.upper() == playlist_name.upper():
                playlist.clear()
                print(f"Successfully removed all videos from {playlist_name}")
                break
        else:
            print(f"Cannot clear playlist {playlist_name}: Playlist does not exist")

    def delete_playlist(self, playlist_name):
        """Deletes a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        for playlist in self._list_playlist:
            if playlist.name.upper() == playlist_name.upper():
                self._list_playlist.remove(playlist)
                print(f"Deleted playlist: {playlist_name}")
                break
        else:
            print(f"Cannot delete playlist {playlist_name}: Playlist does not exist")

    def search_videos(self, search_term):
        """Display all the videos whose titles contain the search_term.

        Args:
            search_term: The query to be used in search.
        """
        videos = self._video_library.get_all_videos()
        results = []
        for video in videos:
            words = video.title.split(' ')
            for word in words:
                if search_term.upper() in word.upper() and not video.flagged:
                    results.append(video)
        if len(results) > 0:
            print(f"Here are the results for {search_term}:")
            for i in range(1, len(results) + 1, 1):
                print(f"{i}) {str(results[i - 1])}")
            print("Would you like to play any of the above? "
                  "If yes, specify the number of the video. ")
            print("If your answer is not a valid number, we will assume it's a no.")
            selection = input()
            try:
                selection = int(selection)
            except ValueError:
                return
            if int(selection) <= len(results):
                self.play_video(results[int(selection) - 1].video_id)

        else:
            print(f"No search results for {search_term}")

    def search_videos_tag(self, video_tag):
        """Display all videos whose tags contains the provided tag.

        Args:
            video_tag: The video tag to be used in search.
        """
        videos = self._video_library.get_all_videos()
        results = []
        for video in videos:
            words = video.tags
            for word in words:
                if video_tag.upper() == word.upper() and not video.flagged:
                    results.append(video)
        if len(results) > 0:
            print(f"Here are the results for {video_tag}:")
            for i in range(1, len(results) + 1, 1):
                print(f"{i}) {str(results[i - 1])}")
            print("Would you like to play any of the above? "
                  "If yes, specify the number of the video. ")
            print("If your answer is not a valid number, we will assume it's a no.")
            selection = input()
            try:
                selection = int(selection)
            except ValueError:
                return
            if int(selection) <= len(results):
                self.play_video(results[int(selection) - 1].video_id)

        else:
            print(f"No search results for {video_tag}")

    def flag_video(self, video_id, flag_reason=""):
        """Mark a video as flagged.

        Args:
            video_id: The video_id to be flagged.
            flag_reason: Reason for flagging the video.
        """
        video = self._video_library.get_video(video_id)
        if video is None:
            print("Cannot flag video: Video does not exist")
        elif not video.flagged:
            if self._playing_video == video:
                self.stop_video()
            video.flagged = True
            video.flagged_reason = flag_reason if len(flag_reason) > 0 else "Not supplied"
            print(f"Successfully flagged video: {video.title} (reason: {video.flagged_reason})")
        else:
            print("Cannot flag video: Video is already flagged")

    def allow_video(self, video_id):
        """Removes a flag from a video.

        Args:
            video_id: The video_id to be allowed again.
        """
        video = self._video_library.get_video(video_id)
        if video is None:
            print("Cannot remove flag from video: Video does not exist")
        elif video.flagged:
            video.flagged = False
            print(f"Successfully removed flag from video: {video.title}")
        else:
            print("Cannot remove flag from video: Video is not flagged")
