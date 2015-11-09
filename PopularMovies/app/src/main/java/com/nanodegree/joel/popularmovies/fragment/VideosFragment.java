package com.nanodegree.joel.popularmovies.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.nanodegree.joel.popularmovies.R;
import com.nanodegree.joel.popularmovies.adapter.VideosViewAdapter;
import com.nanodegree.joel.popularmovies.interfaces.IFetchStrategy;
import com.nanodegree.joel.popularmovies.movie.Constants;
import com.nanodegree.joel.popularmovies.movie.Utils;
import com.nanodegree.joel.popularmovies.task.FetchDataTask;
import com.nanodegree.joel.popularmovies.task.FetchVideosStrategy;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by joel on 11/7/15.
 */
public class VideosFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private final static String LOG_CAT = VideosFragment.class.getSimpleName();

    private VideosViewAdapter mVideosAdapter;
    private Uri mVideosUri;

    @Bind(R.id.list_videos)
    ListView mVideosList;

    @Bind(R.id.empty_videos_list)
    TextView emptyListMessage;

    @Bind(R.id.videos_label_view)
    TextView mVideosLabelView;


    @Override
    public void onStart() {
        super.onStart();
        if (mVideosUri != null && Utils.hasInternetConnection(getActivity())) {
            long movieId = Long.parseLong(mVideosUri.getLastPathSegment());
            IFetchStrategy fetchStrategy = new FetchVideosStrategy(getActivity(), movieId);
            FetchDataTask fetcthData = new FetchDataTask(fetchStrategy);
            fetcthData.execute();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(Constants.VIDEOS_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_videos, container, false);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mVideosUri = arguments.getParcelable(Constants.KEY_VIDEOS_URI);
        }

        ButterKnife.bind(this, rootView);

        mVideosAdapter = new VideosViewAdapter(getActivity(), null, 0);
        mVideosList.setAdapter(mVideosAdapter);
        mVideosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
                if (cursor != null) {
                    String videoKey = cursor.getString(Constants.COL_VIDEO_KEY);
                    Uri videoUri = Uri.parse(Constants.YOUTUBE_BASE_URL).buildUpon().appendQueryParameter(Constants.YOUTUBE_KEY_PARAM, videoKey).build();
                    Intent intent = new Intent(Intent.ACTION_VIEW, videoUri);
                    startActivity(intent);
                }
            }
        });
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null == mVideosUri) {
            Log.e(LOG_CAT, "Reviews uri is not valid retrieve its data from DB.");
            mVideosList.setVisibility(View.GONE);
            mVideosLabelView.setVisibility(View.GONE);
            return null;
        }
        return new CursorLoader(getActivity(), mVideosUri, Constants.VIDEO_COLUMNS, null,null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mVideosAdapter.swapCursor(data);
        if (data == null || data.getCount() == 0) {
            mVideosList.setVisibility(View.GONE);
            emptyListMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mVideosAdapter.swapCursor(null);
    }

}
