package com.nanodegree.joel.popularmovies.movie;

import com.nanodegree.joel.popularmovies.data.MovieColumns;
import com.nanodegree.joel.popularmovies.data.ReviewColumns;
import com.nanodegree.joel.popularmovies.data.VideoColumns;

/**
 * Created by joel on 10/25/15.
 */
public final class Constants {

    private Constants() {
    }

    public static final String[] POP_MOVIE_COLUMNS = {
            MovieColumns._ID,
            MovieColumns.POSTER_PATH,
            MovieColumns.IS_FAVORITE,
            MovieColumns.POSTER_BLOB
    };

    public static final int COL_POP_MOVIE_ID = 0;
    public static final int COL_POP_MOVIE_POSTER_URL = 1;
    public static final int COL_POP_IS_FAVORITE = 2;
    public static final int COL_POP_MOVIE_DATA_POSTER = 3;

    public static final String[] MOVIE_COLUMNS = {
            MovieColumns._ID,
            MovieColumns.TITLE,
            MovieColumns.RELEASE_DATE,
            MovieColumns.AVERAGE_RATE,
            MovieColumns.SYNOPSIS,
            MovieColumns.POSTER_PATH,
            MovieColumns.RUNTIME,
            MovieColumns.POSTER_BLOB,
            MovieColumns.IS_FAVORITE,
    };

    public static final int COL_MOVIE_ID = 0;
    public static final int COL_MOVIE_TITLE = 1;
    public static final int COL_MOVIE_RELEASE = 2;
    public static final int COL_MOVIE_RATE = 3;
    public static final int COL_MOVIE_SYNOPSIS = 4;
    public static final int COL_MOVIE_POSTER = 5;
    public static final int COL_MOVIE_RUNTIME = 6;
    public static final int COL_MOVIE_DATA_POSTER = 7;
    public static final int COL_MOVIE_IS_FAVORITE = 8;

    //public static final String EXTRA_DATA_MOVIE = "MovieData";
    //public static final String EXTRA_DATA_FRAGMENT_FACTORY = "FragmentData";
    public static final String EXTRA_DATA_REVIEWS = "ReviewsContentUri";
    public static final String EXTRA_DATA_VIDEOS = "VideosContentUri";

    public static final String[] VIDEO_COLUMNS = {
            VideoColumns._ID,
            VideoColumns.NAME,
            VideoColumns.KEY
    };

    public static final int COL_VIDEO_NAME = 1;
    public static final int COL_VIDEO_KEY = 2;

    public static final String[] REVIEW_COLUMNS = {
            ReviewColumns._ID,
            ReviewColumns.AUTHOR,
            ReviewColumns.CONTENT
    };

    public static final int COL_REVIEW_AUTHOR = 1;
    public static final int COL_REVIEW_CONTENT = 2;

    public static final String YOUTUBE_BASE_URL = "http://www.youtube.com/watch";
    public static final String YOUTUBE_KEY_PARAM = "v";

    public static final int MOVIE_LOADER = 0;
    public static final int DETAILS_LOADER = 0;
    public static final int REVIEWS_LOADER = 0;
    public static final int VIDEOS_LOADER = 0;

    public static final String KEY_MOVIE_URI = "movie_uri";
    public static final String KEY_REVIEWS_URI = "reviews_uri";
    public static final String KEY_VIDEOS_URI = "videos_uri";
}
