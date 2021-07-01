from .video_playlist import VideoPlaylist

class VideoPlaylistLibraryError(Exception):
    pass

class VideoPlaylistLibrary:
    def __init__(self):
        self._playlists = {}

    def __contains__(self, playlist_name: str):
        return playlist_name.lower() in self._playlists

    def create(self, playlist_name: str):
        if playlist_name in self:
            raise VideoPlaylistLibraryError("A playlist with the same name already exists")
        self._playlists[playlist_name.lower()] = VideoPlaylist(playlist_name)

    def __getitem__(self, playlist_name):
        try:
            return self._playlists[playlist_name.lower()]
        except KeyError:
            raise VideoPlaylistLibraryError("Playlist does not exist")

    def get(self, playlist_name, default=None):
        return self._playlists.get(playlist_name.lower(), default)

    def get_all(self):
        return sorted(self._playlists.values(), key=str)

    def __delitem__(self, playlist_name: str):
        del self._playlists[playlist_name.lower()]
