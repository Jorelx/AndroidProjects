package com.nanodegree.joel.popularmovies.movie;

/**
 * Created by joel on 10/21/15.
 */
public class Video {
    public String getKey() {
        return mKey;
    }

    public String getName() {
        return mName;
    }

    public String getSite() {
        return mSite;
    }

    private final String mKey;
    private final String mName;
    private final String mSite;

    public Video(String key, String name, String site) {
        this.mKey = key;
        this.mName = name;
        this.mSite = site;
    }
}
