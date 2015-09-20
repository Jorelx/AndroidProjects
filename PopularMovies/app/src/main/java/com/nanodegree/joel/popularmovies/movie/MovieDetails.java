package com.nanodegree.joel.popularmovies.movie;

import android.util.LruCache;

/**
 * Created by joel on 9/16/15.
 */
public class MovieDetails {
    private final static int NUMBER_OF_DETAILS = 4;
    private final static  LruCache<String, MovieDetails> mMovieDetailsCache = new LruCache<>(NUMBER_OF_DETAILS);

    public String getTitle() {
        return mTitle;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getAverageRate() {
        return mAverageRate;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    private final String mTitle;
    private final String mReleaseDate;
    private final String mAverageRate;
    private final String mSynopsis;

    public String getPosterPath() {
        return mPosterPath;
    }

    private final String mPosterPath;

    public MovieDetails(String title, String releaseDate, String averageRate, String synopsis, String posterPath) {
        this.mTitle = title;
        this.mReleaseDate = releaseDate;
        this.mAverageRate = averageRate;
        this.mSynopsis = synopsis;
        this.mPosterPath = posterPath;
    }

    public static void AddToCache(String movieId, MovieDetails movieDetails)
    {
        mMovieDetailsCache.put(movieId, movieDetails);
    }

    public static MovieDetails getInstance(String movieId) {
        return mMovieDetailsCache.get(movieId);
    }
}
