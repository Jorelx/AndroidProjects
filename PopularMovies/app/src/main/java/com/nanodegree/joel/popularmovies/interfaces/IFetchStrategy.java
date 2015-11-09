package com.nanodegree.joel.popularmovies.interfaces;

import android.content.Context;
import android.net.Uri;

import org.json.JSONException;

/**
 * Created by joel on 9/16/15.
 */
public interface IFetchStrategy {
    Uri appendParameters(Uri.Builder uriBuilder, String... params);
    Object parseJson(String jsonReponse) throws JSONException;
    String getApiConfigurationOptions();
    String getCompletationMessage();
    Context getContext();
}
