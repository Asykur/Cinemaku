package com.asykurkhamid.submission.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.asykurkhamid.submission.Adapter.FavoriteAdapter;
import com.asykurkhamid.submission.Adapter.MoviesAdapter;
import com.asykurkhamid.submission.ParentClass;
import com.asykurkhamid.submission.Pojo.DataMovieJSON;
import com.asykurkhamid.submission.R;
import com.sackcentury.shinebuttonlib.ShineButton;

import static com.asykurkhamid.submission.Database.DBContract.MovieColumn.CONTENT_URI;

public class FavoriteActivity extends ParentClass {

    private RecyclerView rvFavorite;
    private Cursor listMovie;
    private ProgressBar pgFavorite;
    private FavoriteAdapter favoriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        rvFavorite = findViewById(R.id.rvFavorite);
        pgFavorite = findViewById(R.id.pgFavorite);
        setDefaultToolbar(true,getString(R.string.favorite));
    }

    @Override
    protected void onStart() {
        super.onStart();
        initRecycler();
        new LoadMovieAsync().execute();
    }

    private class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pgFavorite.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI,null,null,null,null);

        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            pgFavorite.setVisibility(View.GONE);
            listMovie = cursor;
            favoriteAdapter.setListMovie(listMovie);
            favoriteAdapter.notifyDataSetChanged();

            if (listMovie.getCount() == 0){
                LinearLayout linearLayout = findViewById(R.id.relaEmpty);
                linearLayout.setVisibility(View.VISIBLE);
                showSnackbarMessage("Favorite movie is empty");
            }
            else {
                rvFavorite.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(FavoriteActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        int column = 2;
        rvFavorite.setLayoutManager(new GridLayoutManager(FavoriteActivity.this,column));
        favoriteAdapter = new FavoriteAdapter(this);
        favoriteAdapter.setListMovie(listMovie);
        rvFavorite.setAdapter(favoriteAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvFavorite, message, Snackbar.LENGTH_LONG).show();
    }
}
