package com.nanodegree.joel.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.nanodegree.joel.popularmovies.custom.MoviesGridViewAdapter;
import com.nanodegree.joel.popularmovies.movie.Movie;
import com.nanodegree.joel.popularmovies.task.FetchDataTask;
import com.nanodegree.joel.popularmovies.task.FetchMoviesStrategy;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String FIRST_PAGE = "1";

    private ArrayAdapter<Movie> mGridApapter;

    @Override
    public void onStart() {
        super.onStart();
        updateView();
    }

    private void updateView(){
        final String defaultSortOption = getString(R.string.prefs_sort_by_popularity);
        FetchDataTask fetchWeatherTask = new FetchDataTask(new FetchMoviesStrategy(mGridApapter), getActivity());
        SharedPreferences sharePreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = sharePreferences.getString(getString(R.string.pref_sort_by_key), defaultSortOption);
        fetchWeatherTask.execute(sortBy, FIRST_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movies_grid, container, false);
        mGridApapter = new MoviesGridViewAdapter(new ArrayList<Movie>(), getActivity());
        GridView gView = (GridView)rootView.findViewById(R.id.search_view);
        gView.setAdapter(mGridApapter);
        gView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = mGridApapter.getItem(position);
                Intent detailsIntent = new Intent(getActivity(), MovieDetailsActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, String.valueOf(movie.getId()));
                startActivity(detailsIntent);
            }
        });
        gView.setBackgroundColor(Color.BLACK);
        return rootView;
    }
}
