package com.nanodegree.joel.popularmovies.task;

import android.content.Context;
import android.net.Uri;

import org.json.JSONException;

/**
 * Created by joel on 9/16/15.
 */
public interface IFetchStrategy {
    Uri appendParemters(Uri.Builder uriBuilder, String... params);
    Object parseJson(String jsonReponse) throws JSONException;
    void updateUIComponent(Object result);
    String getApiConfigurationOptions();
    String getCompletationMessage(Context context);
}
