package com.nanodegree.joel.popularmovies.task;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.nanodegree.joel.popularmovies.R;
import com.nanodegree.joel.popularmovies.movie.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joel on 9/16/15.
 */
public class FetchMoviesStrategy implements IFetchStrategy {
    private static final String LOG_TAG = FetchMoviesStrategy.class.getSimpleName();
    private static final String SORT_BY = "sort_by";
    private static final String PAGE = "page";
    private static final String OWM_RESULTS = "results";
    private static final String OWM_ID = "id";
    private static final String OWM_POSTER_PATH = "poster_path";

    private final ArrayAdapter<Movie> mAdapter;
    private String mSortByOption;

    public FetchMoviesStrategy(ArrayAdapter<Movie> adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public Uri appendParemters(Uri.Builder uriBuilder, String... params) {
        if (params.length < 2) {
            Log.e(LOG_TAG, "Invalid number of arguments");
            return null;
        }

        String pageNumber = params[1];
        mSortByOption = params[0];
        return uriBuilder.appendQueryParameter(SORT_BY, mSortByOption)
                .appendQueryParameter(PAGE, pageNumber)
                .build();
    }

    @Override
    public Object parseJson(String moviesJsonResult) throws JSONException {
        List<Movie> moviesList = new ArrayList<>();
        JSONObject moviesJson = new JSONObject(moviesJsonResult);
        JSONArray results = moviesJson.getJSONArray(OWM_RESULTS);

        for (int i = 0; i < results.length(); i++) {
            long id;
            String posterImage;
            JSONObject movieJson = results.getJSONObject(i);
            id = movieJson.getLong(OWM_ID);
            posterImage = movieJson.getString(OWM_POSTER_PATH);
            moviesList.add(new Movie(id, posterImage));
        }
        return moviesList;
    }

    @Override
    public void updateUIComponent(Object result) {
        if (result == null) {
            return;
        }
        List<Movie> moviesList = (List<Movie>)result;
        if (moviesList == null) {
            return;
        }
        this.mAdapter.clear();
        mAdapter.addAll(moviesList);

    }

    @Override
    public String getApiConfigurationOptions() {
        return "discover/movie";
    }

    @Override
    public String getCompletationMessage(Context context) {
        String sortByPop = context.getString(R.string.prefs_sort_by_popularity);
        String sortByRate = context.getString(R.string.prefs_sort_by_rate);

        if (sortByPop.equals(mSortByOption)) {
            return context.getString(R.string.prefs_sort_by_label_popularity);
        }

        if (sortByRate.equals(mSortByOption)) {
            return context.getString(R.string.prefs_sort_by_label_rate);
        }

        return null;
    }

}
