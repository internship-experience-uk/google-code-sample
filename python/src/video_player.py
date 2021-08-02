"""A video player class."""

from .video_library import VideoLibrary
from .video_library import VideoLibrary
from .video_playlist import Playlist
from random import choice

class VideoPlayer:
    """A class used to represent a Video Player."""

    def __init__(self):
        self._video_library = VideoLibrary()
        self._playlist = Playlist()
        self._stack = []
        self._play = False
        self._pause = False
        self._flag = {}

    def number_of_videos(self):
        num_videos = len(self._video_library.get_all_videos())
        print(f"{num_videos} videos in the library")

    def number_of_videos(self):
        num_videos = len(self._video_library.get_all_videos())
        print(f"{num_videos} videos in the library")

    def _standard_form(self, video):
        """Returns video description & details in specified form"""
        title = video.title
        url = video.video_id 
        tags = video.tags

        tag_string = ""
        for i, tag in enumerate(tags):
            if i == 0:
                tag_string += tag
            else:
                tag_string += " " + tag

        return (str(title) + ' (' + str(url) + ') ' + '[' + tag_string  + ']')         

    def _get_tagstring(self, video):
        """Returns list of tags in lowercase"""
        tags = video.tags

        tag_string = ""
        for i, tag in enumerate(tags):
            if i == 0:
                tag_string += tag.lower()
            else:
                tag_string += " " + tag.lower()
        return tag_string.split()  

    def _non_flag(self):
        """Returns a list of non-flagged videos"""
        non = []
        for vid in self._video_library.get_all_videos():   
            if vid not in self._flag:
                non.append(vid)
        return non 

    def show_all_videos(self):
        """Returns a listing of all videos."""

        print("Here's a list of all available videos:")
        for video in self._video_library.get_sorted_videos():
            if video in self._flag:
                print(f"{self._standard_form(video)} - FLAGGED {self._flag[video]}")
            else:    
                print(self._standard_form(video))
            
    def play_video(self, video_id):
        """Plays the respective video.

        Args:
            video_id: The video_id to be played.
        """
        curr_vid = self._video_library.get_video(video_id)

        if curr_vid in self._flag:
            print(f"Cannot play video: Video is currently flagged {self._flag[curr_vid]}")
            return
        if not self._stack and curr_vid:
            print(f"Playing video: {curr_vid.title}")
            self._stack.append(curr_vid)
            self._play = True
            self._pause = False
        elif self._stack and curr_vid:
            print(f"Stopping video: {self._stack.pop().title}")
            print(f"Playing video: {curr_vid.title}")
            self._stack.append(curr_vid) 
            self._play = True
            self._pause = False
        elif not curr_vid:
            print("Cannot play video: Video does not exist")       

    def stop_video(self):
        """Stops the current video."""

        if not self._stack:
            print("Cannot stop video: No video is currently playing")
        else:
            print(f"Stopping video: {self._stack.pop().title}")
            self._play = False
            self._pause = False

    def play_random_video(self):
        """Plays a random video from the video library."""
        if not self._non_flag():
            print("No videos available") 
            return 

        video = choice(self._non_flag())
        if not self._stack:
            print(f"Playing video: {video.title}")
            self._stack.append(video)
            self._play = True
            self._pause = False
        else:
            print(f"Stopping video: {self._stack.pop().title}")  
            print(f"Playing video: {video.title}") 
            self._stack.append(video)    
            self._play = True
            self._pause = False

    def pause_video(self):
        """Pauses the current video."""
        if self._play and not self._pause:
            print(f"Pausing video: {self._stack[-1].title}")
            self._pause = True
        elif self._play and self._pause:
            print(f"Video already paused: {self._stack[-1].title}")    
        elif not self._play:
            print("Cannot pause video: No video is currently playing")        

    def continue_video(self):
        """Resumes playing the current video."""
        if self._play and not self._pause:
            print("Cannot continue video: Video is not paused")
        elif self._pause:
            print(f"Continuing video: {self._stack[-1].title}") 
        else:
            print("Cannot continue video: No video is currently playing")   

    def show_playing(self):
        """Displays video currently playing."""

        if self._play and not self._pause:
            print(f"Currently playing: {self._standard_form(self._stack[-1])}")
        elif self._pause:
            print(f"Currently playing: {self._standard_form(self._stack[-1])} - PAUSED")    
        else:
            print("No video is currently playing")

    def create_playlist(self, playlist_name):
        """Creates a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        self._playlist.create(playlist_name)

    def add_to_playlist(self, playlist_name, video_id):
        """Adds a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be added.
        """
        self._playlist.add(playlist_name, self._video_library.get_video(video_id), self._flag)

    def show_all_playlists(self):
        """Display all playlists."""

        self._playlist.show_all()

    def show_playlist(self, playlist_name):
        """Display all videos in a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        for list in self._playlist.lists:
            if list.lower() == playlist_name.lower():
                print(f"Showing playlist: {playlist_name}")
                if not self._playlist.lists[list]:
                    print("No videos here yet")
                    return
                else:
                    for video in self._playlist.lists[list]:
                        if video in self._flag:
                            print(f"{self._standard_form(video)} - FLAGGED {self._flag[video]}")
                        else:    
                            print(self._standard_form(video))
                    return

        print("Cannot show playlist another_playlist: Playlist does not exist")  

    def remove_from_playlist(self, playlist_name, video_id):
        """Removes a video to a playlist with a given name.

        Args:
            playlist_name: The playlist name.
            video_id: The video_id to be removed.
        """
        self._playlist.remove(playlist_name, self._video_library.get_video(video_id))

    def clear_playlist(self, playlist_name):
        """Removes all videos from a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        self._playlist.clear(playlist_name)

    def delete_playlist(self, playlist_name):
        """Deletes a playlist with a given name.

        Args:
            playlist_name: The playlist name.
        """
        self._playlist.delete(playlist_name)

    def search_videos(self, search_term):
        """Display all the videos whose titles contain the search_term.

        Args:
            search_term: The query to be used in search.
        """
        cont = []
        for vid in self._non_flag():
            if search_term.lower() in vid.title.lower():
                cont.append(vid.video_id)

        if not cont:
            print(f"No search results for {search_term}")
        else:
            cont.sort()
            print(f"Here are the results for {search_term}:")
            for i,vid_id in enumerate(cont):
                print(f"{i + 1}) {self._standard_form(self._video_library.get_video(vid_id))}")

            print("Would you like to play any of the above? If yes, specify the number of the video.") 
            print("If your answer is not a valid number, we will assume it's a no.")   
            number = input("")
            try:
                if 1 <= int(number) <= len(cont):
                    print(f"Playing video: {self._video_library.get_video(cont[int(number) - 1]).title}") 
            except:
                return 

    def search_videos_tag(self, video_tag):
        """Display all videos whose tags contains the provided tag.

        Args:
            video_tag: The video tag to be used in search.
        """
        cont = []
        for vid in self._non_flag():
            if video_tag.lower() in self._get_tagstring(vid):
                cont.append(vid.video_id)

        if not cont:
            print(f"No search results for {video_tag}")
        else:
            cont.sort()
            print(f"Here are the results for {video_tag}:")
            for i,vid_id in enumerate(cont):
                print(f"{i + 1}) {self._standard_form(self._video_library.get_video(vid_id))}")

            print("Would you like to play any of the above? If yes, specify the number of the video.") 
            print("If your answer is not a valid number, we will assume it's a no.")   
            number = input("")
            try:
                if 1 <= int(number) <= len(cont):
                    print(f"Playing video: {self._video_library.get_video(cont[int(number) - 1]).title}") 
            except:
                return        

    def flag_video(self, video_id, flag_reason=""):
        """Mark a video as flagged.

        Args:
            video_id: The video_id to be flagged.
            flag_reason: Reason for flagging the video.
        """
        if not self._video_library.get_video(video_id):
            print("Cannot flag video: Video does not exist")
            return 
        elif (self._pause or self._play) and self._stack[-1] == self._video_library.get_video(video_id):
            print(f"Stopping video: {self._video_library.get_video(video_id).title}") 
            print(f"Successfully flagged video: {self._video_library.get_video(video_id).title} (reason: {flag_reason})")
            self._flag[self._video_library.get_video(video_id)] = "(reason: " + flag_reason  + ")" 
            self._stack.pop()
            self._play = False
            self._pause = False
            return
        elif (self._pause or self._play) and self._stack[-1] == self._video_library.get_video(video_id):
            print(f"Stopping video: {self._video_library.get_video(video_id).title}") 
            print(f"Successfully flagged video: {self._video_library.get_video(video_id).title} (reason: Not supplied)")
            self._flag[self._video_library.get_video(video_id)] = "(reason: Not supplied)" 
            self._stack.pop()
            self._play = False
            self._pause = False
            return    
        elif not self._flag or not self._video_library.get_video(video_id) in self._flag:
            if not flag_reason:
                print(f"Successfully flagged video: {self._video_library.get_video(video_id).title} (reason: Not supplied)") 
                self._flag[self._video_library.get_video(video_id)] = "(reason: Not supplied)"
            else:
                print(f"Successfully flagged video: {self._video_library.get_video(video_id).title} (reason: {flag_reason})")
                self._flag[self._video_library.get_video(video_id)] = "(reason: " + flag_reason  + ")"  
        else: 
            print("Cannot flag video: Video is already flagged")

    def allow_video(self, video_id):
        """Removes a flag from a video.

        Args:
            video_id: The video_id to be allowed again.
        """
        if not self._video_library.get_video(video_id):
            print("Cannot remove flag from video: Video does not exist")
            return 
        elif self._video_library.get_video(video_id) in self._flag:
            print(f"Successfully removed flag from video: {self._video_library.get_video(video_id).title}") 
            del self._flag[self._video_library.get_video(video_id)]
        else:
            print("Cannot remove flag from video: Video is not flagged") 
