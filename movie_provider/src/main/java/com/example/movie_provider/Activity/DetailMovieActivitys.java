package com.example.movie_provider.Activity;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movie_provider.Pojo.DataMovie;
import com.example.movie_provider.R;
import com.sackcentury.shinebuttonlib.ShineButton;

public class DetailMovieActivitys extends AppCompatActivity {
    private ImageView imgDetail;
    private TextView tvTitle,tvReleased, tvSynopsis;
    private ShineButton btnLike;
    private DataMovie dataMovie = null;
    private boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        getSupportActionBar().setTitle("Movie Info");

        imgDetail = findViewById(R.id.imgDetail);
        tvTitle = findViewById(R.id.tvTitleDetail);
        tvReleased = findViewById(R.id.tvReleaseDetail);
        tvSynopsis = findViewById(R.id.tvSynopsis);
        btnLike = findViewById(R.id.btnLike);

        Uri uri = getIntent().getData();

        if (uri != null){
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            if (cursor != null){
                if (cursor.moveToFirst()){
                    dataMovie = new DataMovie(cursor);
                }else {
                    cursor.close();
                }
            }
        }

        if (dataMovie!=null){
            isUpdate = true;
            btnLike.setChecked(true);
        }

        tvTitle.setText(dataMovie.getTitle());
        tvReleased.setText(dataMovie.getRelease_date());
        tvSynopsis.setText(dataMovie.getOverview());

        Glide.with(imgDetail.getContext())
                .load("http://image.tmdb.org/t/p/w500"+dataMovie.getPoster_path())
                .into(imgDetail);

        btnLike.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                if (!checked){

                }
            }
        });

    }

}
