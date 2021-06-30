"""A video player class."""

from .video_library import VideoLibrary

# import outside of class so it is not imported per instance
# (unless there is caching, I don't know)
from random import randint

class VideoPlayer:
    """A class used to represent a Video Player."""
    def __init__(self):
        self._video_library = VideoLibrary()
        self._video_playing = None

    def get_number_of_videos(self):
        """
        Returns
            number of videos: int
        """
        num_videos = len(self._video_library.get_all_videos())
        return num_videos

    def number_of_videos(self):
        num_videos = self.get_number_of_videos()
        print(f"{num_videos} videos in the library")

    def show_all_videos(self):
        ## """Returns all videos."""
        """Shows all videos."""
        videos = self._video_library.get_all_videos()
        # https://stackoverflow.com/questions/3121979/how-to-sort-a-list-tuple-of-lists-tuples-by-the-element-at-a-given-index
        videos.sort(key= lambda tup: tup.title)
        print("Here's a list of all available videos:")
        for video_index in range(self.get_number_of_videos()):
            video_title = videos[video_index].title
            video_id = "("+ videos[video_index].video_id + ")"

            tags = videos[video_index].tags
            # print(tags)
            tags_string = "["
            num_tags = len(tags)
            if num_tags > 0:
                for tag_index in range(num_tags-1):
                    tags_string += tags[tag_index] + " "
                tags_string += tags[-1]
            tags_string += "]"

            print(video_title,video_id,tags_string)
        # exit()
        # print("show_all_videos needs implementation")

    def play_video(self, video_id):
        """Plays the respective video.

        Args:
            video_id: The video_id to be played.
        """

        # get the video to be played
        video_to_play = self._video_library.get_video(video_id)
        # if the video exists
        if (video_to_play):
            # to stop playing current video
            if (self._video_playing):
                print(f"Stopping video: {self._video_playing.title}")
            # set the video to be "played"
            self._video_playing = video_to_play
            print(f"Playing video: {self._video_library.get_video(video_id).title}")
        else:
            print("Cannot play video: Video does not exist")


    def stop_video(self):
        """Stops the current video."""

        if (self._video_playing):
            print(f"Stopping video: {self._video_playing.title}")
            self._video_playing = None
        else:
            print(f"Cannot stop video: No video is currently playing")


    def play_random_video(self):
        """Plays a random video from the video library."""

        # some of the code can use already existing methods
        # but as the code is little and it saves a bit of time
        # some code will be somewhat reused
        
        # stop video if currently playing
        if (self._video_playing):
            print(f"Stopping video: {self._video_playing.title}")
            self._video_playing = None

        # get a number between 0 and the number of videos
        #    number_of_videos - 1: counting starts from zero and randint is both inclusive
        random_index = randint(0,self.get_number_of_videos()-1)

        # get the video id for a random video
        random_video_id = self._video_library.get_all_videos()[random_index].video_id
        self.play_video(random_video_id)

    def pause_video(self):
        """Pauses the current video."""

        print("pause_video needs implementation")

    def continue_video(self):
        """Resumes playing the current video."""

        print("continue_video needs implementation")

    def show_playing(self):
        """Displays video currently playing."""

        print("show_playing needs implementation")

    def create_playlist(self, playlist_name):
        """Creates a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        print("create_playlist needs implementation")

    def add_to_playlist(self, playlist_name, video_id):
        """Adds a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be added.
        """
        print("add_to_playlist needs implementation")

    def show_all_playlists(self):
        """Display all playlists."""

        print("show_all_playlists needs implementation")

    def show_playlist(self, playlist_name):
        """Display all videos in a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        print("show_playlist needs implementation")

    def remove_from_playlist(self, playlist_name, video_id):
        """Removes a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be removed.
        """
        print("remove_from_playlist needs implementation")

    def clear_playlist(self, playlist_name):
        """Removes all videos from a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        print("clears_playlist needs implementation")

    def delete_playlist(self, playlist_name):
        """Deletes a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        print("deletes_playlist needs implementation")

    def search_videos(self, search_term):
        """Display all the videos whose titles contain the search_term.

        Args:
            search_term: The query to be used in search.
        """
        print("search_videos needs implementation")

    def search_videos_tag(self, video_tag):
        """Display all videos whose tags contains the provided tag.

        Args:
            video_tag: The video tag to be used in search.
        """
        print("search_videos_tag needs implementation")

    def flag_video(self, video_id, flag_reason=""):
        """Mark a video as flagged.

        Args:
            video_id: The video_id to be flagged.
            flag_reason: Reason for flagging the video.
        """
        print("flag_video needs implementation")

    def allow_video(self, video_id):
        """Removes a flag from a video.

        Args:
            video_id: The video_id to be allowed again.
        """
        print("allow_video needs implementation")
