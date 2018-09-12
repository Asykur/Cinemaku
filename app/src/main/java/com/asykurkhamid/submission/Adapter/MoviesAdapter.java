package com.asykurkhamid.submission.Adapter;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<DataMovieJSON> data;
    Context mContext;

    public MoviesAdapter(Context context, ArrayList<DataMovieJSON> data) {
        this.data = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final DataMovieJSON movieData = data.get(position);
        VH vh = (VH)holder;

        vh.title.setText(movieData.getTitle());
        vh.vote_count.setText(String.valueOf(movieData.getVote_count()));
        vh.release_date.setText(movieData.getRelease_date());

        Glide.with(this.mContext)
                .load("http://image.tmdb.org/t/p/w185" + movieData.getPoster_path())
                .into(vh.image);

        vh.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), DetailMovieActivity.class);
                intent.putExtra("datamovie", movieData);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class VH extends RecyclerView.ViewHolder{

        private TextView title,genre,release_date,vote_count;
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
