package com.asykurkhamid.submission.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.asykurkhamid.submission.Database.MovieHelper;

import java.sql.SQLException;

import static com.asykurkhamid.submission.Database.DBContract.AUTHORITY;
import static com.asykurkhamid.submission.Database.DBContract.MovieColumn.CONTENT_URI;
import static com.asykurkhamid.submission.Database.DBContract.MovieColumn.TABLE_NAME;

public class MovieProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MovieHelper movieHelper;

    static {
        //content://com.com.asykurkhamid.submission/movie
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, MOVIE);
        //content://com.com.asykurkhamid.submission/movie/id
        uriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        try {
            movieHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                cursor = movieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long added;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                added = movieHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }
        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleted;
        switch (uriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int updated;
        switch (uriMatcher.match(uri)) {
            case MOVIE_ID:
                updated = movieHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }
        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
