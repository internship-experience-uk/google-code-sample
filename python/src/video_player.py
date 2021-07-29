"""A video player class."""

import random
from .video_library import VideoLibrary
from .video_playlist import Playlist

class VideoPlayer:
    """A class used to represent a Video Player."""

    def __init__(self):
        self._video_library = VideoLibrary()
        self._playlists = {}

    def number_of_videos(self):
        num_videos = len(self._video_library.get_all_videos())
        print(f"{num_videos} videos in the library")

    def show_all_videos(self):
        """Returns all videos."""
        all_videos = self._video_library.get_all_videos()
        all_videos.sort(key = lambda v: v.title)
        print("Here's a list of all available videos:")
        for video in all_videos:
            print(f"\t{video.title} ({video.video_id}) [{' '.join(video.tags)}]", end="")
            if video.is_flagged(): print(f" - FLAGGED (reason: {video.get_flag_reason()})")
            else: print()

    def play_video(self, video_id):
        """Plays the respective video.

        Args:
            video_id: The video_id to be played.
        """
        video_toplay = self._video_library.get_video(video_id)
        video_id_playing = self._video_library.get_video_playing()
        if video_toplay == None:
            print(f"Cannot play video: Video does not exist")
        elif video_toplay.is_flagged():
            print(f"Cannot play video: Video is currently flagged (reason: {video_toplay.get_flag_reason()})")
        else:
            if video_id_playing:
                print(f"Stopping video: {self._video_library.get_video(video_id_playing).title}")
            print(f"Playing video: {video_toplay.title}")
            self._video_library.set_video_playing(video_id) # update playlist
            self._video_library.set_video_paused(False)

    def stop_video(self):
        """Stops the current video."""
        video_id_playing = self._video_library.get_video_playing()
        if video_id_playing == None:
            print("Cannot stop video: No video is currently playing")
        else:
            print(f"Stopping video: {self._video_library.get_video(video_id_playing).title}")
            self._video_library.set_video_playing(None) # update playlist
            self._video_library.set_video_paused(False)

    def play_random_video(self):
        """Plays a random video from the video library."""
        video_available = [video for video in self._video_library.get_all_videos() if not video.is_flagged()]
        number_of_videos = len(video_available)
        if number_of_videos == 0:
            print("No videos available")
        else:
            video_num_toplay = random.randint(0, number_of_videos - 1)
            video_toplay = video_available[video_num_toplay]
            self.play_video(video_toplay.video_id)

    def pause_video(self):
        """Pauses the current video."""
        video_id_playing = self._video_library.get_video_playing()
        video_playing = self._video_library.get_video(video_id_playing) # None when video_id_playing is None
        if video_id_playing == None:
            print("Cannot pause video: No video is currently playing")
        elif self._video_library.is_video_paused():
            print(f"Video already paused: {video_playing.title}")
        else:
            print(f"Pausing video: {video_playing.title}")
            self._video_library.set_video_paused(True)

    def continue_video(self):
        """Resumes playing the current video."""
        video_id_playing = self._video_library.get_video_playing()
        video_playing = self._video_library.get_video(video_id_playing) # None when video_id_playing is None
        if video_id_playing == None:
            print("Cannot continue video: No video is currently playing")
        elif self._video_library.is_video_paused() == False:
            print(f"Cannot continue video: Video is not paused")
        else:
            print(f"Continuing video: {video_playing.title}")
            self._video_library.set_video_paused(False)
        
    def show_playing(self):
        """Displays video currently playing."""
        video_id_playing = self._video_library.get_video_playing()
        video_playing = self._video_library.get_video(video_id_playing) # None when video_id_playing is None
        if video_id_playing == None:
            print("No video is currently playing")
            return
        print(f"Currently playing: {video_playing.title} ({video_playing.video_id}) [{' '.join(video_playing.tags)}]", end = '')
        if self._video_library.is_video_paused(): print(" - PAUSED")
        else: print("")

    def create_playlist(self, playlist_name):
        """Creates a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        if playlist_name.lower() in self._playlists:
            print("Cannot create playlist: A playlist with the same name already exists")
        else:
            playlist = Playlist(playlist_name)
            self._playlists[playlist.get_name().lower()] = playlist
            print(f"Successfully created new playlist: {playlist_name}")

    def add_to_playlist(self, playlist_name, video_id):
        """Adds a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be added.
        """
        video_toadd = self._video_library.get_video(video_id)
        if playlist_name.lower() not in self._playlists:
            print(f"Cannot add video to {playlist_name}: Playlist does not exist")
        elif video_toadd == None:
            print(f"Cannot add video to {playlist_name}: Video does not exist")
        elif video_toadd.is_flagged():
            print(f"Cannot add video to {playlist_name}: Video is currently flagged (reason: {video_toadd.get_flag_reason()})")
        elif video_id in self._playlists[playlist_name.lower()].get_all_videos():
            print(f"Cannot add video to {playlist_name}: Video already added")
        else:
            self._playlists[playlist_name.lower()].add_video(video_id)
            print(f"Added video to {playlist_name}: {self._video_library.get_video(video_id).title}")
        
    def show_all_playlists(self):
        """Display all playlists."""
        if not self._playlists:
            print("No playlists exist yet")
        else:
            print(f"Showing all playlists:")
            for playlist in sorted(self._playlists):
                print(self._playlists[playlist].get_name())

    def show_playlist(self, playlist_name):
        """Display all videos in a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        if playlist_name.lower() not in self._playlists:
            print(f"Cannot show playlist {playlist_name}: Playlist does not exist")
            return
        print(f"Showing playlist: {playlist_name}")
        all_videos = self._playlists[playlist_name.lower()].get_all_videos()
        if not all_videos:
            print("\tNo videos here yet")
        else:
            for video_id in all_videos:
                video = self._video_library.get_video(video_id)
                print(f"\t{video.title} ({video.video_id}) [{' '.join(video.tags)}]", end="")
                if video.is_flagged(): print(f" - FLAGGED (reason: {video.get_flag_reason()})")
                else: print()

    def remove_from_playlist(self, playlist_name, video_id):
        """Removes a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be removed.
        """
        if playlist_name.lower() not in self._playlists:
            print(f"Cannot remove video from {playlist_name}: Playlist does not exist")
        elif self._video_library.get_video(video_id) == None:
            print(f"Cannot remove video from {playlist_name}: Video does not exist")
        elif video_id not in self._playlists[playlist_name.lower()].get_all_videos():
            print(f"Cannot remove video from {playlist_name}: Video is not in playlist")
        else:
            self._playlists[playlist_name.lower()].remove_video(video_id)
            print(f"Removed video from {playlist_name}: {self._video_library.get_video(video_id).title}")
        

    def clear_playlist(self, playlist_name):
        """Removes all videos from a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        if playlist_name.lower() not in self._playlists:
            print(f"Cannot clear playlist {playlist_name}: Playlist does not exist")
        else:
            self._playlists[playlist_name.lower()].clear_videos()
            print(f"Successfully removed all videos from {playlist_name}")

    def delete_playlist(self, playlist_name):
        """Deletes a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        if playlist_name.lower() not in self._playlists:
            print(f"Cannot delete playlist {playlist_name}: Playlist does not exist")
        else:
            del self._playlists[playlist_name.lower()]
            print(f"Deleted playlist: {playlist_name}")

    def search_videos(self, search_term):
        """Display all the videos whose titles contain the search_term.

        Args:
            search_term: The query to be used in search.
        """
        videos_contain = []
        for video in self._video_library.get_all_videos():
            if search_term.lower() in video.title.lower() and not video.is_flagged():
                videos_contain.append(video)
        videos_contain.sort(key = lambda v: v.title)

        if not videos_contain:
            print(f"No search results for {search_term}")
        else:
            print(f"Here are the results for {search_term}:")
            self.choose_video_toplay(videos_contain)

    def search_videos_tag(self, video_tag):
        """Display all videos whose tags contains the provided tag.

        Args:
            video_tag: The video tag to be used in search.
        """
        videos_contain = []
        for video in self._video_library.get_all_videos():
            if video_tag.lower() in video.tags and not video.is_flagged():
                videos_contain.append(video)
        videos_contain.sort(key = lambda v: v.title)

        if not videos_contain:
            print(f"No search results for {video_tag}")
        else:
            print(f"Here are the results for {video_tag}:")
            self.choose_video_toplay(videos_contain)
            
    def choose_video_toplay(self, videos):
        for i in range(len(videos)):
            print(f"\t{i + 1}) {videos[i].title} ({videos[i].video_id}) [{' '.join(videos[i].tags)}]")
            
        print("Would you like to play any of the above? If yes, specify the number of the video.\nIf your answer is not a valid number, we will assume it's a no.")
        video_num = input()
        if video_num.isdigit() and 1 <= int(video_num) <= len(videos):
            self.play_video(videos[int(video_num) - 1].video_id)

    def flag_video(self, video_id, flag_reason=""):
        """Mark a video as flagged.

        Args:
            video_id: The video_id to be flagged.
            flag_reason: Reason for flagging the video.
        """
        video = self._video_library.get_video(video_id)
        if video == None:
            print("Cannot flag video: Video does not exist")
        elif video.is_flagged():
            print("Cannot flag video: Video is already flagged")
        else:
            video.flag(True, flag_reason)
            if video.video_id == self._video_library.get_video_playing():
                self.stop_video()
            print(f"Successfully flagged video: {video.title} (reason: {video.get_flag_reason()})")

    def allow_video(self, video_id):
        """Removes a flag from a video.

        Args:
            video_id: The video_id to be allowed again.
        """
        video = self._video_library.get_video(video_id)
        if video == None:
            print("Cannot remove flag from video: Video does not exist")
        elif not video.is_flagged():
            print("Cannot remove flag from video: Video is not flagged")
        else:
            video.unflag()
            print(f"Successfully removed flag from video: {video.title}")
