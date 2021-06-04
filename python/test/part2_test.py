from src import video_player


def test_create_playlist(capfd):
    player = video_player.VideoPlayer()
    player.create_playlist("my_playlist")
    out, err = capfd.readouterr()
    assert "Successfully created new playlist: my_playlist" in out


def test_create_existing_playlist(capfd):
    player = video_player.VideoPlayer()
    player.create_playlist("my_playlist")
    player.create_playlist("my_playlist")
    out, err = capfd.readouterr()
    assert "Successfully created new playlist: my_playlist" in out
    assert ("Cannot create playlist: A playlist with the same name already "
            "exists") in out


def test_add_to_playlist(capfd):
    player = video_player.VideoPlayer()
    player.create_playlist("my_playlist")
    player.add_to_playlist("my_playlist", "amazing_cats_video_id")
    out, err = capfd.readouterr()
    assert "Successfully created new playlist: my_playlist" in out
    assert "Added video to my_playlist: Amazing Cats" in out


def test_add_to_playlist_already_added(capfd):
    player = video_player.VideoPlayer()
    player.create_playlist("my_playlist")
    player.add_to_playlist("my_playlist", "amazing_cats_video_id")
    player.add_to_playlist("my_playlist", "amazing_cats_video_id")
    out, err = capfd.readouterr()
    assert "Successfully created new playlist: my_playlist" in out
    assert "Added video to my_playlist: Amazing Cats" in out
    assert "Cannot add video to my_playlist. Video already added" in out


def test_add_to_playlist_nonexistent_video(capfd):
    player = video_player.VideoPlayer()
    player.create_playlist("my_playlist")
    player.add_to_playlist("my_playlist", "amazing_cats_video_id")
    player.add_to_playlist("my_playlist", "some_other_video_id")
    out, err = capfd.readouterr()
    assert "Successfully created new playlist: my_playlist" in out
    assert "Cannot add video to my_playlist: Video does not exist" in out


def test_add_to_playlist_nonexistent(capfd):
    player = video_player.VideoPlayer()
    player.add_to_playlist("another_playlist", "amazing_cats_video_id")
    out, err = capfd.readouterr()
    assert "Cannot add video to another_playlist. Playlist does not exist" in out


def test_show_all_playlist(capfd):
    player = video_player.VideoPlayer()
    player.show_all_playlists()
    player.create_playlist("my_playlist")
    player.show_all_playlists()
    out, err = capfd.readouterr()
    assert "No playlists exist yet" in out
    assert "Showing all playlists:" in out
    assert "my_playlist" in out


def test_show_playlist(capfd):
    player = video_player.VideoPlayer()
    player.create_playlist("my_playlist")
    player.show_playlist("my_playlist")
    player.add_to_playlist("my_playlist", "amazing_cats_video_id")
    player.show_playlist("my_playlist")
    out, err = capfd.readouterr()
    assert "Successfully created new playlist: my_playlist" in out
    assert "Showing Playlist: my_playlist" in out
    assert "No videos here yet" in out
    assert "Added video to my_playlist: Amazing Cats" in out
    assert "Showing Playlist: my_playlist" in out
    assert "Amazing Cats (amazing_cats_video_id) [#cat #animal]" in out


def test_remove_from_playlist(capfd):
    player = video_player.VideoPlayer()
    player.create_playlist("my_playlist")
    player.add_to_playlist("my_playlist", "amazing_cats_video_id")
    player.remove_from_playlist("my_playlist", "amazing_cats_video_id")
    player.remove_from_playlist("my_playlist", "amazing_cats_video_id")
    out, err = capfd.readouterr()
    assert "Successfully created new playlist: my_playlist" in out
    assert "Added video to my_playlist: Amazing Cats" in out
    assert "Removed video from my_playlist: Amazing Cats" in out
    assert "Cannot remove video from my_playlist: Video is not in playlist" in out


def test_remove_from_playlist_nonexistent_video(capfd):
    player = video_player.VideoPlayer()
    player.create_playlist("my_playlist")
    player.add_to_playlist("my_playlist", "amazing_cats_video_id")
    player.remove_from_playlist("my_playlist", "some_other_video_id")
    out, err = capfd.readouterr()
    assert "Successfully created new playlist: my_playlist" in out
    assert "Added video to my_playlist: Amazing Cats" in out
    assert "Cannot remove video from my_playlist: Video does not exist" in out


def test_clear_playlist(capfd):
    player = video_player.VideoPlayer()
    player.create_playlist("my_playlist")
    player.add_to_playlist("my_playlist", "amazing_cats_video_id")
    player.show_playlist("my_playlist")
    player.clear_playlist("my_playlist")
    player.show_playlist("my_playlist")
    out, err = capfd.readouterr()
    assert "Successfully created new playlist: my_playlist" in out
    assert "Added video to my_playlist: Amazing Cats" in out
    assert "Showing Playlist: my_playlist" in out
    assert "Amazing Cats (amazing_cats_video_id) [#cat #animal]" in out
    assert "Successfully removed all videos from my_playlist" in out
    assert "Showing Playlist: my_playlist" in out
    assert "No videos here yet" in out


def test_clear_playlist_nonexistent(capfd):
    player = video_player.VideoPlayer()
    player.clear_playlist("my_playlist")
    out, err = capfd.readouterr()
    assert "Cannot clear playlist another_playlist: Playlist does not exist" in out


def test_delete_playlist(capfd):
    player = video_player.VideoPlayer()
    player.create_playlist("my_playlist")
    player.delete_playlist("my_playlist")
    out, err = capfd.readouterr()
    assert "Successfully created new playlist: my_playlist" in out
    assert "Deleted playlist: my_playlist" in out


def test_delete_playlist_nonexistent(capfd):
    player = video_player.VideoPlayer()
    player.delete_playlist("my_playlist")
    out, err = capfd.readouterr()
    assert "Cannot delete playlist my_playlist: Playlist does not exist" in out
