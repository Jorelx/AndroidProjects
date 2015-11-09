package com.nanodegree.joel.popularmovies.task;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.nanodegree.joel.popularmovies.BuildConfig;
import com.nanodegree.joel.popularmovies.R;
import com.nanodegree.joel.popularmovies.interfaces.IFetchStrategy;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by joel on 9/14/15.
 */

public class FetchDataTask extends AsyncTask<String, Void, Object> {
    private final String LOG_TAG = FetchDataTask.class.getSimpleName();
    private static final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "api_key";
    private static final int ConnectionTimeout = 5000;

    private final IFetchStrategy mFetchStrategy;
    private ProgressDialog mDialog;
    private String mErrorMessage;

    public FetchDataTask(IFetchStrategy mFetchStrategy) {
        this.mFetchStrategy = mFetchStrategy;
        this.mErrorMessage = null;
    }

    @Override
    protected void onPreExecute() {
        mDialog = new ProgressDialog(
                mFetchStrategy.getContext());
        mDialog.setMessage(mFetchStrategy.getContext().getString(R.string.wait_message));
        mDialog.setIndeterminate(true);
        mDialog.setCancelable(false);
        mDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            String baseUrl = MOVIES_BASE_URL + mFetchStrategy.getApiConfigurationOptions();
            Uri.Builder uriBuilder = Uri.parse(baseUrl).buildUpon().appendQueryParameter(API_KEY, BuildConfig.MOVIEDB_API_KEY);
            Uri uri = mFetchStrategy.appendParameters(uriBuilder, params);
            URL url = new URL(uri.toString());
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(ConnectionTimeout);
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }

            if (builder.length() == 0) {
                return null;
            }
            return mFetchStrategy.parseJson(builder.toString());
        }
        catch (IOException ex) {
            Log.e(LOG_TAG,"Error ",  ex);
            mErrorMessage = ex.getMessage();
        } catch (JSONException ex) {
            Log.e(LOG_TAG, "An error ocurred parsing the Json response ", ex);
            mErrorMessage = ex.getMessage();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Log.e(LOG_TAG, "Error closing the stream", ex);
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object objects) {
        String completationMessage;
        try {
            super.onPostExecute(objects);
        } finally {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
        completationMessage = mErrorMessage == null? mFetchStrategy.getCompletationMessage() : mErrorMessage;
        if (completationMessage == null) return;
        Toast.makeText(mFetchStrategy.getContext(), completationMessage, Toast.LENGTH_SHORT).show();
    }
}
