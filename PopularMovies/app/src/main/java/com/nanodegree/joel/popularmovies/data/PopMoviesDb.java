package com.nanodegree.joel.popularmovies.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by joel on 10/20/15.
 */
@Database(version = PopMoviesDb.VERSION,
        packageName = "com.nanodegree.joel.popularmovies.data.provider")
public class PopMoviesDb {
    public static final int VERSION = 1;

    private PopMoviesDb() {}

    public static class Tables {

        @Table(MovieColumns.class) public static final String MOVIES = "movies";
        @Table(VideoColumns.class) public static final String VIDEOS = "videos";
        @Table(ReviewColumns.class) public static final String REVIEWS = "reviews";
    }
}
