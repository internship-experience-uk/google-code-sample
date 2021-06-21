from src.video_player import VideoPlayer


def test_create_playlist(capfd):
    player = VideoPlayer()
    player.create_playlist("my_PLAYlist")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 1
    assert "Successfully created new playlist: my_PLAYlist" in lines[0]


def test_create_existing_playlist(capfd):
    player = VideoPlayer()
    player.create_playlist("my_cool_playlist")
    player.create_playlist("my_COOL_PLAYLIST")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 2
    assert "Successfully created new playlist: my_cool_playlist" in lines[0]
    assert ("Cannot create playlist: A playlist with the same name already "
            "exists") in lines[1]


def test_add_to_playlist(capfd):
    player = VideoPlayer()
    player.create_playlist("my_COOL_playlist")
    player.add_to_playlist("my_cool_PLAYLIST", "amazing_cats_video_id")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 2
    assert "Successfully created new playlist: my_COOL_playlist" in lines[0]
    assert "Added video to my_cool_PLAYLIST: Amazing Cats" in lines[1]


def test_add_to_playlist_already_added(capfd):
    player = VideoPlayer()
    player.create_playlist("my_cool_playlist")
    player.add_to_playlist("my_cool_playlist", "amazing_cats_video_id")
    player.add_to_playlist("my_cool_playlist", "amazing_cats_video_id")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 3
    assert "Successfully created new playlist: my_cool_playlist" in lines[0]
    assert "Added video to my_cool_playlist: Amazing Cats" in lines[1]
    assert "Cannot add video to my_cool_playlist: Video already added" in lines[2]


def test_add_to_playlist_nonexistent_video(capfd):
    player = VideoPlayer()
    player.create_playlist("my_cool_playlist")
    player.add_to_playlist("my_cool_playlist", "amazing_cats_video_id")
    player.add_to_playlist("my_cool_playlist", "some_other_video_id")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 3
    assert "Successfully created new playlist: my_cool_playlist" in lines[0]
    assert "Added video to my_cool_playlist: Amazing Cats" in lines[1]
    assert "Cannot add video to my_cool_playlist: Video does not exist" in lines[2]


def test_add_to_playlist_nonexistent_playlist(capfd):
    player = VideoPlayer()
    player.add_to_playlist("another_playlist", "amazing_cats_video_id")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 1
    assert "Cannot add video to another_playlist: Playlist does not exist" in lines[0]


def test_add_to_playlist_nonexistent_playlist_nonexistent_video(capfd):
    player = VideoPlayer()
    player.add_to_playlist("another_playlist", "does_not_exist_video_id")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 1
    assert "Cannot add video to another_playlist: Playlist does not exist" in lines[0]


def test_show_all_playlists_no_playlists_exist(capfd):
    player = VideoPlayer()
    player.show_all_playlists()
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 1
    assert "No playlists exist yet" in lines[0]


def test_show_all_playlists(capfd):
    player = VideoPlayer()
    player.create_playlist("my_cool_playLIST")
    player.create_playlist("anotheR_playlist")
    player.show_all_playlists()
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 5
    assert "Showing all playlists:" in lines[2]
    assert "anotheR_playlist" in lines[3]
    assert "my_cool_playLIST" in lines[4]


def test_show_playlist(capfd):
    player = VideoPlayer()
    player.create_playlist("my_cool_playlist")
    player.show_playlist("my_cool_playlist")
    player.add_to_playlist("my_cool_playlist", "amazing_cats_video_id")
    player.show_playlist("my_COOL_playlist")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 6
    assert "Successfully created new playlist: my_cool_playlist" in lines[0]
    assert "Showing playlist: my_cool_playlist" in lines[1]
    assert "No videos here yet" in lines[2]
    assert "Added video to my_cool_playlist: Amazing Cats" in lines[3]
    assert "Showing playlist: my_COOL_playlist" in lines[4]
    assert "Amazing Cats (amazing_cats_video_id) [#cat #animal]" in lines[5]


