package com.nanodegree.joel.popularmovies.movie;

/**
 * Created by joel on 9/14/15.
 */
public class Movie {
    private final long mId;
    private final String mPosterPath;

    public Movie(long id, String posterPath) {
        this.mId = id;
        this.mPosterPath = posterPath;
    }


    public String getPosterPath() {
        return mPosterPath;
    }

    public long getId() {
        return mId;
    }

}
