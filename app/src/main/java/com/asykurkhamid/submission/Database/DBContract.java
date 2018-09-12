package com.asykurkhamid.submission.Database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DBContract {

    public static final String AUTHORITY = "com.asykurkhamid.submission";
    public static final String SCHEME = "content";

    public DBContract() {
    }

    public static final class MovieColumn implements BaseColumns{
        public static String TABLE_NAME = "favorite";
//        public static String TABLE_FAVORITE = "favorite";

        public static String POSTER_PATH = "poster";
        public static String TITLE = "title";
        public static String DATE_RELEASE = "release";
        public static String SYNOPSIS = "description";
        public static String FAVORITE = "favorite";

        //Base content yg digunakan utk akses content provider
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

    //Akses data pada cursor dgn param String
    public static String getColumnString(Cursor cursor,String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    //Akses data pada cursor dgn param Integer
    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    //Akses data pada cursor dgn param Long
    public static long getColumnLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

}
