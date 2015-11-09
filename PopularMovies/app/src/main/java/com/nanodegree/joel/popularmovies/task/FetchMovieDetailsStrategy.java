package com.nanodegree.joel.popularmovies.task;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.nanodegree.joel.popularmovies.data.MovieColumns;
import com.nanodegree.joel.popularmovies.data.MoviesProvider;
import com.nanodegree.joel.popularmovies.interfaces.IFetchStrategy;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joel on 9/17/15.
 */
public class FetchMovieDetailsStrategy implements IFetchStrategy {

    private static final String OWM_TITLE = "title";
    private static final String OWM_RELEASE_DATE = "release_date";
    private static final String OWM_SYNOPSIS = "overview";
    private static final String OWM_RUNTIME = "runtime";

    private final Context mContext;
    private final long mMovieId;

    public FetchMovieDetailsStrategy(Context context, long movieId) {
        this.mContext = context;
        this.mMovieId = movieId;
    }

    @Override
    public Uri appendParameters(Uri.Builder uriBuilder, String... params) {
        return uriBuilder.build();
    }

    @Override
    public Object parseJson(String jsonReponse) throws JSONException {
        JSONObject moviesJson = new JSONObject(jsonReponse);
        String title = moviesJson.getString(OWM_TITLE);
        String releaseDate = moviesJson.getString(OWM_RELEASE_DATE).split("-")[0];
        String synopsis = moviesJson.getString(OWM_SYNOPSIS);
        String runtime = moviesJson.getString(OWM_RUNTIME) + "min";
//        JSONArray reviewsJSON = moviesJson.getJSONObject("reviews").getJSONArray("results");
//        JSONArray videosJSON = moviesJson.getJSONObject("videos").getJSONArray("results");

        ContentValues values = new ContentValues();
        values.put(MovieColumns.TITLE, title);
        values.put(MovieColumns.RELEASE_DATE, releaseDate);
        values.put(MovieColumns.SYNOPSIS, synopsis);
        values.put(MovieColumns.RUNTIME, runtime);
        mContext.getContentResolver().update(
                MoviesProvider.Movies.withId(mMovieId),
                values,
                null,
                null);

        return null;
    }

    @Override
    public String getApiConfigurationOptions() {
        return "movie/" + mMovieId;
    }

    @Override
    public String getCompletationMessage() {
        return "Movie Details Loaded."; //Nothing to display
    }

    @Override
    public Context getContext() {
        return mContext;
    }
}
