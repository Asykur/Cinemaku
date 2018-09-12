package com.example.movie_provider.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movie_provider.R;

import static com.example.movie_provider.Database.DBContract.MovieColumn.FAVORITE;
import static com.example.movie_provider.Database.DBContract.MovieColumn.DATE_RELEASE;
import static com.example.movie_provider.Database.DBContract.MovieColumn.POSTER_PATH;
import static com.example.movie_provider.Database.DBContract.MovieColumn.TITLE;
import static com.example.movie_provider.Database.DBContract.getColumnString;


public class MovieAdapter extends CursorAdapter {

    public MovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_movie,viewGroup,false);
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null){
            TextView tvTitle = view.findViewById(R.id.tvTitle);
            TextView tvRelease = view.findViewById(R.id.tvRelease);
            TextView tvVote = view.findViewById(R.id.tvVoted);
            ImageView imgPoster = view.findViewById(R.id.imgMovies);

            tvTitle.setText(getColumnString(cursor,TITLE));
            tvRelease.setText(getColumnString(cursor, DATE_RELEASE));
            tvVote.setText(getColumnString(cursor,FAVORITE));

            String img = "http://image.tmdb.org/t/p/w185"+getColumnString(cursor,POSTER_PATH);

            Glide.with(imgPoster.getContext())
                    .load(img)
                    .into(imgPoster);
        }
    }
}
