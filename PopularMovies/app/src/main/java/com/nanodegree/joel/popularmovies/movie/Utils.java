package com.nanodegree.joel.popularmovies.movie;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by joel on 9/19/15.
 */
public final class Utils {
    private static final String LOG_TAG = Utils.class.getSimpleName();
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/%s%s";

    private Utils(){}

    public static void loadMoviePoster(ImageView imageView, PosterSize posterSize, Context context, String posterPath ){
        String posterUrl = String.format(IMAGE_BASE_URL, posterSize.mPosterSize, posterPath);
        Log.v(LOG_TAG, "Loading poster: " + posterUrl);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context).load(posterUrl).into(imageView);
    }

    public enum PosterSize {
        SMALL("w154"), MEDIUM("w185"), LARGE("w342");
        private final String mPosterSize;

        PosterSize(String posterSize) {
            mPosterSize = posterSize;
        }
    }
}
