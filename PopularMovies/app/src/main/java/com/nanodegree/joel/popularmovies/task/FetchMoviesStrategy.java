package com.nanodegree.joel.popularmovies.task;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.nanodegree.joel.popularmovies.R;
import com.nanodegree.joel.popularmovies.data.MovieColumns;
import com.nanodegree.joel.popularmovies.data.MoviesProvider;
import com.nanodegree.joel.popularmovies.interfaces.IFetchStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
    private static final String OWM_POPULARITY = "popularity";
    private static final String OWM_RATE = "vote_average";
    private String mSortByOption;
    private final Context mContext;

    public FetchMoviesStrategy(Context context) {
        mContext = context;
    }

    @Override
    public Uri appendParameters(Uri.Builder uriBuilder, String... params) {
        if (params.length < 1) {
            Log.e(LOG_TAG, "Invalid number of arguments");
            return null;
        }

        String pageNumber = "1";
        mSortByOption = params[0];
        return uriBuilder.appendQueryParameter(SORT_BY, mSortByOption)
                .appendQueryParameter(PAGE, pageNumber)
                .build();
    }

    @Override
    public Object parseJson(String moviesJsonResult) throws JSONException {
        JSONObject moviesJson = new JSONObject(moviesJsonResult);
        JSONArray results = moviesJson.getJSONArray(OWM_RESULTS);
        ArrayList<ContentValues> moviesContentValues = new ArrayList<>();
        ArrayList<ContentProviderOperation> updates = new ArrayList<>();
        Set<Long> st = getFavoriteMoviesId();
        long id;
        String posterPath;
        double averageRate;
        double popularity;
        JSONObject movieJson;

        for (int i = 0; i < results.length(); i++) {
            movieJson = results.getJSONObject(i);
            id = movieJson.getLong(OWM_ID);
            posterPath = movieJson.getString(OWM_POSTER_PATH);
            averageRate = movieJson.getDouble(OWM_RATE);
            popularity = movieJson.getDouble(OWM_POPULARITY);

            ContentValues values = new ContentValues();
            values.put(MovieColumns.POSTER_PATH, posterPath);
            values.put(MovieColumns.POPULARITY, popularity);
            values.put(MovieColumns.AVERAGE_RATE, averageRate);

            if (st.contains(id)) {
                getContext().
                        getContentResolver().
                        update(MoviesProvider.Movies.CONTENT_URI,
                                values,
                                MovieColumns._ID + "=" + id,
                                null);
            } else {
                values.put(MovieColumns._ID, id);
                moviesContentValues.add(values);
            }
        }

        saveOnDatabase(moviesContentValues, updates);
        return null;
    }

    private void saveOnDatabase(ArrayList<ContentValues> moviesContentValues, ArrayList<ContentProviderOperation> updates) {
        mContext.getContentResolver().delete(MoviesProvider.Movies.CONTENT_URI, MovieColumns.IS_FAVORITE + " <> 1 ", null);

        Log.i(LOG_TAG, "Saving movie data in DB");
        ContentValues[] valuesArray = new ContentValues[moviesContentValues.size()];
        moviesContentValues.toArray(valuesArray);
        mContext.getContentResolver().bulkInsert(MoviesProvider.Movies.CONTENT_URI, valuesArray);

    }

    private Set<Long> getFavoriteMoviesId() {
        Set<Long> idsSet = new HashSet<>();
        Cursor moviesId = mContext.getContentResolver().query(
                MoviesProvider.Movies.CONTENT_URI,
                new String[]{ MovieColumns._ID},
                MovieColumns.IS_FAVORITE + " = 1 ", null, null);

        while(moviesId.moveToNext()) {
            idsSet.add(moviesId.getLong(0));
        }
        return idsSet;
    }

    @Override
    public String getApiConfigurationOptions() {
        return "discover/movie";
    }

    @Override
    public String getCompletationMessage() {
        final String sortByPop = mContext.getString(R.string.prefs_sort_by_popularity);
        final String sortByRate = mContext.getString(R.string.prefs_sort_by_rate);

        if (sortByPop.equals(mSortByOption)) {
            return mContext.getString(R.string.prefs_sort_by_label_popularity);
        }

        if (sortByRate.equals(mSortByOption)) {
            return mContext.getString(R.string.prefs_sort_by_label_rate);
        }

        return mContext.getString(R.string.prefs_sort_by_label_favorite);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

}
