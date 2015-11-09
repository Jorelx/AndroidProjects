package com.nanodegree.joel.popularmovies.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.nanodegree.joel.popularmovies.R;
import com.nanodegree.joel.popularmovies.movie.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by joel on 10/27/15.
 */
public class ReviewsAdapter extends CursorAdapter {

    public ReviewsAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_reviews, viewGroup, false);
        view.setTag(new ViewHolder(view));
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.userNameView.setText(cursor.getString(Constants.COL_REVIEW_AUTHOR));

        viewHolder.contentView.setText(cursor.getString(Constants.COL_REVIEW_CONTENT));
        viewHolder.contentView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static class ViewHolder {
        @Bind(R.id.list_item_user_name_view)
        TextView userNameView;

        @Bind(R.id.list_item_review_view)
        TextView contentView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
