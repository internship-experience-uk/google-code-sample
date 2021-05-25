import pytest
import re
from src.video import video_player


def test_show_all_videos(capfd):
  player = video_player.VideoPlayer()
  player.show_all_videos()
  out, err = capfd.readouterr()
  assert "Here's a list of all available videos:" in out
  assert "Amazing cats (amazing_cats_video_id) [#cat #animal]" in out
  assert "Another Cat video (another_cat_video_id) [#cat #animal]" in out
  assert "Funny Dogs (funny_dogs_video_id) [#dog #animal]" in out
  assert "Life at Google (life_at_google_video_id) [#google #career]" in out
  assert all(out.splitlines()[i] <= out.splitlines()[i + 1]
             for i in range(len(out.splitlines()) - 1))


def test_play_video(capfd):
  player = video_player.VideoPlayer()
  player.play_video("amazing_cat_video_id")
  out, err = capfd.readouterr()
  assert "Playing video:  Amazing Cats" in out


def test_show_playing(capfd):
  player = video_player.VideoPlayer()
  player.play_video("amazing_cat_video_id")
  player.show_playing()
  out, err = capfd.readouterr()
  assert "Playing video:  Amazing Cats" in out
  assert "Currently playing: Amazing Cats (amazing_cats_video_id)" in out


def test_show_playing(capfd):
  player = video_player.VideoPlayer()
  player.show_playing()
  out, err = capfd.readouterr()
  assert "Nothing currently playing." in out


def test_play_video_stop_current(capfd):
  player = video_player.VideoPlayer()
  player.play_video("amazing_cat_video_id")
  player.play_video("amazing_dog_video_id")
  out, err = capfd.readouterr()
  assert "Stopping video: Amazing Cats" in out
  assert "Playing video: Funny Dogs" in out


def test_play_random_video(capfd):
  player = video_player.VideoPlayer()
  player.play_random_video()
  out, err = capfd.readouterr()
  assert re.match(
      "Playing video: (Amazing cats|Another Cat video|Funny Dogs|Life at Google)\n",
      out)


def test_stop_video(capfd):
  player = video_player.VideoPlayer()
  player.play_video("amazing_cat_video_id")
  player.stop_video()
  out, err = capfd.readouterr()
  assert "Playing video: Amazing Cats" in out
  assert "Stopping video: Amazing Cats" in out


def test_stop_video_none_playing(capfd):
  player = video_player.VideoPlayer()
  player.stop_video()
  out, err = capfd.readouterr()
  assert "Nothing is currently playing." in out


def test_pause_video(capfd):
  player = video_player.VideoPlayer()
  player.play_video("amazing_cat_video_id")
  player.pause_video()
  out, err = capfd.readouterr()
  assert "Playing video: Amazing Cats" in out
  assert "Pausing video: Amazing Cats" in out


def test_pause_video_none_playing(capfd):
  player = video_player.VideoPlayer()
  player.pause_video()
  out, err = capfd.readouterr()
  assert "Can not pause video: No video currently playing." in out


def test_continue_video(capfd):
  player = video_player.VideoPlayer()
  player.play_video("amazing_cat_video_id")
  player.pause_video()
  player.continue_video()
  out, err = capfd.readouterr()
  assert "Playing video: Amazing Cats" in out
  assert "Pausing video: Amazing Cats" in out
  assert "Continuing video: Amazing Cats" in out


def test_continue_video_playing(capfd):
  player = video_player.VideoPlayer()
  player.play_video("amazing_cat_video_id")
  player.continue_video()
  out, err = capfd.readouterr()
  assert "Can not continue video: Video is currently playing and not paused." in out


def test_continue_none_playing(capfd):
  player = video_player.VideoPlayer()
  player.continue_video()
  out, err = capfd.readouterr()
  assert "Can not continue video: No video currently playing." in out
