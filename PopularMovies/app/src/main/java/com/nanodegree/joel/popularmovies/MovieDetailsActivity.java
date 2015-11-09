package com.nanodegree.joel.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.nanodegree.joel.popularmovies.fragment.MovieDetailsFragment;
import com.nanodegree.joel.popularmovies.fragment.ReviewsFragment;
import com.nanodegree.joel.popularmovies.fragment.VideosFragment;
import com.nanodegree.joel.popularmovies.movie.Constants;
import com.nanodegree.joel.popularmovies.movie.Utils;


public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();

        if (savedInstanceState == null && intent != null && intent.getData() != null) {
            Uri movieUri = intent.getData();
            long movieId = Long.parseLong(movieUri.getLastPathSegment());
            Bundle movieArguments = new Bundle();
            movieArguments.putParcelable(Constants.KEY_MOVIE_URI, movieUri);

            MovieDetailsFragment detailsFragment = new MovieDetailsFragment();
            detailsFragment.setArguments(movieArguments);

            Bundle videosArguments = Utils.createFragmentParams(Constants.KEY_VIDEOS_URI, movieId);
            VideosFragment videosFragment = new VideosFragment();
            videosFragment.setArguments(videosArguments);

            Bundle reviewsArguments = Utils.createFragmentParams(Constants.KEY_REVIEWS_URI, movieId);
            ReviewsFragment reviewsFragment = new ReviewsFragment();
            reviewsFragment.setArguments(reviewsArguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_details_container, detailsFragment)
                    .add(R.id.movie_videos_container, videosFragment)
                    .add(R.id.movie_reviews_container, reviewsFragment)
                    .commit();

        }
    }
}
