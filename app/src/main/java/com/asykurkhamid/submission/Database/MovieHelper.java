package com.asykurkhamid.submission.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.asykurkhamid.submission.Pojo.DataMovieJSON;

import java.sql.SQLException;
import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.asykurkhamid.submission.Database.DBContract.MovieColumn.DATE_RELEASE;
import static com.asykurkhamid.submission.Database.DBContract.MovieColumn.FAVORITE;
import static com.asykurkhamid.submission.Database.DBContract.MovieColumn.POSTER_PATH;
import static com.asykurkhamid.submission.Database.DBContract.MovieColumn.SYNOPSIS;
import static com.asykurkhamid.submission.Database.DBContract.MovieColumn.TABLE_NAME;
import static com.asykurkhamid.submission.Database.DBContract.MovieColumn.TITLE;

public class MovieHelper {

    private static String DB_TABLE = TABLE_NAME;
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException{
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    /*
    ===============================
      METHOD UNTUK QUERY SQLITE
    ===============================
    */

    // untuk ambil semua data
    public ArrayList<DataMovieJSON> query(){
        ArrayList<DataMovieJSON> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DB_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + "DESC",
                null);
        cursor.moveToFirst();
        DataMovieJSON datas;
        if (cursor.getCount() > 0){
            do {
                datas = new DataMovieJSON();
                datas.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                datas.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                datas.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(SYNOPSIS)));
                datas.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                datas.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(DATE_RELEASE)));
                datas.setVote_count(cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE)));
                arrayList.add(datas);
                cursor.moveToNext();

            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    //untuk insert
    public long insert(DataMovieJSON datas){
        ContentValues values = new ContentValues();
        values.put(TITLE, datas.getTitle());
        values.put(POSTER_PATH, datas.getPoster_path());
        values.put(DATE_RELEASE, datas.getRelease_date());
        values.put(SYNOPSIS, datas.getOverview());
        values.put(FAVORITE, datas.getVote_count());
        return database.insert(DB_TABLE,null,values);
    }

    //untuk update
    public int update(DataMovieJSON datas){
        ContentValues values = new ContentValues();
        values.put(TITLE, datas.getTitle());
        values.put(POSTER_PATH, datas.getPoster_path());
        values.put(DATE_RELEASE, datas.getRelease_date());
        values.put(SYNOPSIS, datas.getOverview());
        values.put(FAVORITE, datas.getVote_count());
        return database.update(DB_TABLE,values,_ID+"= "+datas.getId()+"'",null);
    }

    //untuk delete
    public  int delete(int id){
        return database.delete(DB_TABLE,_ID+"= "+id+"'",null);
    }

    /*
    ======================================================
    METHOD UNTUK CONTENT PROVIDER DGN NILAI BALIK CURSOR
    ======================================================
    */

    //Ambil data dari provider berdasarkan param ID
    public Cursor queryByIdProvider(String id){
        return database.query(DB_TABLE,null,
                _ID+" = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    //Ambil semua data dlm provider
    public Cursor queryProvider(){
        return database.query(DB_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID+" DESC");
    }

    //Insert data dlm provider
    public long insertProvider (ContentValues values){
        return database.insert(DB_TABLE,null,values);
    }

    //Updata data dlm provider
    public int updateProvider(String id, ContentValues values){
        return database.update(DB_TABLE,values,_ID+" = ?", new String[]{id});
    }

    //Delete data dalam provider
    public int deleteProvider(String id){
        return database.delete(DB_TABLE,_ID+" = ?",new String[]{id});
    }
}
