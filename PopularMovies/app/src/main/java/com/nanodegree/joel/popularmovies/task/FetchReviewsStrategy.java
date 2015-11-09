package com.nanodegree.joel.popularmovies.task;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.nanodegree.joel.popularmovies.data.MoviesProvider;
import com.nanodegree.joel.popularmovies.interfaces.IFetchStrategy;
import com.nanodegree.joel.popularmovies.movie.Review;
import com.nanodegree.joel.popularmovies.movie.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joel on 10/27/15.
 */
public class FetchReviewsStrategy implements IFetchStrategy {

    private final long mMovieId;
    private final Context mContext;

    public FetchReviewsStrategy(Context activity, long movieId) {
        this.mContext = activity;
        this.mMovieId = movieId;
    }

    @Override
    public Uri appendParameters(Uri.Builder uriBuilder, String... params) {
        return uriBuilder.build();
    }

    @Override
    public Object parseJson(String jsonReponse) throws JSONException {
        JSONObject reponseJson = new JSONObject(jsonReponse);
        JSONArray reviewsJSON = reponseJson.getJSONArray("results");
        ContentValues values[] = new ContentValues[reviewsJSON.length()];
        for (int i = 0 ; i < reviewsJSON.length(); i++) {
            JSONObject reviewJson = reviewsJSON.getJSONObject(i);
            String author  = reviewJson.getString("author");
            String content  = reviewJson.getString("content");
            String url  = reviewJson.getString("url");
            Review review = new Review(author, content, url);
            values[i] = Utils.getReviewContentValues(review, mMovieId);
        }
        mContext.getContentResolver().delete(MoviesProvider.Reviews.fromMovie(mMovieId), null, null);
        mContext.getContentResolver().bulkInsert(MoviesProvider.Reviews.CONTENT_URI, values);
        return null;
    }

    @Override
    public String getApiConfigurationOptions() {
        return "movie/" + mMovieId + "/reviews";
    }

    @Override
    public String getCompletationMessage() {
        return null;
    }

    @Override
    public Context getContext() {
        return mContext;
    }
}
