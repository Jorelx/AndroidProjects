package com.nanodegree.joel.popularmovies.movie;

/**
 * Created by joel on 10/21/15.
 */
public class Review {
    public String getAuthor() {
        return mAuthor;
    }

    public String getContent() {
        return mContent;
    }

    public String getUrl() {
        return mUrl;
    }

    private final String mAuthor;
    private final String mContent;
    private final String mUrl;

    public Review(String author, String content, String url) {
        this.mAuthor = author;
        this.mContent = content;
        this.mUrl = url;
    }
}
