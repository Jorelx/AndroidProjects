package com.nanodegree.joel.popularmovies.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.nanodegree.joel.popularmovies.R;
import com.nanodegree.joel.popularmovies.adapter.ReviewsAdapter;
import com.nanodegree.joel.popularmovies.interfaces.IFetchStrategy;
import com.nanodegree.joel.popularmovies.movie.Constants;
import com.nanodegree.joel.popularmovies.movie.Utils;
import com.nanodegree.joel.popularmovies.task.FetchDataTask;
import com.nanodegree.joel.popularmovies.task.FetchReviewsStrategy;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by joel on 11/7/15.
 */
public class ReviewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{


    private Uri mReviewsUri;
    private ReviewsAdapter mReviewsAdapter;

    @Bind(R.id.empty_reviews_list)
    TextView mEmptyListMessage;

    @Bind(R.id.list_reviews)
    ListView mReviewList;

    @Bind(R.id.reviews_label_view)
    TextView mReviewsLabelView;

    @Override
    public void onStart() {
        super.onStart();
        if (mReviewsUri != null && Utils.hasInternetConnection(getActivity())) {
            long movieId = Long.parseLong(mReviewsUri.getLastPathSegment());
            IFetchStrategy fetchStrategy = new FetchReviewsStrategy(getActivity(), movieId);
            FetchDataTask fetcthData = new FetchDataTask(fetchStrategy);
            fetcthData.execute();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(Constants.REVIEWS_LOADER, null, this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reviews, container, false);
        ButterKnife.bind(this, rootView);
        mReviewsAdapter = new ReviewsAdapter(getActivity(), null, 0);
        mReviewList.setAdapter(mReviewsAdapter);

        Bundle arguments = getArguments();
        if (arguments != null) {
            mReviewsUri = arguments.getParcelable(Constants.KEY_REVIEWS_URI);
        }
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (mReviewsUri == null) {
            mReviewList.setVisibility(View.GONE);
            mReviewsLabelView.setVisibility(View.GONE);
            return null;
        }
        return new CursorLoader(getActivity(), mReviewsUri, Constants.REVIEW_COLUMNS, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mReviewsAdapter.swapCursor(data);
        if (data == null || data.getCount() == 0) {
            mReviewList.setVisibility(View.GONE);
            mEmptyListMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mReviewsAdapter.swapCursor(null);
    }

}
