package com.nanodegree.joel.popularmovies.task;

import android.content.Context;
import android.net.Uri;

import com.nanodegree.joel.popularmovies.MovieDetailsFragment;
import com.nanodegree.joel.popularmovies.movie.MovieDetails;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joel on 9/17/15.
 */
public class FetchMovieDetailsStrategy implements IFetchStrategy {

    private static final String OWM_TITLE = "title";
    private static final String OWM_RELEASE_DATE = "release_date";
    private static final String OWM_POSTER_PATH = "poster_path";
    private static final String OWM_RATE = "vote_average";
    private static final String OWM_SYNOPSIS = "overview";

    private final MovieDetailsFragment mRootView;
    private final String mMovieId;

    public FetchMovieDetailsStrategy(MovieDetailsFragment viewFragment, String movieId) {
        this.mRootView = viewFragment;
        this.mMovieId = movieId;
    }

    @Override
    public Uri appendParemters(Uri.Builder uriBuilder, String... params) {
        return uriBuilder.build();
    }

    @Override
    public Object parseJson(String jsonReponse) throws JSONException {
        JSONObject moviesJson = new JSONObject(jsonReponse);
        String title = moviesJson.getString(OWM_TITLE);
        String releaseDate = moviesJson.getString(OWM_RELEASE_DATE);
        String averageRate = moviesJson.getString(OWM_RATE);
        String synopsis = moviesJson.getString(OWM_SYNOPSIS);
        String posterPath = moviesJson.getString(OWM_POSTER_PATH);
        return new MovieDetails(title,releaseDate,averageRate,synopsis,posterPath);
    }

    @Override
    public void updateUIComponent(Object result) {
        MovieDetails details = (MovieDetails)result;
        MovieDetails.AddToCache(mMovieId, details);
        mRootView.UpdateDetailsView(details);
    }

    @Override
    public String getApiConfigurationOptions() {
        return "movie/" + mMovieId;
    }

    @Override
    public String getCompletationMessage(Context context) {
        return null; //Nothing to display
    }
}
