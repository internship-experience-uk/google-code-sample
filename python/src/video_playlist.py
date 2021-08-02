"""A video playlist class."""


class Playlist:
    """A class used to represent a Playlist."""
    def __init__(self):
        self._lists = {}

    @property
    def lists(self): 
        return self._lists  

    def _checkCase(self, val):
        """Checks if there is a match between a playist and any 
        of the playlists regardless of case
        """
        for list in self._lists:
            if list.lower() == val:
                return True 
        return False           

    def create(self, playlist):
        """Creates a new playlist"""
        temp = playlist.lower()
        if not self._lists or not self._checkCase(temp):
            self._lists[playlist] = []
            print(f"Successfully created new playlist: {playlist}")

        elif self._checkCase(temp):
            print("Cannot create playlist: A playlist with the same name already exists")

    def add(self, playlist_name, video_id, flag):
        """Adds videos to a playlist"""
        for list in self._lists:
            temp = list.lower()
            temp2 = playlist_name.lower()
            if temp == temp2:
                if not (video_id):
                    print(f"Cannot add video to {playlist_name}: Video does not exist")
                    return
                elif video_id in flag: 
                    print(f"Cannot add video to {playlist_name}: Video is currently flagged {flag[video_id]}")   
                    return
                elif (video_id) in self._lists[list]:
                    print(f"Cannot add video to {playlist_name}: Video already added")
                    return
                else:
                    print(f"Added video to {playlist_name}: {(video_id).title}")
                    self._lists[list].append(video_id)
                    return

        print(f"Cannot add video to {playlist_name}: Playlist does not exist")

    def show_all(self):
        """Displays all playlists sorted lexicographically"""
        if not self._lists:
            print("No playlists exist yet")
        else:
            print("Showing all playlists:")
            temp = []
            map = {}

            # first create list of playlists in lowercase and map lower case to actual case 
            for list in self._lists:
                temp.append(list.lower())
                map[list.lower()] = list

            # sort list of playlists in lowercase
            temp.sort()
            for list in temp:
                print(map[list])     

    def remove(self, playlist_name, video_id):
        """Remove given playlist from list of playlists- case insensitive"""
        for list in self._lists:
            if list.lower() == playlist_name.lower():
                if not video_id:
                    print(f"Cannot remove video from {playlist_name}: Video does not exist")
                    return
                elif video_id in self._lists[list]:
                    print(f"Removed video from {playlist_name}: {video_id.title}")
                    self._lists[list].remove(video_id)
                    return
                else:
                    print(f"Cannot remove video from {playlist_name}: Video is not in playlist")
                    return
     
        print(f"Cannot remove video from {playlist_name}: Playlist does not exist")
        
    def clear(self, playlist):
        """Remove all videos from given playlist"""
        for list in self._lists:
            if list.lower() == playlist.lower():
                print(f"Successfully removed all videos from {playlist}")
                self._lists[list] = []
                return 

        print(f"Cannot clear playlist {playlist}: Playlist does not exist")        

    def delete(self, playlist):
        """Delete given playlist - case insensitive"""
        for list in self._lists:
            if list.lower() == playlist.lower():
                print(f"Deleted playlist: {playlist}")
                del self._lists[list]
                return 

        print(f"Cannot delete playlist {playlist}: Playlist does not exist") 