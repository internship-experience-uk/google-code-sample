import pytest
from src.video import video_player


def test_search_videos(capfd):
  player = video_player.VideoPlayer()
  player.search_videos("cat")
  out, err = capfd.readouterr()
  assert "Here are the results for cat:" in out
  assert "1) Amazing Cats (amazing_cat_video_id)" in out
  assert "2) Another Cat video (another_cat_video_id)" in out
  assert ("Would you like to play any of the above? If yes, specify the number "
          "of the video. If your answer is not a valid number, we will assume "
          "it's a no.") in out


def test_search_videos_nonexistant(capfd):
  player = video_player.VideoPlayer()
  player.search_videos("blah")
  out, err = capfd.readouterr()
  assert "No search results for blah." in out


def test_search_videos_tag(capfd):
  player = video_player.VideoPlayer()
  player.show_all_videos()
  out, err = capfd.readouterr()
  assert "Here are the results for cat:" in out
  assert "1) Amazing Cats (amazing_cat_video_id)" in out
  assert "2) Another Cat video (another_cat_video_id)" in out
  assert """Would you like to play any of the above? If yes, specify the number of the video.
            If your answer is not a valid number, we will assume it's a no.""" in out


def test_search_videos_tag_nonexistant(capfd):
  player = video_player.VideoPlayer()
  player.search_videos_tag("#blah")
  out, err = capfd.readouterr()
  assert "No search results for #blah." in out
