package com.google;

import java.util.ArrayList;
import java.util.List;

class SearchResult implements Comparable<Video> {

    private final List<Video> searchResult;

    SearchResult() {
        this.searchResult = new ArrayList<>();
    }

    List<Video> getSearchResult() {
        return searchResult;
    }

    @Override
    public int compareTo(Video video) {
        return Integer.compare(video.getTitle().compareTo(video.getTitle()), 0);
    }
}
