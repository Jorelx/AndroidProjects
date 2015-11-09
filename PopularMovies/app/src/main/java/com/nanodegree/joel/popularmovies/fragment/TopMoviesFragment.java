package com.nanodegree.joel.popularmovies.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.nanodegree.joel.popularmovies.R;
import com.nanodegree.joel.popularmovies.adapter.MoviesAdapter;
import com.nanodegree.joel.popularmovies.data.MovieColumns;
import com.nanodegree.joel.popularmovies.data.MoviesProvider;
import com.nanodegree.joel.popularmovies.interfaces.OnMovieSeletedListener;
import com.nanodegree.joel.popularmovies.movie.Constants;
import com.nanodegree.joel.popularmovies.movie.Utils;
import com.nanodegree.joel.popularmovies.task.FetchDataTask;
import com.nanodegree.joel.popularmovies.task.FetchMoviesStrategy;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A placeholder fragment containing a simple view.
 */
public class TopMoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_CAT = TopMoviesFragment.class.getSimpleName();
    private static String LAST_SELECTED_MOVIE_KEY = "position";
    private MoviesAdapter mMoviesAdapter;

    @Bind(R.id.top_movies_view)
    GridView mMoviesGridView;

    private int mLastSelectedPosition = GridView.INVALID_POSITION;

    @Override
    public void onStart() {
        super.onStart();
        fetchMoviesData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void fetchMoviesData(){
        String sortBy = Utils.getPreferedSortOption(getActivity());
        if (sortBy.equalsIgnoreCase(getString(R.string.prefs_sort_by_favorite))) {
            Log.i(LOG_CAT, "Movies are not going to be fetched from API.");
        } else if (Utils.hasInternetConnection(getActivity())){
            Log.i(LOG_CAT, "Loading movies from API.");
            FetchDataTask fetchWeatherTask = new FetchDataTask(
                    new FetchMoviesStrategy(getActivity()));
            fetchWeatherTask.execute(sortBy);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(Constants.MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_top_movies, container, false);
        ButterKnife.bind(this, rootView);
        mMoviesAdapter = new MoviesAdapter(getContext());
        mMoviesGridView.setAdapter(mMoviesAdapter);

        mMoviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor movieCursor = (Cursor) mMoviesAdapter.getItem(position);
                if (movieCursor != null) {
                    long movieId = movieCursor.getLong(Constants.COL_POP_MOVIE_ID);
                    ((OnMovieSeletedListener) getActivity()).onMovieItemSelected(movieId);
                    mLastSelectedPosition = position;
                }
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(LAST_SELECTED_MOVIE_KEY)) {
            mLastSelectedPosition = savedInstanceState.getInt(LAST_SELECTED_MOVIE_KEY);
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(LAST_SELECTED_MOVIE_KEY, mLastSelectedPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = null;
        String orderBy = null;
        String sortPref = Utils.getPreferedSortOption(getActivity());

        if (sortPref.equalsIgnoreCase(getString(R.string.prefs_sort_by_favorite))) {
            selection = MovieColumns.IS_FAVORITE + "= 1";
        } else if (!Utils.hasInternetConnection(getActivity())){
            Toast.makeText(
                    getActivity(),
                    R.string.message_no_internet,
                    Toast.LENGTH_SHORT).
                    show();
            return null;
        } else if (sortPref.equalsIgnoreCase(getString(R.string.prefs_sort_by_popularity))) {
            Log.i(LOG_CAT, "Sort by Popularity");
            orderBy = MovieColumns.POPULARITY + " DESC";
        } else {
            Log.i(LOG_CAT, "Sort by average rate");
            orderBy = MovieColumns.AVERAGE_RATE + " DESC";
        }

        return new CursorLoader(
                getActivity(),
                MoviesProvider.Movies.CONTENT_URI,
                Constants.POP_MOVIE_COLUMNS,
                selection,
                null,
                orderBy);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mMoviesAdapter.swapCursor(data);
        if (mLastSelectedPosition != GridView.INVALID_POSITION) {
            mMoviesGridView.smoothScrollToPosition(mLastSelectedPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMoviesAdapter.swapCursor(null);
    }

    public void updateMoviesList() {
        fetchMoviesData();
        mLastSelectedPosition = GridView.INVALID_POSITION;
        mMoviesAdapter.swapCursor(null);
        getLoaderManager().restartLoader(Constants.MOVIE_LOADER, null, this);
    }
}
