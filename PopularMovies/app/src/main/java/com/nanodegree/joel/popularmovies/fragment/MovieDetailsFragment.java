package com.nanodegree.joel.popularmovies.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanodegree.joel.popularmovies.R;
import com.nanodegree.joel.popularmovies.data.MovieColumns;
import com.nanodegree.joel.popularmovies.interfaces.IFetchStrategy;
import com.nanodegree.joel.popularmovies.movie.Constants;
import com.nanodegree.joel.popularmovies.movie.Utils;
import com.nanodegree.joel.popularmovies.task.FetchDataTask;
import com.nanodegree.joel.popularmovies.task.FetchMovieDetailsStrategy;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_CAT = MovieDetailsFragment.class.getSimpleName();

    private static final String MOVIE_SHARE_HASHTAG = " #PopMoviesAPP";

    private ShareActionProvider mShareActionProvider;

    @Bind(R.id.movie_poster_view)
    ImageView mImageViewPoster;

    @Bind(R.id.view_favorite_icon)
    ImageView mImageViewFavorite;

    @Bind(R.id.movie_vote_average_view)
    TextView mTextViewRate;

    @Bind(R.id.movie_release_date_view)
    TextView mTextViewRelease;

    @Bind(R.id.movie_title_view)
    TextView mTitle;

    @Bind(R.id.movie_plot_synopsis_view)
    TextView mTextViewSynopsis;

    @Bind(R.id.movie_runtime_view)
    TextView mTextViewRuntime;

    @Bind(R.id.button_mark_favorite)
    Button mButtonMarkAsFavorite;

    private Uri mMovieUri;
    private String mMovie;

    public MovieDetailsFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mMovieUri != null && Utils.hasInternetConnection(getActivity())) {
            long movieId = Long.parseLong(mMovieUri.getLastPathSegment());
            IFetchStrategy ss = new FetchMovieDetailsStrategy(getActivity(), movieId);
            FetchDataTask tas = new FetchDataTask(ss);
            tas.execute();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        Bundle arguments = getArguments();
        if (arguments != null) {
            mMovieUri = arguments.getParcelable(Constants.KEY_MOVIE_URI);
        }

        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_detail_fragment, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        if (mMovie != null) {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        }
    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mMovie + MOVIE_SHARE_HASHTAG);
        return shareIntent;
    }

    private void updateUIForFavoriteMovie() {
        mButtonMarkAsFavorite.setVisibility(View.GONE);
        mImageViewFavorite.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.button_mark_favorite)
    public void saveFavorite() {
        updateUIForFavoriteMovie();
        byte[] posterData = Utils.asByteArray(mImageViewPoster.getDrawable());
        ContentValues values = new ContentValues();
        values.put(MovieColumns.POSTER_BLOB, posterData);
        values.put(MovieColumns.IS_FAVORITE, 1);
        getActivity().getContentResolver().update(mMovieUri,values, null, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(Constants.DETAILS_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        if (null == mMovieUri) {
            Log.i(LOG_CAT, "Movie not loaded from DB.");
            mButtonMarkAsFavorite.setVisibility(View.GONE);
            return null;
        }
        return new CursorLoader(getActivity(), mMovieUri, Constants.MOVIE_COLUMNS, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (null == data || !data.moveToFirst())
        {
            Log.i(LOG_CAT, "Movie cursor not loaded from DB.");
            return;
        }

        String title = data.getString(Constants.COL_MOVIE_TITLE);
        String releaseDate = data.getString(Constants.COL_MOVIE_RELEASE);
        String rate = data.getString(Constants.COL_MOVIE_RATE);
        String synopsis = data.getString(Constants.COL_MOVIE_SYNOPSIS);
        String posterPath = data.getString(Constants.COL_MOVIE_POSTER);
        String runtime = data.getString(Constants.COL_MOVIE_RUNTIME);
        boolean isFavorite = data.getInt(Constants.COL_MOVIE_IS_FAVORITE) == 1;
        byte[] posterData = data.getBlob(Constants.COL_MOVIE_DATA_POSTER);

        mImageViewPoster.setAdjustViewBounds(true);
        if (isFavorite && posterData != null) {
            mImageViewPoster.setImageDrawable(Utils.byteArrayAsDrawable(posterData));
        } else {
            Utils.loadMoviePoster(mImageViewPoster, Utils.PosterSize.MEDIUM, getActivity(), posterPath);
        }

        if (isFavorite) {
            updateUIForFavoriteMovie();
        }

        mTextViewRate.setText(rate + "/10");
        mTextViewRelease.setText(releaseDate);
        mTitle.setText(title);
        mTextViewRuntime.setText(runtime);
        mTextViewSynopsis.setText(synopsis == null? "": synopsis);

        mMovie = String.format("%s(%s) - %s/10", title, releaseDate, rate);

        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
