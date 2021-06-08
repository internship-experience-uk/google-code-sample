import re
from src.video_player import VideoPlayer


def test_show_all_videos(capfd):
    player = VideoPlayer()
    player.show_all_videos()
    out, err = capfd.readouterr()
    assert "Here's a list of all available videos:" in out
    assert "Amazing Cats (amazing_cats_video_id) [#cat #animal]" in out
    assert "Another Cat Video (another_cat_video_id) [#cat #animal]" in out
    assert "Funny Dogs (funny_dogs_video_id) [#dog #animal]" in out
    assert "Life at Google (life_at_google_video_id) [#google #career]" in out
    assert "Video about nothing (nothing_video_id) []" in out
    assert all(out.splitlines()[i] <= out.splitlines()[i + 1]
               for i in range(1, len(out.splitlines()) - 1))


def test_play_video(capfd):
    player = VideoPlayer()
    player.play_video("amazing_cats_video_id")
    out, err = capfd.readouterr()
    assert "Playing video: Amazing Cats" in out


def test_play_video_nonexistent(capfd):
    player = VideoPlayer()
    player.play_video("does_not_exist")
    out, err = capfd.readouterr()
    assert "Cannot play video: Video does not exist" in out


def test_play_video_stop_previous(capfd):
    player = VideoPlayer()
    player.play_video("amazing_cats_video_id")
    player.play_video("funny_dogs_video_id")
    out, err = capfd.readouterr()
    assert "Stopping video: Amazing Cats" in out
    assert "Playing video: Funny Dogs" in out


def test_play_video_dont_stop_previous_if_nonexistent(capfd):
    player = VideoPlayer()
    player.play_video("amazing_cats_video_id")
    player.play_video("some_other_video")
    out, err = capfd.readouterr()
    assert "Stopping video: Amazing Cats" not in out
    assert "Cannot play video: Video does not exist" in out


def test_stop_video(capfd):
    player = VideoPlayer()
    player.play_video("amazing_cats_video_id")
    player.stop_video()
    out, err = capfd.readouterr()
    assert "Playing video: Amazing Cats" in out
    assert "Stopping video: Amazing Cats" in out


def test_stop_video_twice(capfd):
    player = VideoPlayer()
    player.play_video("amazing_cats_video_id")
    player.stop_video()
    player.stop_video()
    out, err = capfd.readouterr()
    assert "Playing video: Amazing Cats" in out
    assert "Stopping video: Amazing Cats" in out
    assert "Cannot stop video: No video is currently playing" in out


def test_stop_video_none_playing(capfd):
    player = VideoPlayer()
    player.stop_video()
    out, err = capfd.readouterr()
    assert "Cannot stop video: No video is currently playing" in out


def test_play_random_video(capfd):
    player = VideoPlayer()
    player.play_random_video()
    out, err = capfd.readouterr()
    assert re.match(
        "Playing video: (Amazing Cats|Another Cat Video|Funny Dogs|Life at Google|Video about nothing)",
        out)


def test_play_random_stops_previous_video(capfd):
    player = VideoPlayer()
    player.play_video("amazing_cats_video_id")
    player.play_random_video()
    out, err = capfd.readouterr()
    assert "Stopping video: Amazing Cats" in out
    assert re.match(
        "Playing video: (Amazing Cats|Another Cat Video|Funny Dogs|Life at Google|Video about nothing)",
        out)


def test_show_playing(capfd):
    player = VideoPlayer()
    player.play_video("amazing_cats_video_id")
    player.show_playing()
    out, err = capfd.readouterr()
    assert "Playing video: Amazing Cats" in out
    assert "Currently playing: Amazing Cats (amazing_cats_video_id) [#cat #animal]" in out


def test_show_nothing_playing(capfd):
    player = VideoPlayer()
    player.show_playing()
    out, err = capfd.readouterr()
    assert "No video is currently playing" in out


def test_pause_video(capfd):
    player = VideoPlayer()
    player.play_video("amazing_cats_video_id")
    player.pause_video()
    out, err = capfd.readouterr()
    assert "Playing video: Amazing Cats" in out
    assert "Pausing video: Amazing Cats" in out


def test_pause_video_show_playing(capfd):
    player = VideoPlayer()
    player.play_video("amazing_cats_video_id")
    player.pause_video()
    player.show_playing()
    out, err = capfd.readouterr()
    assert "Currently playing: Amazing Cats (amazing_cats_video_id) " \
           "[#cat #animal] - PAUSED" in out


def test_pause_video_none_playing(capfd):
    player = VideoPlayer()
    player.pause_video()
    out, err = capfd.readouterr()
    assert "Cannot pause video: No video is currently playing" in out


def test_continue_video(capfd):
    player = VideoPlayer()
    player.play_video("amazing_cats_video_id")
    player.pause_video()
    player.continue_video()
    out, err = capfd.readouterr()
    assert "Playing video: Amazing Cats" in out
    assert "Pausing video: Amazing Cats" in out
    assert "Continuing video: Amazing Cats" in out


def test_continue_video_not_paused(capfd):
    player = VideoPlayer()
    player.play_video("amazing_cats_video_id")
    player.continue_video()
    out, err = capfd.readouterr()
    assert "Cannot continue video: Video is not paused" in out


def test_continue_none_playing(capfd):
    player = VideoPlayer()
    player.continue_video()
    out, err = capfd.readouterr()
    assert "Cannot continue video: No video is currently playing" in out
