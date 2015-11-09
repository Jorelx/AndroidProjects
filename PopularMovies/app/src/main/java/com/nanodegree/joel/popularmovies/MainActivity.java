package com.nanodegree.joel.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nanodegree.joel.popularmovies.data.MoviesProvider;
import com.nanodegree.joel.popularmovies.fragment.MovieDetailsFragment;
import com.nanodegree.joel.popularmovies.fragment.ReviewsFragment;
import com.nanodegree.joel.popularmovies.fragment.TopMoviesFragment;
import com.nanodegree.joel.popularmovies.fragment.VideosFragment;
import com.nanodegree.joel.popularmovies.interfaces.OnMovieSeletedListener;
import com.nanodegree.joel.popularmovies.movie.Constants;
import com.nanodegree.joel.popularmovies.movie.Utils;

import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements OnMovieSeletedListener {

    private boolean mShowMovieDetails;

    private TextView noMoviesSelecteView;
    private String mSortOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowMovieDetails = findViewById(R.id.movie_details_container) != null;
        mSortOption = Utils.getPreferedSortOption(this);
        ButterKnife.bind(this);
        if (mShowMovieDetails && savedInstanceState == null)  {
            noMoviesSelecteView = (TextView) findViewById(R.id.message_select_a_movie);
            noMoviesSelecteView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String newSortOption = Utils.getPreferedSortOption(this);
        if (newSortOption != null && !newSortOption.equals(mSortOption)) {
            TopMoviesFragment tmf = (TopMoviesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_top_movies);
            if (tmf != null) {
                tmf.updateMoviesList();
            }
            mSortOption = newSortOption;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieItemSelected(long movieId) {

        if (mShowMovieDetails) {
            if (noMoviesSelecteView != null) {
                noMoviesSelecteView.setVisibility(View.GONE);
            }
            Bundle movieArguments = Utils.createFragmentParams(Constants.KEY_MOVIE_URI, movieId);
            MovieDetailsFragment detailsFragment = new MovieDetailsFragment();
            detailsFragment.setArguments(movieArguments);

            Bundle videosArguments = Utils.createFragmentParams(Constants.KEY_VIDEOS_URI, movieId);
            VideosFragment videosFragment = new VideosFragment();
            videosFragment.setArguments(videosArguments);

            Bundle reviewsArguments = Utils.createFragmentParams(Constants.KEY_REVIEWS_URI, movieId);
            ReviewsFragment reviewsFragment = new ReviewsFragment();
            reviewsFragment.setArguments(reviewsArguments);

            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.movie_details_container, detailsFragment).
                    replace(R.id.movie_videos_container, videosFragment).
                    replace(R.id.movie_reviews_container, reviewsFragment).
                    commit();
        } else {
            Uri movieUri = MoviesProvider.Movies.withId(movieId);
            Intent intent = new Intent(this, MovieDetailsActivity.class);
            intent.setData(movieUri);
            startActivity(intent);
        }

    }

}
