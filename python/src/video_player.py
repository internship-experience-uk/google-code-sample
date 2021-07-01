"""A video player class."""

from .video_library import VideoLibrary

import random
from collections import defaultdict


class VideoPlayer:
    """A class used to represent a Video Player."""

    def __init__(self):
        self._video_library = VideoLibrary()
        self.currently_playing = None
        self.paused_video = None

        self.playlists = defaultdict(list)
        self.playlist_names = defaultdict()

    def number_of_videos(self):
        num_videos = len(self._video_library.get_all_videos())
        print(f"{num_videos} videos in the library")

    def show_all_videos(self):
        """Returns all videos."""

        all_videos = self._video_library.get_all_videos()
        titles = [video.title for video in all_videos]
        ids = [video.video_id for video in all_videos]
        tags = [video.tags for video in all_videos]
        flag_msgs = [video.flag_msg for video in all_videos]

        sorted_videos = ([(title, url, (" ".join(tags)), flag_msg) for title, url, tags, flag_msg in sorted(zip(
            titles,
            ids,
            tags,
            flag_msgs
        ))])

        print("Here's a list of all available videos:")
        for video in sorted_videos:
            if video[3] == "":
                print(f'  {video[0]} ({video[1]}) [{video[2]}]')
            else:
                print(f'  {video[0]} ({video[1]}) [{video[2]}] - FLAGGED (reason: {video[3]})')

    def play_video(self, video_id):
        """Plays the respective video.

        Args:
            video_id: The video_id to be played.
        """

        video_to_play = self._video_library.get_video(video_id)

        if video_to_play == None:
            print("Cannot play video: Video does not exist")
        else:
            if video_to_play.flag_msg == "":
                if self.currently_playing == None:
                    print(f"Playing video: {video_to_play.title}")
                    self.paused_video = None
                    self.currently_playing = video_to_play
                else:
                    print(f"Stopping video: {self.currently_playing.title}")
                    print(f"Playing video: {video_to_play.title}")
                    self.paused_video = None
                    self.currently_playing = video_to_play
            else:
                print(f"Cannot play video: Video is currently flagged (reason: {video_to_play.flag_msg})")


    def stop_video(self):
        """Stops the current video."""

        if self.currently_playing == None:
            print("Cannot stop video: No video is currently playing")
        else:
            print(f"Stopping video: {self.currently_playing.title}")
            self.currently_playing = None

    def play_random_video(self):
        """Plays a random video from the video library."""

        all_videos = self._video_library.get_all_videos()
        all_videos = [video for video in all_videos if video.flag_msg == '']

        if len(all_videos) != 0:
            random_video_idx = random.randrange(len(all_videos))
            video_to_play = all_videos[random_video_idx]

            if self.currently_playing == None:
                print(f"Playing video: {video_to_play.title}")
                self.paused_video = None
                self.currently_playing = video_to_play
            else:
                print(f"Stopping video: {self.currently_playing.title}")
                print(f"Playing video: {video_to_play.title}")
                self.paused_video = None
                self.currently_playing = video_to_play

        else:
            print("No videos available")

    def pause_video(self):
        """Pauses the current video."""

        if self.currently_playing == None:
            print("Cannot pause video: No video is currently playing")

        else:

            if self.paused_video == None:
                print(f"Pausing video: {self.currently_playing.title}")
                self.paused_video = self.currently_playing
            else:
                print(f"Video already paused: {self.paused_video.title}")

    def continue_video(self):
        """Resumes playing the current video."""

        if self.currently_playing == None:
            print("Cannot continue video: No video is currently playing")

        else:

            if self.paused_video == None:
                print("Cannot continue video: Video is not paused")
            else:
                print(f"Continuing video: {self.paused_video.title}")
                self.paused_video = None
                
    def show_playing(self):
        """Displays video currently playing."""

        if self.currently_playing == None:
            print("No video is currently playing")

        else:
            if self.paused_video == None:
                print(f"Currently playing: {self.currently_playing.title} ({self.currently_playing.video_id}) [{' '.join(self.currently_playing.tags)}]") 
            else:
                print(f"Currently playing: {self.currently_playing.title} ({self.currently_playing.video_id}) [{' '.join(self.currently_playing.tags)}] - PAUSED")


    def create_playlist(self, playlist_name):
        """Creates a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """

        if playlist_name.lower() in self.playlist_names.keys():
            print("Cannot create playlist: A playlist with the same name already exists")

        else:
            self.playlists[playlist_name] = []
            self.playlist_names[playlist_name.lower()] = playlist_name
            print(f"Successfully created new playlist: {playlist_name}")

    def add_to_playlist(self, playlist_name, video_id):
        """Adds a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be added.
        """

        video_to_play = self._video_library.get_video(video_id)

        if playlist_name.lower() not in self.playlist_names.keys():
            print(f"Cannot add video to {playlist_name}: Playlist does not exist")
        elif video_to_play == None:
            print(f"Cannot add video to {playlist_name}: Video does not exist")
        else:
            if video_to_play.flag_msg == "":
                if video_to_play in self.playlists[self.playlist_names[playlist_name.lower()]]:
                    print(f"Cannot add video to {playlist_name}: Video already added")
                else:
                    self.playlists[self.playlist_names[playlist_name.lower()]].append(video_to_play)
                    print(f"Added video to {playlist_name}: {video_to_play.title}")
            else:
                print(f"Cannot add video to {playlist_name}: Video is currently flagged (reason: {video_to_play.flag_msg})")

    def show_all_playlists(self):
        """Display all playlists."""

        if len(self.playlists.keys()) == 0:
            print("No playlists exist yet")
        else:
            print("Showing all playlists:")
            for pl in sorted(self.playlists.keys()):
                print(f" {pl}")

    def show_playlist(self, playlist_name):
        """Display all videos in a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """

        if playlist_name.lower() not in self.playlist_names.keys():
            print(f"Cannot show playlist {playlist_name}: Playlist does not exist")
        else:
            print(f"Showing playlist: {playlist_name}")
            if len(self.playlists[self.playlist_names[playlist_name.lower()]]) == 0:
                print("  No videos here yet")
            else:
                for video in self.playlists[self.playlist_names[playlist_name.lower()]]:
                    if video.flag_msg == '':
                        print(f"  {video.title} ({video.video_id}) [{' '.join(video.tags)}]")
                    else:
                        print(f"  {video.title} ({video.video_id}) [{' '.join(video.tags)}] - FLAGGED (reason: {video.flag_msg})")

    def remove_from_playlist(self, playlist_name, video_id):
        """Removes a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be removed.
        """

        video_to_play = self._video_library.get_video(video_id)

        if playlist_name.lower() not in self.playlist_names.keys():
            print(f"Cannot remove video from {playlist_name}: Playlist does not exist")
        elif video_to_play == None:
            print(f"Cannot remove video from {playlist_name}: Video does not exist")
        else:
            if video_to_play not in self.playlists[self.playlist_names[playlist_name.lower()]]:
                print(f"Cannot remove video from {playlist_name}: Video is not in playlist")
            else:
                self.playlists[self.playlist_names[playlist_name.lower()]].remove(video_to_play)
                print(f"Removed video from {playlist_name}: {video_to_play.title}")

    def clear_playlist(self, playlist_name):
        """Removes all videos from a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        
        if playlist_name.lower() not in self.playlist_names.keys():
            print(f"Cannot clear playlist {playlist_name}: Playlist does not exist")

        else:
            self.playlists[self.playlist_names[playlist_name.lower()]] = []
            print(f"Successfully removed all videos from {playlist_name}")

    def delete_playlist(self, playlist_name):
        """Deletes a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """

        if playlist_name.lower() not in self.playlist_names.keys():
            print(f"Cannot delete playlist {playlist_name}: Playlist does not exist")

        else:
            deleted = self.playlists.pop(self.playlist_names[playlist_name.lower()])
            deleted = self.playlist_names.pop(playlist_name.lower())
            print(f"Deleted playlist: {playlist_name}")

    def search_videos(self, search_term):
        """Display all the videos whose titles contain the search_term.

        Args:
            search_term: The query to be used in search.
        """

        all_videos = self._video_library.get_all_videos()
        all_videos = [video for video in all_videos if video.flag_msg == '']
        qualified_videos = {video.title:video for video in all_videos if search_term.lower() in video.title.lower()}
        qualified_videos = dict(sorted(qualified_videos.items()))

        if len(qualified_videos) != 0:
            print(f"Here are the results for {search_term}:")
            sno = 1
            for key, value in qualified_videos.items():
                tags = " ".join(value.tags)
                print(f"  {sno}) {key} ({value.video_id}) [{tags}]")
                sno += 1

            print("Would you like to play any of the above? If yes, specify the number of the video.")
            print("If your answer is not a valid number, we will assume it's a no.")
            
            try:
                choice = int(input())
                if (choice>0 and choice<=len(qualified_videos)):
                    video_to_play = list(qualified_videos.keys())[choice-1]
                    print(f"Playing video: {video_to_play}")
                    self.currently_playing = qualified_videos[video_to_play]
            except:
                pass

        else:
            print(f"No search results for {search_term}")

    def search_videos_tag(self, video_tag):
        """Display all videos whose tags contains the provided tag.

        Args:
            video_tag: The video tag to be used in search.
        """

        all_videos = self._video_library.get_all_videos()
        all_videos = [video for video in all_videos if video.flag_msg == '']
        qualified_videos = {video.title:video for video in all_videos if video_tag.lower() in\
            [tag.lower() for tag in video.tags]}
        qualified_videos = dict(sorted(qualified_videos.items()))

        if len(qualified_videos) != 0:
            print(f"Here are the results for {video_tag}:")
            sno = 1
            for key, value in qualified_videos.items():
                tags = " ".join(value.tags)
                print(f"  {sno}) {key} ({value.video_id}) [{tags}]")
                sno += 1

            print("Would you like to play any of the above? If yes, specify the number of the video.")
            print("If your answer is not a valid number, we will assume it's a no.")
            
            try:
                choice = int(input())
                if (choice>0 and choice<=len(qualified_videos)):
                    video_to_play = list(qualified_videos.keys())[choice-1]
                    print(f"Playing video: {video_to_play}")
                    self.currently_playing = qualified_videos[video_to_play]
            except:
                pass

        else:
            print(f"No search results for {video_tag}")

    def flag_video(self, video_id, flag_reason=""):
        """Mark a video as flagged.

        Args:
            video_id: The video_id to be flagged.
            flag_reason: Reason for flagging the video.
        """

        video_to_flag = self._video_library.get_video(video_id)

        if video_to_flag == None:
            print(f"Cannot flag video: Video does not exist")

        elif video_to_flag.flag_msg != "":
            print(f"Cannot flag video: Video is already flagged")

        else:
            if flag_reason == "":
                flag_reason = "Not supplied"
            if self.currently_playing == video_to_flag or self.paused_video == video_to_flag:
                self.stop_video()
            video_to_flag.assign_flag_msg(flag_reason)
            print(f"Successfully flagged video: {video_to_flag.title} (reason: {flag_reason})")      
        
    def allow_video(self, video_id):
        """Removes a flag from a video.

        Args:
            video_id: The video_id to be allowed again.
        """

        video_to_unflag = self._video_library.get_video(video_id)
        if video_to_unflag == None:
            print(f"Cannot remove flag from video: Video does not exist")

        elif video_to_unflag.flag_msg == "":
            print(f"Cannot remove flag from video: Video is not flagged")

        else:
            video_to_unflag.assign_flag_msg("")
            print(f"Successfully removed flag from video: {video_to_unflag.title}")