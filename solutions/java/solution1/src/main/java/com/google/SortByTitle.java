package com.google;

import java.util.Comparator;

// This class can be used by a Collection of Videos like a List of Videos. By using this,
// I can sort a list of Videos by their title easily whenever we need to display videos in a sorted way.
public class SortByTitle implements Comparator<Video> {
    @Override
    public int compare(Video o1, Video o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }
}