def test_remove_from_playlist_then_re_add(capfd):
    player = VideoPlayer()
    player.create_playlist("MY_playlist")
    player.add_to_playlist("my_playlist", "amazing_cats_video_id")
    player.add_to_playlist("my_playlist", "life_at_google_video_id")
    player.remove_from_playlist("my_playlist", "amazing_cats_video_id")
    player.add_to_playlist("my_playlist", "amazing_cats_video_id")
    player.show_playlist("my_playLIST")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 8
    assert "Showing playlist: my_playLIST" in lines[5]
    assert "Life at Google (life_at_google_video_id) [#google #career]" in lines[6]
    assert "Amazing Cats (amazing_cats_video_id) [#cat #animal]" in lines[7]


def test_show_playlist_nonexistent_playlist(capfd):
    player = VideoPlayer()
    player.show_playlist("another_playlist")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 1
    assert "Cannot show playlist another_playlist: Playlist does not exist" in lines[0]


def test_remove_from_playlist(capfd):
    player = VideoPlayer()
    player.create_playlist("my_cool_playlist")
    player.add_to_playlist("my_cool_playlist", "amazing_cats_video_id")
    player.remove_from_playlist("my_COOL_playlist", "amazing_cats_video_id")
    player.remove_from_playlist("my_cool_playlist", "amazing_cats_video_id")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 4
    assert "Successfully created new playlist: my_cool_playlist" in lines[0]
    assert "Added video to my_cool_playlist: Amazing Cats" in lines[1]
    assert "Removed video from my_COOL_playlist: Amazing Cats" in lines[2]
    assert "Cannot remove video from my_cool_playlist: Video is not in playlist" in lines[3]


def test_remove_from_playlist_video_is_not_in_playlist(capfd):
    player = VideoPlayer()
    player.create_playlist("my_cool_playlist")
    player.remove_from_playlist("my_cool_playlist", "amazing_cats_video_id")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 2
    assert "Successfully created new playlist: my_cool_playlist" in lines[0]
    assert "Cannot remove video from my_cool_playlist: Video is not in playlist" in lines[1]


def test_remove_from_playlist_nonexistent_video(capfd):
    player = VideoPlayer()
    player.create_playlist("my_cool_playlist")
    player.add_to_playlist("my_cool_playlist", "amazing_cats_video_id")
    player.remove_from_playlist("my_cool_playlist", "some_other_video_id")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 3
    assert "Successfully created new playlist: my_cool_playlist" in lines[0]
    assert "Added video to my_cool_playlist: Amazing Cats" in lines[1]
    assert "Cannot remove video from my_cool_playlist: Video does not exist" in lines[2]


def test_remove_from_playlist_nonexistent_playlist(capfd):
    player = VideoPlayer()
    player.remove_from_playlist("my_cool_playlist", "amazing_cats_video_id")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 1
    assert "Cannot remove video from my_cool_playlist: Playlist does not exist" in lines[0]


def test_clear_playlist(capfd):
    player = VideoPlayer()
    player.create_playlist("my_cool_playlist")
    player.add_to_playlist("my_cool_playlist", "amazing_cats_video_id")
    player.show_playlist("my_cool_playlist")
    player.clear_playlist("my_COOL_playlist")
    player.show_playlist("my_cool_playlist")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 7
    assert "Successfully created new playlist: my_cool_playlist" in lines[0]
    assert "Added video to my_cool_playlist: Amazing Cats" in lines[1]
    assert "Showing playlist: my_cool_playlist" in lines[2]
    assert "Amazing Cats (amazing_cats_video_id) [#cat #animal]" in lines[3]
    assert "Successfully removed all videos from my_COOL_playlist" in lines[4]
    assert "Showing playlist: my_cool_playlist" in lines[5]
    assert "No videos here yet" in lines[6]


def test_clear_playlist_nonexistent(capfd):
    player = VideoPlayer()
    player.clear_playlist("my_cool_playlist")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 1
    assert "Cannot clear playlist my_cool_playlist: Playlist does not exist" in lines[0]


def test_delete_playlist(capfd):
    player = VideoPlayer()
    player.create_playlist("my_cool_playlist")
    player.delete_playlist("my_cool_playlist")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 2
    assert "Successfully created new playlist: my_cool_playlist" in lines[0]
    assert "Deleted playlist: my_cool_playlist" in lines[1]


def test_delete_playlist_nonexistent(capfd):
    player = VideoPlayer()
    player.delete_playlist("my_cool_playlist")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 1
    assert "Cannot delete playlist my_cool_playlist: Playlist does not exist" in lines[0]
