from unittest import mock

from src.video_player import VideoPlayer


def test_flag_video_with_reason(capfd):
    player = VideoPlayer()
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    out, err = capfd.readouterr()
    assert "Successfully flagged video: Amazing Cats (reason: dont_like_cats)" \
           in out


def test_flag_video_without_reason(capfd):
    player = VideoPlayer()
    player.flag_video("another_cat_video_id")
    out, err = capfd.readouterr()
    assert "Successfully flagged video: Another Cat Video " \
           "(reason: Not supplied)" in out


def test_flag_video_already_flagged(capfd):
    player = VideoPlayer()
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    out, err = capfd.readouterr()
    assert "Successfully flagged video: Amazing Cats (reason: dont_like_cats)" in out
    assert "Cannot flag video: Video is already flagged" in out


def test_flag_video_nonexistent(capfd):
    player = VideoPlayer()
    player.flag_video("video_does_not_exist", "flag_video_reason")
    out, err = capfd.readouterr()
    assert "Cannot flag video: Video does not exist" in out


def test_flag_video_can_no_longer_play(capfd):
    player = VideoPlayer()
    player.flag_video("amazing_cats_video_id")
    player.play_video("amazing_cats_video_id")
    out, err = capfd.readouterr()
    assert "Successfully flagged video: Amazing Cats " \
           "(reason: Not supplied)" in out
    assert "Cannot play video: Video is currently flagged " \
           "(reason: Not supplied)" in out


def test_flag_video_add_to_playlist(capfd):
    player = VideoPlayer()
    player.flag_video("amazing_cats_video_id")
    player.create_playlist("my_playlist")
    player.add_to_playlist("my_playlist", "amazing_cats_video_id")
    out, err = capfd.readouterr()
    assert ("Successfully flagged video: Amazing Cats "
            "(reason: Not supplied)") in out
    assert "Successfully created new playlist: my_playlist" in out
    assert ("Cannot add video to playlist my_playlist: Video is currently "
            "flagged (reason: Not supplied)") in out


def test_flag_video_show_playlist(capfd):
    player = VideoPlayer()
    player.create_playlist("my_playlist")
    player.add_to_playlist("my_playlist", "amazing_cats_video_id")
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    player.show_playlist("my_playlist")
    out, err = capfd.readouterr()
    assert "Successfully created new playlist: my_playlist" in out
    assert "Added video to my_playlist: Amazing Cats" in out
    assert "Successfully flagged video: Amazing Cats " \
           "(reason: dont_like_cats)" in out
    assert "Showing playlist: my_playlist" in out
    assert ("Amazing Cats (amazing_cats_video_id) [#cat #animal] - FLAGGED "
            "(reason: dont_like_cats)") in out


def test_flag_video_show_all_videos(capfd):
    player = VideoPlayer()
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    player.show_all_videos()
    out, err = capfd.readouterr()
    assert "Successfully flagged video: Amazing Cats " \
           "(reason: dont_like_cats)" in out
    assert "Here's a list of all available videos:" in out
    assert ("Amazing Cats (amazing_cats_video_id) [#cat #animal] - FLAGGED "
            "(reason: dont_like_cats)") in out
    assert "Another Cat Video (another_cat_video_id) [#cat #animal]" in out
    assert "Funny Dogs (funny_dogs_video_id) [#dog #animal]" in out
    assert "Life at Google (life_at_google_video_id) [#google #career]" in out
    assert "Video about nothing (nothing_video_id) []" in out


@mock.patch('builtins.input', lambda *args: 'No')
def test_flag_video_search_videos(capfd):
    player = VideoPlayer()
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    player.search_videos("cat")
    out, err = capfd.readouterr()
    assert "Successfully flagged video: Amazing Cats " \
           "(reason: dont_like_cats)" in out
    assert "Here are the results for cat:" in out
    assert "1) Another Cat Video (another_cat_video_id)" in out
    assert ("Would you like to play any of the above? If yes, "
            "specify the number of the video. If your answer is not a valid "
            "number, we will assume it's a no") in out


def test_flag_video_stop_video_playing(capfd):
    player = VideoPlayer()
    player.play_video("amazing_cats_video_id")
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    player.show_playing()
    out, err = capfd.readouterr()
    assert "Playing video: Amazing Cats" in out
    assert "Successfully flagged video: Amazing Cats " \
           "(reason: dont_like_cats)" in out
    assert "No video is currently playing" in out


def test_allow_video(capfd):
    player = VideoPlayer()
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    player.allow_video("amazing_cats_video_id")
    out, err = capfd.readouterr()
    assert "Successfully flagged video: Amazing Cats " \
           "(reason: dont_like_cats)" in out
    assert "Successfully removed flag from video: Amazing Cats" in out


def test_allow_video_not_flagged(capfd):
    player = VideoPlayer()
    player.allow_video("amazing_cats_video_id")
    player.show_playing()
    out, err = capfd.readouterr()
    assert "Cannot remove flag from video: Video is not flagged" in out


def test_allow_video_show_playlist(capfd):
    player = VideoPlayer()
    player.create_playlist("my_playlist")
    player.add_to_playlist("my_playlist", "amazing_cats_video_id")
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    player.show_playlist("my_playlist")
    player.allow_video("amazing_cats_video_id")
    player.show_playlist("my_playlist")
    out, err = capfd.readouterr()
    assert "Successfully created new playlist: my_playlist" in out
    assert "Added video to my_playlist: Amazing Cats" in out
    assert ("Successfully flagged video: Amazing Cats "
            "(reason: dont_like_cats)") in out
    assert "Showing playlist: my_playlist" in out
    assert ("Amazing Cats (amazing_cats_video_id) [#cat #animal] - FLAGGED "
            "(reason: dont_like_cats)") in out
    assert "Successfully removed flag from video: Amazing Cats" in out
    assert "Amazing Cats (amazing_cats_video_id) [#cat #animal]" in out
