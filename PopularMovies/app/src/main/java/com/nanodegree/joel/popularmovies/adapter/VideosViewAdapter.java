package com.nanodegree.joel.popularmovies.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.nanodegree.joel.popularmovies.R;
import com.nanodegree.joel.popularmovies.movie.Constants;

/**
 * Created by joel on 10/27/15.
 */
public class VideosViewAdapter extends CursorAdapter {

    public VideosViewAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_videos, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView videoName = (TextView) view.findViewById(R.id.video_name);
        videoName.setText(cursor.getString(Constants.COL_VIDEO_NAME));
    }
}
