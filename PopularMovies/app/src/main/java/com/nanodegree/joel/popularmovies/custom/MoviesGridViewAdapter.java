package com.nanodegree.joel.popularmovies.custom;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.nanodegree.joel.popularmovies.movie.Movie;
import com.nanodegree.joel.popularmovies.movie.Utils;

import java.util.ArrayList;


/**
 * Created by joel on 9/14/15.
 */
public class MoviesGridViewAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG = MoviesGridViewAdapter.class.getCanonicalName();

    public MoviesGridViewAdapter(ArrayList<Movie> items, Context context) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie;
        ImageView imageView = (ImageView) convertView;

        if (imageView == null) {
            imageView = new ImageView(getContext());
            imageView.setAdjustViewBounds(true);
        }

        // Get the image URL for the current position.
        movie = getItem(position);

        if (movie == null) {
            Log.e(LOG_TAG, "Movie not found in the possition " + position);
            return imageView;
        }

        Utils.loadMoviePoster(imageView, Utils.PosterSize.MEDIUM, getContext(), movie.getPosterPath());
        return imageView;
    }
}
