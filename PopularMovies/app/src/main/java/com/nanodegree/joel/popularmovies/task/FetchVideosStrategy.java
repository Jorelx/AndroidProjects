package com.nanodegree.joel.popularmovies.task;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.nanodegree.joel.popularmovies.data.MoviesProvider;
import com.nanodegree.joel.popularmovies.interfaces.IFetchStrategy;
import com.nanodegree.joel.popularmovies.movie.Utils;
import com.nanodegree.joel.popularmovies.movie.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joel on 10/27/15.
 */
public class FetchVideosStrategy implements IFetchStrategy {
    private final long mMovieId;
    private final Context mContext;

    public FetchVideosStrategy(Context activity, long movieId) {
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
        JSONArray videosJSON = reponseJson.getJSONArray("results");
        ContentValues[] values = new ContentValues[videosJSON.length()];
        for (int i = 0 ; i < videosJSON.length(); i++) {
            JSONObject reviewJson = videosJSON.getJSONObject(i);
            String key  = reviewJson.getString("key");
            String name  = reviewJson.getString("name");
            String site  = reviewJson.getString("site");
            Video video = new Video(key, name, site);
            values[i] = Utils.getVideoContentValues(video, mMovieId);
        }

        mContext.getContentResolver().delete(MoviesProvider.Videos.fromMovie(mMovieId), null, null);
        mContext.getContentResolver().bulkInsert(MoviesProvider.Videos.CONTENT_URI, values);
        return null;
    }

    @Override
    public String getApiConfigurationOptions() {
        return "movie/" + mMovieId + "/videos";
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
