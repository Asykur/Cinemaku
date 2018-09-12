package com.asykurkhamid.submission.Activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.asykurkhamid.submission.Database.MovieHelper;
import com.asykurkhamid.submission.ParentClass;
import com.asykurkhamid.submission.Pojo.DataMovieJSON;
import com.asykurkhamid.submission.R;
import com.bumptech.glide.Glide;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.sql.SQLException;

import static com.asykurkhamid.submission.Database.DBContract.MovieColumn.CONTENT_URI;
import static com.asykurkhamid.submission.Database.DBContract.MovieColumn.DATE_RELEASE;
import static com.asykurkhamid.submission.Database.DBContract.MovieColumn.FAVORITE;
import static com.asykurkhamid.submission.Database.DBContract.MovieColumn.POSTER_PATH;
import static com.asykurkhamid.submission.Database.DBContract.MovieColumn.SYNOPSIS;
import static com.asykurkhamid.submission.Database.DBContract.MovieColumn.TITLE;

public class DetailMovieActivity extends ParentClass {
    DataMovieJSON data = null;
    DataMovieJSON dataFav = null;
    private ImageView imgDetail;
    private TextView tvTitle,tvReleased, tvSynopsis;
    private ShineButton btnLike;
    private Toolbar tbDetail;
    private ScrollView scPage;
    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;
    public static int REQUEST_UPDATE = 200;
    public static int RESULT_UPDATE = 201;
    public static int RESULT_DELETE = 301;
    private DataMovieJSON datas;
    private MovieHelper movieHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        data = getIntent().getParcelableExtra("datamovie");
        dataFav = getIntent().getParcelableExtra("datamovieFav");

        btnLike = findViewById(R.id.btnLike);
        btnLike.init(DetailMovieActivity.this);

        setDefaultToolbar(true, "Movie Info");
        imgDetail = findViewById(R.id.imgDetail);
        tvTitle = findViewById(R.id.tvTitleDetail);
        scPage = findViewById(R.id.scPage);
        tvReleased = findViewById(R.id.tvReleaseDetail);
        tvSynopsis = findViewById(R.id.tvSynopsis);
        tbDetail = findViewById(R.id.tbDetailPage);
        tbDetail.setBackgroundColor(ContextCompat.getColor(DetailMovieActivity.this, R.color.dark_navy_trans));

        movieHelper = new MovieHelper(this);
        try {
            movieHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final Uri uri = getIntent().getData();
        if (uri != null){
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            if (cursor != null){
                if (cursor.moveToFirst()){
                    datas = new DataMovieJSON(cursor);
                }
                cursor.close();
            }
        }

        if (datas != null){
            btnLike.setChecked(true);
            assignDataFromFavorite();
        }else {
            assignDataFromFragment();
        }


      // fungsi onClick btn favorite
        btnLike.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean isChecked) {
                //
                if (isChecked == true){
                    ContentValues values = new ContentValues();
                    values.put(TITLE,data.getTitle());
                    values.put(POSTER_PATH,data.getPoster_path());
                    values.put(DATE_RELEASE,data.getRelease_date());
                    values.put(SYNOPSIS,data.getOverview());
                    values.put(FAVORITE,data.getVote_count());

                    getContentResolver().insert(CONTENT_URI, values);
                    setResult(RESULT_ADD);
                    Toast.makeText(DetailMovieActivity.this, "Added to Favorite", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (uri == null){
                        btnLike.setChecked(true);
                    }else {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(DetailMovieActivity.this);
                        builder.setTitle("Remove Favorite Movie");
                        builder.setMessage("Are you sure to remove your favorite movie?");
                        builder.setCancelable(false);
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getContentResolver().delete(getIntent().getData(), null, null);
                                setResult(RESULT_DELETE, null);
                                Toast.makeText(DetailMovieActivity.this, "Remove from Favorite", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                btnLike.setChecked(true);
                            }
                        });
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            }
        });

    }

    private void assignDataFromFavorite() {
        tvTitle.setText(datas.getTitle());
        tvReleased.setText(datas.getRelease_date());
        tvSynopsis.setText(datas.getOverview());

        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w500"+datas.getPoster_path())
                .into(imgDetail);
    }

    private void assignDataFromFragment() {
        tvTitle.setText(data.getTitle());
        tvReleased.setText(data.getRelease_date());
        tvSynopsis.setText(data.getOverview());

        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w500"+data.getPoster_path())
                .into(imgDetail);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (movieHelper !=null){
            movieHelper.close();
        }
    }
}
