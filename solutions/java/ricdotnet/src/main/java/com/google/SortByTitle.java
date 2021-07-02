package com.google;

import java.util.Comparator;

public class SortByTitle implements Comparator<Video> {

  @Override
  public int compare(Video v1, Video v2) {
    return v1.getTitle().compareTo(v2.getTitle());
  }
}
