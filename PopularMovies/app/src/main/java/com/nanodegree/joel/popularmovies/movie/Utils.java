package com.nanodegree.joel.popularmovies.movie;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;

import com.nanodegree.joel.popularmovies.R;
import com.nanodegree.joel.popularmovies.data.MovieColumns;
import com.nanodegree.joel.popularmovies.data.MoviesProvider;
import com.nanodegree.joel.popularmovies.data.ReviewColumns;
import com.nanodegree.joel.popularmovies.data.VideoColumns;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by joel on 9/19/15.
 */
public final class Utils {
    private static final String LOG_TAG = Utils.class.getSimpleName();
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/%s%s";

    private Utils(){}

    public static String getPreferedSortOption(Context context) {
        final String defaultSortOption = context.getString(R.string.prefs_sort_by_popularity);
        SharedPreferences sharePreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharePreferences.getString(context.getString(R.string.pref_sort_by_key), defaultSortOption);
    }

    public static void loadMoviePoster(final ImageView imageView, PosterSize posterSize, final Context context, String posterPath){
        String posterUrl = String.format(IMAGE_BASE_URL, posterSize.mPosterSize, posterPath);
        Log.v(LOG_TAG, "Loading poster: " + posterUrl);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context).load(posterUrl).into(imageView);
    }

    public static boolean hasInternetConnection(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


    public static boolean hasExtras(Intent intent, String... extras) {
        for (String extra : extras) {
            if (!intent.hasExtra(extra)) {
                return false;
            }
        }
        return true;
    }

    public static byte[] asByteArray(Drawable image) {
        BitmapDrawable bitDw = (BitmapDrawable) image;
        Bitmap bitmap = bitDw.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static Drawable byteArrayAsDrawable(byte[] image) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(image);
        return Drawable.createFromStream(inputStream, "");
    }

    public static boolean isFavoriteMovie(Activity activity, long movieId) {
        Cursor cursor = activity.getContentResolver().query(
                MoviesProvider.Movies.withId(movieId),
                new String[]{MovieColumns._ID},
                null,
                null,
                null);
        return cursor.moveToFirst();
    }

    public static Bundle createFragmentParams(String key, long movieId) {
        Bundle arguments = new Bundle();

        switch (key) {
            case Constants.KEY_MOVIE_URI:
                arguments.putParcelable(Constants.KEY_MOVIE_URI, MoviesProvider.Movies.withId(movieId));
                break;
            case Constants.KEY_REVIEWS_URI:
                arguments.putParcelable(Constants.KEY_REVIEWS_URI, MoviesProvider.Reviews.fromMovie(movieId));
                break;
            case Constants.KEY_VIDEOS_URI:
                arguments.putParcelable(Constants.KEY_VIDEOS_URI, MoviesProvider.Videos.fromMovie(movieId));
                break;
            default:
                Log.e(LOG_TAG, "Invalid key for fragment arguments " + key);
        }
        return arguments;
    }

    public static ContentValues getReviewContentValues(Review review, long movieId) {
        ContentValues values = new ContentValues();
        values.put(ReviewColumns.MOVIE_ID, movieId);
        values.put(ReviewColumns.AUTHOR, review.getAuthor());
        values.put(ReviewColumns.CONTENT, review.getContent());
        values.put(ReviewColumns.URL, review.getUrl());
        return values;
    }

    public static ContentValues getVideoContentValues(Video video, long movieId) {
        ContentValues values = new ContentValues();
        values.put(VideoColumns.MOVIE_ID, movieId);
        values.put(VideoColumns.NAME, video.getName());
        values.put(VideoColumns.KEY, video.getKey());
        values.put(VideoColumns.SITE, video.getSite());
        return values;
    }

    public enum PosterSize {
        SMALL("w154"), MEDIUM("w185"), LARGE("w342");
        private final String mPosterSize;

        PosterSize(String posterSize) {
            mPosterSize = posterSize;
        }
    }
}
