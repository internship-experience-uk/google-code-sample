from .video_playlist import VideoPlaylist

class VideoPlaylistLibraryError(Exception):
    pass

class VideoPlaylistLibrary:
    """A library containing video playlists. We want this class to behave like
    a python dictionary but with some additional functionality.
    """
    def __init__(self):
        # keep the playlists indexed from lower-case name as key to
        # Playlist object as value. This will help us with the lookup and
        # maintaining the case.
        self._playlists = {}

    def __contains__(self, playlist_name: str):
        """Overloading __contains__ allows us to use `in` like
        `if playlist in video_library`. Here, we check if the playlist name
        in lower case is part of the playlists.
        """
        return playlist_name.lower() in self._playlists

    def create(self, playlist_name: str):
        """Create a new playlist with the provided name and store it in the
        dictionary with lowercase name for easier lookup in the future."""
        if playlist_name in self:
            raise VideoPlaylistLibraryError("A playlist with the same name already exists")
        self._playlists[playlist_name.lower()] = VideoPlaylist(playlist_name)

    def __getitem__(self, playlist_name):
        """Overloading __getitem__ will allow us to use the [] operator for
        the VideoPlaylistLibrary. e.g. we can do playlist_library[playlistname]
        to retrieve a playlist from the library.

        Here, we do the lookup in lowercase, because the playlist name should
        not be case sensitive.
        """
        try:
            return self._playlists[playlist_name.lower()]
        except KeyError:
            raise VideoPlaylistLibraryError("Playlist does not exist")

    def get(self, playlist_name, default=None):
        """Returns the playlist from the library or None if it doesn't
        exist. We look up the playlist in lowercase because we don't care
        about the case."""
        return self._playlists.get(playlist_name.lower(), default)

    def get_all(self):
        return sorted(self._playlists.values(), key=str)

    def __delitem__(self, playlist_name: str):
        """This allows us to delete a playlist from the library without
        caring about the case. """
        del self._playlists[playlist_name.lower()]
