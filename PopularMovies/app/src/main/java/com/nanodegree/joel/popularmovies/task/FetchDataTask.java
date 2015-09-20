package com.nanodegree.joel.popularmovies.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.nanodegree.joel.popularmovies.R;

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
    private static final String KEY = "ENTER YOUR API KEY HERE";

    private final IFetchStrategy mFetchStrategy;
    private final Context mContext;
    private ProgressDialog mDialog;

    public FetchDataTask(IFetchStrategy mFetchStrategy, Context mContext) {
        this.mFetchStrategy = mFetchStrategy;
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {
        mDialog = new ProgressDialog(
                mContext);
        mDialog.setMessage(mContext.getString(R.string.wait_message));
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
            Uri.Builder uriBuilder = Uri.parse(baseUrl).buildUpon().appendQueryParameter(API_KEY, KEY);
            Uri uri = mFetchStrategy.appendParemters(uriBuilder, params);
            URL url = new URL(uri.toString());

            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
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
        } catch (JSONException ex) {
            Log.e(LOG_TAG, "An error ocurred parsing the Json response ", ex);
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
            mFetchStrategy.updateUIComponent(objects);
            super.onPostExecute(objects);
        } finally {
            mDialog.dismiss();
        }
        completationMessage = mFetchStrategy.getCompletationMessage(mContext);
        if (completationMessage == null) return;
        Toast.makeText(mContext, completationMessage, Toast.LENGTH_SHORT).show();
    }
}
