from unittest import mock

from src.video_player import VideoPlayer


def test_flag_video_with_reason(capfd):
    player = VideoPlayer()
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 1
    assert "Successfully flagged video: Amazing Cats (reason: dont_like_cats)" \
           in lines[0]


def test_flag_video_without_reason(capfd):
    player = VideoPlayer()
    player.flag_video("another_cat_video_id")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 1
    assert "Successfully flagged video: Another Cat Video " \
           "(reason: Not supplied)" in lines[0]


def test_flag_video_already_flagged(capfd):
    player = VideoPlayer()
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 2
    assert "Successfully flagged video: Amazing Cats (reason: dont_like_cats)" in \
           lines[0]
    assert "Cannot flag video: Video is already flagged" in lines[1]


def test_flag_video_nonexistent(capfd):
    player = VideoPlayer()
    player.flag_video("video_does_not_exist", "flag_video_reason")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 1
    assert "Cannot flag video: Video does not exist" in lines[0]


def test_flag_video_can_no_longer_play(capfd):
    player = VideoPlayer()
    player.flag_video("amazing_cats_video_id")
    player.play_video("amazing_cats_video_id")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 2
    assert "Successfully flagged video: Amazing Cats " \
           "(reason: Not supplied)" in lines[0]
    assert "Cannot play video: Video is currently flagged " \
           "(reason: Not supplied)" in lines[1]


def test_flag_videos_play_random(capfd):
    player = VideoPlayer()
    player.flag_video("funny_dogs_video_id")
    player.flag_video("amazing_cats_video_id")
    player.flag_video("another_cat_video_id")
    player.flag_video("life_at_google_video_id")
    player.flag_video("nothing_video_id")
    player.play_random_video()
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 6
    assert "Successfully flagged video: Funny Dogs " \
           "(reason: Not supplied)" in lines[0]
    assert "Successfully flagged video: Amazing Cats " \
           "(reason: Not supplied)" in lines[1]
    assert "Successfully flagged video: Another Cat Video " \
           "(reason: Not supplied)" in lines[2]
    assert "Successfully flagged video: Life at Google " \
           "(reason: Not supplied)" in lines[3]
    assert "Successfully flagged video: Video about nothing " \
           "(reason: Not supplied)" in lines[4]
    assert "No videos available" in lines[5]


def test_flag_video_add_to_playlist(capfd):
    player = VideoPlayer()
    player.flag_video("amazing_cats_video_id")
    player.create_playlist("my_playlist")
    player.add_to_playlist("my_playlist", "amazing_cats_video_id")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 3
    assert ("Successfully flagged video: Amazing Cats "
            "(reason: Not supplied)") in lines[0]
    assert "Successfully created new playlist: my_playlist" in lines[1]
    assert ("Cannot add video to my_playlist: Video is currently "
            "flagged (reason: Not supplied)") in lines[2]


def test_flag_video_show_playlist(capfd):
    player = VideoPlayer()
    player.create_playlist("my_playlist")
    player.add_to_playlist("my_playlist", "amazing_cats_video_id")
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    player.show_playlist("my_playlist")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 5
    assert "Successfully created new playlist: my_playlist" in lines[0]
    assert "Added video to my_playlist: Amazing Cats" in lines[1]
    assert "Successfully flagged video: Amazing Cats " \
           "(reason: dont_like_cats)" in lines[2]
    assert "Showing playlist: my_playlist" in lines[3]
    assert ("Amazing Cats (amazing_cats_video_id) [#cat #animal] - FLAGGED "
            "(reason: dont_like_cats)") in lines[4]


def test_flag_video_show_all_videos(capfd):
    player = VideoPlayer()
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    player.show_all_videos()
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 7
    assert "Successfully flagged video: Amazing Cats " \
           "(reason: dont_like_cats)" in lines[0]
    assert "Here's a list of all available videos:" in lines[1]
    assert ("Amazing Cats (amazing_cats_video_id) [#cat #animal] - FLAGGED "
            "(reason: dont_like_cats)") in lines[2]
    assert "Another Cat Video (another_cat_video_id) [#cat #animal]" in lines[3]
    assert "Funny Dogs (funny_dogs_video_id) [#dog #animal]" in lines[4]
    assert "Life at Google (life_at_google_video_id) [#google #career]" in \
           lines[5]
    assert "Video about nothing (nothing_video_id) []" in lines[6]


