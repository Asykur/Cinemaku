package com.example.movie_provider.Activity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.movie_provider.Adapter.MovieAdapter;
import com.example.movie_provider.R;
import com.karumi.dexter.Dexter;

import static android.provider.BaseColumns._ID;
import static com.example.movie_provider.Database.DBContract.MovieColumn.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    private ListView rvMovie;
    private MovieAdapter adapter;
    private final int LOAD_ID = 110;
    private LinearLayout linEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Movie Provider");
        linEmpty = findViewById(R.id.relaEmpty);
        rvMovie = findViewById(R.id.lvMovie);
        adapter = new MovieAdapter(this, null, true);
        rvMovie.setAdapter(adapter);
        rvMovie.setOnItemClickListener(this);

        getSupportLoaderManager().initLoader(LOAD_ID, null, this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null){
            linEmpty.setVisibility(View.VISIBLE);
        }else {
            adapter.swapCursor(cursor);
            rvMovie.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Cursor cursor = (Cursor) adapter.getItem(i);
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
        Intent intent = new Intent(MainActivity.this, DetailMovieActivitys.class);
        intent.setData(Uri.parse(CONTENT_URI + "/" + id));
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
