package com.nanodegree.joel.popularmovies.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by joel on 10/22/15.
 */
@ContentProvider(authority = MoviesProvider.AUTHORITY,
        database = PopMoviesDb.class,
        packageName = "com.nanodegree.joel.popularmovies.data.provider")
public final class MoviesProvider {

    private MoviesProvider(){}

    public static final String AUTHORITY = "com.nanodegree.joel.popularmovies.data.MoviesProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String MOVIES = "movies";
        String REVIEWS = "reviews";
        String VIDEOS = "videos";
        String FROM_MOVIE = "fromMovie";
    }

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = PopMoviesDb.Tables.MOVIES) public static class Movies {
        @ContentUri(
                path = Path.MOVIES,
                type = "vnd.android.cursor.dir/movie")
        public static final Uri CONTENT_URI = buildUri(Path.MOVIES);

        @InexactContentUri(
                path = Path.MOVIES + "/#",
                name = "MOVIE_ID",
                type = "vnd.android.cursor.item/movie",
                whereColumn = MovieColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return buildUri(Path.MOVIES, String.valueOf(id));
        }
    }

    @TableEndpoint(table = PopMoviesDb.Tables.REVIEWS) public static class Reviews {
        @ContentUri(
                path = Path.REVIEWS,
                type = "vnd.android.cursor.dir/review")
        public static final Uri CONTENT_URI = buildUri(Path.REVIEWS);

        @InexactContentUri(
                name = "REVIEWS_FROM_MOVIE",
                path = Path.REVIEWS + "/" + Path.FROM_MOVIE + "/#",
                type = "vnd.android.cursor.dir/list",
                whereColumn = ReviewColumns.MOVIE_ID,
                pathSegment = 2)
        public static Uri fromMovie(long movieId) {
            return buildUri(Path.REVIEWS, Path.FROM_MOVIE, String.valueOf(movieId));
        }

    }

    @TableEndpoint(table = PopMoviesDb.Tables.VIDEOS) public static class Videos {
        @ContentUri(
                path = Path.VIDEOS,
                type = "vnd.android.cursor.dir/review")
        public static final Uri CONTENT_URI = buildUri(Path.VIDEOS);

        @InexactContentUri(
                name = "VIDEOS_FROM_MOVIE",
                path = Path.VIDEOS + "/" + Path.FROM_MOVIE + "/#",
                type = "vnd.android.cursor.dir/list",
                whereColumn = VideoColumns.MOVIE_ID,
                pathSegment = 2)
        public static Uri fromMovie(long movieId) {
            return buildUri(Path.VIDEOS, Path.FROM_MOVIE, String.valueOf(movieId));
        }

    }



}
