package com.nanodegree.joel.popularmovies.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.nanodegree.joel.popularmovies.R;
import com.nanodegree.joel.popularmovies.movie.Constants;
import com.nanodegree.joel.popularmovies.movie.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by joel on 11/7/15.
 */
public class MoviesAdapter extends CursorAdapter {

    private final String LOG_CAT = MoviesAdapter.class.getSimpleName();


    public MoviesAdapter(Context context) {
        super(context, null, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_movie, viewGroup, false);
        view.setTag(new ViewHolder(view));
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        String posterUrl = cursor.getString(Constants.COL_POP_MOVIE_POSTER_URL);
        boolean isFavorite = cursor.getInt(Constants.COL_POP_IS_FAVORITE) == 1;
        byte[] posterImage = cursor.getBlob(Constants.COL_POP_MOVIE_DATA_POSTER);

        if (Utils.hasInternetConnection(context)) {
            Log.i(LOG_CAT, "Loading poster path from API");
            Utils.loadMoviePoster(viewHolder.mPosterImageView, Utils.PosterSize.MEDIUM, context, posterUrl);
        } else if (isFavorite && posterImage != null){
            Log.i(LOG_CAT, "Loading poster path from DB");
            Drawable poster = Utils.byteArrayAsDrawable(posterImage);
            viewHolder.mPosterImageView.setImageDrawable(poster);
        }
    }

    public static class ViewHolder {
//        @Bind(R.id.top_line)
//        View mTopLineView;

//        @Bind(R.id.bottom_line)
//        View mBottomLineView;

        @Bind(R.id.imageview_poster)
        ImageView mPosterImageView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void makeHighLinesVisible() {
//            mTopLineView.setVisibility(View.VISIBLE);
//            mBottomLineView.setVisibility(View.VISIBLE);
        }

        public void hideHighLines() {
  //          mTopLineView.setVisibility(View.GONE);
  //          mBottomLineView.setVisibility(View.GONE);
        }
    }
}