@mock.patch('builtins.input', lambda *args: 'No')
def test_flag_video_search_videos(capfd):
    player = VideoPlayer()
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    player.search_videos("cat")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 5
    assert "Successfully flagged video: Amazing Cats " \
           "(reason: dont_like_cats)" in lines[0]
    assert "Here are the results for cat:" in lines[1]
    assert "1) Another Cat Video (another_cat_video_id) [#cat #animal]" in \
           lines[2]
    assert ("Would you like to play any of the above? If yes, "
            "specify the number of the video.") in lines[3]
    assert ("If your answer is not a valid number, we will assume "
            "it's a no.") in lines[4]


@mock.patch('builtins.input', lambda *args: 'No')
def test_flag_video_search_videos_with_tag(capfd):
    player = VideoPlayer()
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    player.search_videos_tag("#cat")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 5
    assert "Successfully flagged video: Amazing Cats " \
           "(reason: dont_like_cats)" in lines[0]
    assert "Here are the results for #cat:" in lines[1]
    assert "1) Another Cat Video (another_cat_video_id) [#cat #animal]" in \
           lines[2]
    assert ("Would you like to play any of the above? If yes, "
            "specify the number of the video.") in lines[3]
    assert ("If your answer is not a valid number, we will assume "
            "it's a no.") in lines[4]


def test_flag_video_stops_playing_video(capfd):
    player = VideoPlayer()
    player.play_video("amazing_cats_video_id")
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    player.show_playing()
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 4
    assert "Playing video: Amazing Cats" in lines[0]
    assert "Stopping video: Amazing Cats" in lines[1]
    assert "Successfully flagged video: Amazing Cats " \
           "(reason: dont_like_cats)" in lines[2]
    assert "No video is currently playing" in lines[3]


def test_flag_video_leaves_video_if_video_is_different(capfd):
    player = VideoPlayer()
    player.play_video("amazing_cats_video_id")
    player.flag_video("another_cat_video_id", "dont_like_cats")
    player.show_playing()
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 3
    assert "Playing video: Amazing Cats" in lines[0]
    assert "Successfully flagged video: Another Cat Video " \
           "(reason: dont_like_cats)" in lines[1]
    assert "Currently playing: Amazing Cats (amazing_cats_video_id) " \
           "[#cat #animal]" in lines[2]


def test_flag_video_stops_paused_video(capfd):
    player = VideoPlayer()
    player.play_video("amazing_cats_video_id")
    player.pause_video()
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    player.show_playing()
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 5
    assert "Playing video: Amazing Cats" in lines[0]
    assert "Pausing video: Amazing Cats" in lines[1]
    assert "Stopping video: Amazing Cats" in lines[2]
    assert "Successfully flagged video: Amazing Cats " \
           "(reason: dont_like_cats)" in lines[3]
    assert "No video is currently playing" in lines[4]


def test_allow_video(capfd):
    player = VideoPlayer()
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    player.allow_video("amazing_cats_video_id")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 2
    assert "Successfully flagged video: Amazing Cats " \
           "(reason: dont_like_cats)" in lines[0]
    assert "Successfully removed flag from video: Amazing Cats" in lines[1]


def test_allow_video_not_flagged(capfd):
    player = VideoPlayer()
    player.allow_video("amazing_cats_video_id")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 1
    assert "Cannot remove flag from video: Video is not flagged" in lines[0]


def test_allow_video_nonexistent(capfd):
    player = VideoPlayer()
    player.allow_video("video_does_not_exist")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 1
    assert "Cannot remove flag from video: Video does not exist" in lines[0]


def test_allow_video_show_playlist(capfd):
    player = VideoPlayer()
    player.create_playlist("my_playlist")
    player.add_to_playlist("my_playlist", "amazing_cats_video_id")
    player.flag_video("amazing_cats_video_id", "dont_like_cats")
    player.show_playlist("my_playlist")
    player.allow_video("amazing_cats_video_id")
    player.show_playlist("my_playlist")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 8
    assert "Successfully created new playlist: my_playlist" in lines[0]
    assert "Added video to my_playlist: Amazing Cats" in lines[1]
    assert ("Successfully flagged video: Amazing Cats "
            "(reason: dont_like_cats)") in lines[2]
    assert "Showing playlist: my_playlist" in lines[3]
    assert ("Amazing Cats (amazing_cats_video_id) [#cat #animal] - FLAGGED "
            "(reason: dont_like_cats)") in lines[4]
    assert "Successfully removed flag from video: Amazing Cats" in lines[5]
    assert "Showing playlist: my_playlist" in lines[6]
    assert "Amazing Cats (amazing_cats_video_id) [#cat #animal]" in lines[7]
