from src.video_player import VideoPlayer
from unittest import mock


@mock.patch('builtins.input', lambda *args: 'No')
def test_search_videos_with_no_answer(capfd):
    player = VideoPlayer()
    player.search_videos("cat")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 5
    assert "Here are the results for cat:" in lines[0]
    assert "1) Amazing Cats (amazing_cats_video_id) [#cat #animal]" in lines[1]
    assert "2) Another Cat Video (another_cat_video_id) [#cat #animal]" in lines[2]
    assert ("Would you like to play any of the above? If yes, "
            "specify the number of the video.") in lines[3]
    assert (
               "If your answer is not a valid number, we will assume "
               "it's a no.") in lines[4]
    assert "Playing video" not in out


@mock.patch('builtins.input', lambda *args: '2')
def test_search_videos_and_play_answer(capfd):
    player = VideoPlayer()
    player.search_videos("cat")

    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 6
    assert "Here are the results for cat:" in lines[0]
    assert "1) Amazing Cats (amazing_cats_video_id) [#cat #animal]" in lines[1]
    assert "2) Another Cat Video (another_cat_video_id) [#cat #animal]" in lines[2]
    assert ("Would you like to play any of the above? If yes, "
            "specify the number of the video.") in lines[3]
    assert ("If your answer is not a valid number, we will assume "
            "it's a no.") in lines[4]
    assert "Playing video: Another Cat Video" in lines[5]


@mock.patch('builtins.input', lambda *args: '6')
def test_search_videos_number_out_of_bounds(capfd):
    player = VideoPlayer()
    player.search_videos("cat")

    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 5
    assert "Here are the results for cat:" in lines[0]
    assert "1) Amazing Cats (amazing_cats_video_id) [#cat #animal]" in lines[1]
    assert "2) Another Cat Video (another_cat_video_id) [#cat #animal]" in lines[2]
    assert ("Would you like to play any of the above? If yes, "
            "specify the number of the video.") in lines[3]
    assert ("If your answer is not a valid number, we will assume "
            "it's a no.") in lines[4]
    assert "Playing video" not in out


@mock.patch('builtins.input', lambda *args: 'ab3g')
def test_search_videos_invalid_number(capfd):
    player = VideoPlayer()
    player.search_videos("cat")

    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 5
    assert "Here are the results for cat:" in lines[0]
    assert "1) Amazing Cats (amazing_cats_video_id) [#cat #animal]" in lines[1]
    assert "2) Another Cat Video (another_cat_video_id) [#cat #animal]" in lines[2]
    assert ("Would you like to play any of the above? If yes, "
            "specify the number of the video.") in lines[3]
    assert ("If your answer is not a valid number, we will assume "
            "it's a no.") in lines[4]
    assert "Playing video" not in out


def test_search_videos_no_results(capfd):
    player = VideoPlayer()
    player.search_videos("blah")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 1
    assert "No search results for blah" in lines[0]


@mock.patch('builtins.input', lambda *args: 'No')
def test_search_videos_with_tag_no_answer(capfd):
    player = VideoPlayer()
    player.search_videos_tag("#cat")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 5
    assert "Here are the results for #cat:" in lines[0]
    assert "1) Amazing Cats (amazing_cats_video_id) [#cat #animal]" in lines[1]
    assert "2) Another Cat Video (another_cat_video_id) [#cat #animal]" in lines[2]
    assert ("Would you like to play any of the above? If yes, "
            "specify the number of the video.") in lines[3]
    assert ("If your answer is not a valid number, we will assume "
            "it's a no.") in lines[4]


@mock.patch('builtins.input', lambda *args: '1')
def test_search_videos_with_tag_play_answered_number(capfd):
    player = VideoPlayer()
    player.search_videos_tag("#cat")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 6
    assert "Here are the results for #cat:" in lines[0]
    assert "1) Amazing Cats (amazing_cats_video_id) [#cat #animal]" in lines[1]
    assert "2) Another Cat Video (another_cat_video_id) [#cat #animal]" in lines[2]
    assert ("Would you like to play any of the above? If yes, "
            "specify the number of the video.") in lines[3]
    assert ("If your answer is not a valid number, we will assume "
            "it's a no.") in lines[4]
    assert "Playing video: Amazing Cats" in lines[5]


@mock.patch('builtins.input', lambda *args: '5')
def test_search_videos_with_tag_number_out_of_bounds(capfd):
    player = VideoPlayer()
    player.search_videos_tag("#cat")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 5
    assert "Here are the results for #cat:" in lines[0]
    assert "1) Amazing Cats (amazing_cats_video_id) [#cat #animal]" in lines[1]
    assert "2) Another Cat Video (another_cat_video_id) [#cat #animal]" in lines[2]
    assert ("Would you like to play any of the above? If yes, "
            "specify the number of the video.") in lines[3]
    assert ("If your answer is not a valid number, we will assume "
            "it's a no.") in lines[4]
    assert "Playing video" not in out


def test_search_videos_tag_no_results(capfd):
    player = VideoPlayer()
    player.search_videos_tag("#blah")
    out, err = capfd.readouterr()
    lines = out.splitlines()
    assert len(lines) == 1
    assert "No search results for #blah" in lines[0]
