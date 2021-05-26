package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * A class used to represent a Video Library.
 */
class VideoLibrary {

  private final HashMap<String, Video> videos;

  VideoLibrary() {
    this.videos = new HashMap<String, Video>();
    try {
      Scanner scanner = new Scanner(new File("videos.txt"));
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] split = line.split("\\|");
        String title = split[0].strip();
        String id = split[1].strip();
        List<String> tags = Arrays.stream(split[2].split(",")).map(String::strip).collect(
            Collectors.toList());
        this.videos.put(id, new Video(title, id, tags));
      }

    } catch (FileNotFoundException e) {
      System.out.println("Couldn't find videos.txt");
      e.printStackTrace();
    }
  }

  List<Video> getVideos() {
    return new ArrayList<Video>(this.videos.values());
  }

  Video getVideo(String videoId) throws Exception {
    if (this.videos.containsKey(videoId)) {
      return this.videos.get(videoId);
    }
    throw new Exception("Video not found in video library");
  }
}
