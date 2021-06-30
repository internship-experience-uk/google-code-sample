# Google Coding Challenge - Youtube Player

### Coding challenge to code a CLI version of YouTube

- should edit video_player.py (mostly)
- files use underscode `_` naming scheme

#### Some Extra
- implemented some `.bat` files to more easily run python commands

### Part 1
- [x] NUMBER_OF_VIDEOS
  - already implemented
- [x] SHOW_ALL_VIDEOS
  - To show all the videos
  ```
  YT> SHOW_ALL_VIDEOS
    Here's a list of all available videos:
    Amazing cats (amazing_cats_video_id) [#cat #animal]
    Another Cat Video (another_cat_video_id) [#cat #animal]
    Funny Dogs (funny_dogs_video_id) [#dog #animal]
    Life at Google (life_at_google_video_id) [#google #career]
    Video about nothing (nothing_video_id) []
  ```
  - implemented ```get_number_of_videos()``` that returns the number of videos
    - edited ```number_of_videos()``` so it uses ```get_number_of_videos()```
  - used ```sorted()``` with ```key = lambda ... ``` to sort the videos in terms of title in lexicographical order (as [sort()](https://docs.python.org/3/howto/sorting.html#:~:text=This%20idiom%20works%20because%20tuples%20are%20compared%20lexicographically%3B%20the%20first%20items%20are%20compared%3B%20if%20they%20are%20the%20same%20then%20the%20second%20items%20are%20compared%2C%20and%20so%20on)) does
- [x] PLAY <video_id>
  - Play the specified video
    - If the same video is already playing, then stop and play again.
    - If video does not exist, show that it cannot be played.
  ```
  YT> PLAY amazing_cats_video_id
  Playing video: Amazing Cats

  YT> PLAY funny_dogs_video_id
  Stopping video: Amazing Cats
  Playing video: Funny Dogs

  YT> PLAY funny_dogs_video_id
  Stopping video: Funny Dogs
  Playing video: Funny Dogs

  YT> PLAY some_other_video_id
  Cannot play video: Video does not exist
  ```
  - added `_video_playing` attribute to the `video_player` class
  - used `get_video(video_id)` method from `video` class
- [x] STOP
  - Stop the current video playing
  ```
  YT> PLAY amazing_cats_video_id
  Playing video: Amazing Cats

  YT> STOP
  Stopping video: Amazing Cats

  YT> STOP
  Cannot stop video: No video is currently playing
  ```
- [x] PLAY_RANDOM
  - Play a random video
    - If a video is already playing, stop the video
  ```
  YT> PLAY_RANDOM
  Playing video: Life at Google

  YT> PLAY_RANDOM
  Stopping video: Life at Google
  Playing video: Funny Dogs
  ```
    - imported `random.randint()` 
      - outside of class: so it does not have to be imported for every instance (unless python caches it)
      - `randint()` is both inclusive so the arguments are `0` and `number_of_videos - 1`
```
GreyTeddy - Dennis (Dionysios Ntouka)
Art by Joan G. Stark
          ___   .--.
    .--.-"   "-' .- |
   / .-,`          .'
   \   `           \
    '.            ! \
      |     !  .--.  |
      \        '--'  /.____
     /`-.     \__,'.'      `\
  __/   \`-.____.-' `\      /
  | `---`'-'._/-`     \----'    _ 
  |,-'`  /             |    _.-' `\
 .'     /              |--'`     / |
/      /\              `         | |
|   .\/  \      .--. __          \ |
 '-'      '._       /  `\         /
    jgs      `\    '     |------'`
               \  |      |
                \        /
                 '._  _.'

```