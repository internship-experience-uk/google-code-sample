#pragma once

#include <string>

/**
 * A class used to represent a Video Player.
 *
 * When actually implementing this class, and adding members, etc, you might need to implement
 * the currently-default constructors (and don't forget about rule-of-five). Until we need them,
 * rule-of-zero.
 */
class VideoPlayer {
     public:
        void showAllVideos();
        void playVideo(std::string videoId);
        void playRandomVideo();
        void stopVideo();
        void pauseVideo();
        void continueVideo();
        void showPlaying();
        void createPlaylist( std::string playlistName);
        void addVideoToPlaylist( std::string playlistName,  std::string videoId);
        void removeFromPlaylist( std::string playlistName,  std::string videoId);
        void clearPlaylist( std::string playlistName);
        void deletePlaylist( std::string playlistName);
        void showPlaylist( std::string playlistName);
        void showAllPlaylists();
        void searchVideos( std::string searchTerm);
        void searchVideosWithTag( std::string videoTag);
        void flagVideo( std::string videoId);
        void flagVideo( std::string videoId,  std::string reason);
        void allowVideo( std::string videoId);

    // If you need to define either of the below methods, you should define all of them.
    // Otherwise strange things might happen, and you might miss out on optimizations
    //public:
    //    VideoPlayer() = default;
    //    ~VideoPlayer() = default;
    //    VideoPlayer(VideoPlayer const & other) = default;
    //    VideoPlayer(VideoPlayer && other) noexcept = default;
    //    VideoPlayer & operator=(VideoPlayer const & other) noexcept = default;
    //    VideoPlayer & operator=(VideoPlayer&& other) noexcept = default;
};
