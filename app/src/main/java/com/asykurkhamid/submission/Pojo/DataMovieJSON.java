package com.asykurkhamid.submission.Pojo;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.asykurkhamid.submission.Database.DBContract;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.asykurkhamid.submission.Database.DBContract.MovieColumn.FAVORITE;
import static com.asykurkhamid.submission.Database.DBContract.getColumnInt;
import static com.asykurkhamid.submission.Database.DBContract.getColumnString;

public class DataMovieJSON implements Parcelable {
    String vote_count;
    int id;
    boolean video;
    double vote_average;
    String title;
    double popularity;
    String poster_path;
    String original_language;
    String original_title;
    ArrayList<Integer> genre_ids;
    String backdrop_path;
    boolean adult;
    String overview;
    String release_date;

    public DataMovieJSON() {
    }

    public DataMovieJSON(Parcel in) {
        vote_count = in.readString();
        id = in.readInt();
        video = in.readByte() != 0;
        vote_average = in.readDouble();
        title = in.readString();
        popularity = in.readDouble();
        poster_path = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        backdrop_path = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        release_date = in.readString();
    }

    public static final Creator<DataMovieJSON> CREATOR = new Creator<DataMovieJSON>() {
        @Override
        public DataMovieJSON createFromParcel(Parcel in) {
            return new DataMovieJSON(in);
        }

        @Override
        public DataMovieJSON[] newArray(int size) {
            return new DataMovieJSON[size];
        }
    };

    public DataMovieJSON(Cursor cursor) {
        this.id = getColumnInt(cursor,_ID);
        this.poster_path = getColumnString(cursor,DBContract.MovieColumn.POSTER_PATH);
        this.title = getColumnString(cursor,DBContract.MovieColumn.TITLE);
        this.overview = getColumnString(cursor,DBContract.MovieColumn.SYNOPSIS);
        this.release_date = getColumnString(cursor,DBContract.MovieColumn.DATE_RELEASE);
        this.vote_count = getColumnString(cursor,FAVORITE);
    }

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

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
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
        parcel.writeByte((byte) (video ? 1 : 0));
        parcel.writeDouble(vote_average);
        parcel.writeString(title);
        parcel.writeDouble(popularity);
        parcel.writeString(poster_path);
        parcel.writeString(original_language);
        parcel.writeString(original_title);
        parcel.writeString(backdrop_path);
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeString(overview);
        parcel.writeString(release_date);
    }

    @Override
    public String toString() {
        return "DataMovieJSON{" +
                "vote_count='" + vote_count + '\'' +
                ", id=" + id +
                ", video=" + video +
                ", vote_average=" + vote_average +
                ", title='" + title + '\'' +
                ", popularity=" + popularity +
                ", poster_path='" + poster_path + '\'' +
                ", original_language='" + original_language + '\'' +
                ", original_title='" + original_title + '\'' +
                ", genre_ids=" + genre_ids +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", adult=" + adult +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                '}';
    }
}
