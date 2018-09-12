package com.example.movie_provider.Pojo;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.example.movie_provider.Database.DBContract.MovieColumn.FAVORITE;
import static com.example.movie_provider.Database.DBContract.MovieColumn.POSTER_PATH;
import static com.example.movie_provider.Database.DBContract.MovieColumn.DATE_RELEASE;
import static com.example.movie_provider.Database.DBContract.MovieColumn.SYNOPSIS;
import static com.example.movie_provider.Database.DBContract.MovieColumn.TITLE;
import static com.example.movie_provider.Database.DBContract.getColumnInt;
import static com.example.movie_provider.Database.DBContract.getColumnString;

public class DataMovie implements Parcelable{
    String vote_count;
    int id;
    String title;
    String poster_path;
    String overview;
    String release_date;

    public DataMovie() {
    }

    public DataMovie(Cursor cursor) {
        this.id = getColumnInt(cursor,_ID);
        this.title = getColumnString(cursor,TITLE);
        this.poster_path = getColumnString(cursor,POSTER_PATH);
        this.overview = getColumnString(cursor,SYNOPSIS);
        this.release_date = getColumnString(cursor, DATE_RELEASE);
        this.vote_count = getColumnString(cursor,FAVORITE);
    }

    protected DataMovie(Parcel in) {
        vote_count = in.readString();
        id = in.readInt();
        title = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
    }

    public static final Creator<DataMovie> CREATOR = new Creator<DataMovie>() {
        @Override
        public DataMovie createFromParcel(Parcel in) {
            return new DataMovie(in);
        }

        @Override
        public DataMovie[] newArray(int size) {
            return new DataMovie[size];
        }
    };

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(vote_count);
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeString(overview);
        parcel.writeString(release_date);
    }
}
