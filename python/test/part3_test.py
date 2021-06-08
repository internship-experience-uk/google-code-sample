from src.video_player import VideoPlayer
from unittest import mock


@mock.patch('builtins.input', lambda *args: 'No')
def test_search_videos_with_no_answer(capfd):
    player = VideoPlayer()
    player.search_videos("cat")
    out, err = capfd.readouterr()
    assert "Here are the results for cat:" in out
    assert "1) Amazing Cats (amazing_cats_video_id)" in out
    assert "2) Another Cat Video (another_cat_video_id)" in out
    assert ("Would you like to play any of the above? If yes, "
            "specify the number of the video.") in out
    assert (
               "If your answer is not a valid number, we will assume "
               "it's a no.") in out
    assert "Playing video" not in out


@mock.patch('builtins.input', lambda *args: '2')
def test_search_videos_and_play_answer(capfd):
    player = VideoPlayer()
    player.search_videos("cat")

    out, err = capfd.readouterr()
    assert "Here are the results for cat:" in out
    assert "1) Amazing Cats (amazing_cats_video_id)" in out
    assert "2) Another Cat Video (another_cat_video_id)" in out
    assert ("Would you like to play any of the above? If yes, "
            "specify the number of the video.") in out
    assert ("If your answer is not a valid number, we will assume "
            "it's a no.") in out
    assert "Playing video: Another Cat Video" in out


@mock.patch('builtins.input', lambda *args: '6')
def test_search_videos_number_out_of_bounds(capfd):
    player = VideoPlayer()
    player.search_videos("cat")

    out, err = capfd.readouterr()
    assert "Here are the results for cat:" in out
    assert "1) Amazing Cats (amazing_cats_video_id)" in out
    assert "2) Another Cat Video (another_cat_video_id)" in out
    assert ("Would you like to play any of the above? If yes, "
            "specify the number of the video.") in out
    assert ("If your answer is not a valid number, we will assume "
            "it's a no.") in out
    assert "Playing video" not in out


@mock.patch('builtins.input', lambda *args: 'ab3g')
def test_search_videos_invalid_number(capfd):
    player = VideoPlayer()
    player.search_videos("cat")

    out, err = capfd.readouterr()
    assert "Here are the results for cat:" in out
    assert "1) Amazing Cats (amazing_cats_video_id)" in out
    assert "2) Another Cat Video (another_cat_video_id)" in out
    assert ("Would you like to play any of the above? If yes, "
            "specify the number of the video.") in out
    assert ("If your answer is not a valid number, we will assume "
            "it's a no.") in out
    assert "Playing video" not in out


def test_search_videos_no_results(capfd):
    player = VideoPlayer()
    player.search_videos("blah")
    out, err = capfd.readouterr()
    assert "No search results for blah" in out


@mock.patch('builtins.input', lambda *args: 'No')
def test_search_videos_with_tag_no_answer(capfd):
    player = VideoPlayer()
    player.search_videos_tag("#cat")
    out, err = capfd.readouterr()
    assert "Here are the results for #cat:" in out
    assert "1) Amazing Cats (amazing_cats_video_id)" in out
    assert "2) Another Cat Video (another_cat_video_id)" in out
    assert ("Would you like to play any of the above? If yes, "
            "specify the number of the video.") in out
    assert ("If your answer is not a valid number, we will assume "
            "it's a no.") in out


@mock.patch('builtins.input', lambda *args: '1')
def test_search_videos_with_tag_play_answered_number(capfd):
    player = VideoPlayer()
    player.search_videos_tag("#cat")
    out, err = capfd.readouterr()
    assert "Here are the results for #cat:" in out
    assert "1) Amazing Cats (amazing_cats_video_id)" in out
    assert "2) Another Cat Video (another_cat_video_id)" in out
    assert ("Would you like to play any of the above? If yes, "
            "specify the number of the video.") in out
    assert ("If your answer is not a valid number, we will assume "
            "it's a no.") in out
    assert "Playing video: Amazing Cats" in out


@mock.patch('builtins.input', lambda *args: '5')
def test_search_videos_with_tag_number_out_of_bounds(capfd):
    player = VideoPlayer()
    player.search_videos_tag("#cat")
    out, err = capfd.readouterr()
    assert "Here are the results for #cat:" in out
    assert "1) Amazing Cats (amazing_cats_video_id)" in out
    assert "2) Another Cat Video (another_cat_video_id)" in out
    assert ("Would you like to play any of the above? If yes, "
            "specify the number of the video.") in out
    assert ("If your answer is not a valid number, we will assume "
            "it's a no.") in out
    assert "Playing video" not in out


def test_search_videos_tag_no_results(capfd):
    player = VideoPlayer()
    player.search_videos_tag("#blah")
    out, err = capfd.readouterr()
    assert "No search results for #blah" in out
