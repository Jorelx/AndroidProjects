package com.nanodegree.joel.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanodegree.joel.popularmovies.movie.MovieDetails;
import com.nanodegree.joel.popularmovies.movie.Utils;
import com.nanodegree.joel.popularmovies.task.FetchDataTask;
import com.nanodegree.joel.popularmovies.task.FetchMovieDetailsStrategy;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {

    private View mRootView;

    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String movieId = intent.getStringExtra(Intent.EXTRA_TEXT);
            MovieDetails detail = MovieDetails.getInstance(movieId);
            if (detail == null) {
                FetchDataTask task = new FetchDataTask(new FetchMovieDetailsStrategy(this, movieId), getActivity());
                task.execute();
            } else {
                UpdateDetailsView(detail);
            }
        }

        return mRootView;

    }

    public void UpdateDetailsView(MovieDetails moviewDetails) {
        ImageView poster = (ImageView)mRootView.findViewById(R.id.movie_poster);
        poster.setAdjustViewBounds(true);
        Utils.loadMoviePoster(poster, Utils.PosterSize.LARGE, getActivity(), moviewDetails.getPosterPath());

        TextView rate = (TextView)mRootView.findViewById(R.id.movie_vote_average);
        rate.setText(moviewDetails.getAverageRate());

        TextView release = (TextView)mRootView.findViewById(R.id.movie_release_Date);
        release.setText(moviewDetails.getReleaseDate());

        TextView title = (TextView)mRootView.findViewById(R.id.movie_title);
        title.setText(moviewDetails.getTitle());

        TextView synopsis = (TextView)mRootView.findViewById(R.id.movie_plot_synopsis);
        synopsis.setText(moviewDetails.getSynopsis() == null ? "" : moviewDetails.getSynopsis());
    }
}
