package com.example.movie_provider.Database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DBContract {

    public static final String AUTHORITY = "com.asykurkhamid.submission";
    public static final String SCHEME = "content";


    public static final class MovieColumn implements BaseColumns{
        public static String TABLE_NAME = "favorite";

        public static String POSTER_PATH = "poster";
        public static String TITLE = "title";
        public static String DATE_RELEASE = "release";
        public static String SYNOPSIS = "description";
        public static String FAVORITE = "favorite";


        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

}
