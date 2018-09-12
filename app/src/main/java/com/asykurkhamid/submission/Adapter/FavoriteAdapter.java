package com.asykurkhamid.submission.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asykurkhamid.submission.Activity.DetailMovieActivity;
import com.asykurkhamid.submission.Pojo.DataMovieJSON;
import com.asykurkhamid.submission.R;
import com.bumptech.glide.Glide;

import static com.asykurkhamid.submission.Database.DBContract.MovieColumn.CONTENT_URI;

public class FavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Cursor listMovie;
    private Activity activity;

    public FavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListMovie(Cursor cursor){
        this.listMovie = cursor;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final DataMovieJSON datas = getItem(position);
        VH vh = (VH) holder;
        vh.title.setText(datas.getTitle());
        vh.release_date.setText(datas.getRelease_date());
        vh.vote_count.setText(datas.getVote_count());

        Glide.with(this.activity)
                .load("http://image.tmdb.org/t/p/w185" + datas.getPoster_path())
                .into(vh.image);

        vh.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailMovieActivity.class);
//                intent.putExtra("datamovieFav", datas);
                Uri uri = Uri.parse(CONTENT_URI + "/" + datas.getId());
                intent.setData(uri);
                v.getContext().startActivity(intent);
            }
        });

    }

    private DataMovieJSON getItem(int position) {
        if (!listMovie.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new DataMovieJSON(listMovie);
    }

    @Override
    public int getItemCount() {
        if (listMovie == null){
            return 0;
        }else {
            return listMovie.getCount();
        }
    }

    public class VH extends RecyclerView.ViewHolder{

        private TextView title,release_date,vote_count;
        private ImageView image;


        public VH(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvTitleToolbar);
            image = itemView.findViewById(R.id.imgMovie);
            vote_count = itemView.findViewById(R.id.tvVoted);
            release_date = itemView.findViewById(R.id.tvRelease);
        }
    }
}
