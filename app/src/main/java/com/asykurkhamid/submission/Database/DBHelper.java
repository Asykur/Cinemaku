package com.asykurkhamid.submission.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "moviedb";
    private static final int DB_VERSION = 1;
    private static final String CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL)",
    DBContract.MovieColumn.TABLE_NAME,
    DBContract.MovieColumn._ID,
    DBContract.MovieColumn.TITLE,
    DBContract.MovieColumn.DATE_RELEASE,
    DBContract.MovieColumn.POSTER_PATH,
    DBContract.MovieColumn.SYNOPSIS,
    DBContract.MovieColumn.FAVORITE
    );

    public DBHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.MovieColumn.TABLE_NAME);
    }
}
