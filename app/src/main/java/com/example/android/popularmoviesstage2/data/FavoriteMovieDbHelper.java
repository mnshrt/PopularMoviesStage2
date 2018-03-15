package com.example.android.popularmoviesstage2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.popularmoviesstage2.data.FavoriteMovieContract.FavoriteMovieEntry.MOVIE_ID;
import static com.example.android.popularmoviesstage2.data.FavoriteMovieContract.FavoriteMovieEntry.MOVIE_POSTER_PATH;
import static com.example.android.popularmoviesstage2.data.FavoriteMovieContract.FavoriteMovieEntry.MOVIE_RATING;
import static com.example.android.popularmoviesstage2.data.FavoriteMovieContract.FavoriteMovieEntry.MOVIE_RELEASE_DATE;
import static com.example.android.popularmoviesstage2.data.FavoriteMovieContract.FavoriteMovieEntry.MOVIE_SYNOPSIS;
import static com.example.android.popularmoviesstage2.data.FavoriteMovieContract.FavoriteMovieEntry.MOVIE_TITLE;
import static com.example.android.popularmoviesstage2.data.FavoriteMovieContract.FavoriteMovieEntry.TABLE_NAME;


public class FavoriteMovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favoritemovie.db";
    private static final int DATABASE_VERSION = 1;
    public FavoriteMovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
     final String SQL_CREATE_FAV_MOVIE_TABLE = "create table " + TABLE_NAME + "( "+
                                                MOVIE_ID+ " INTEGER PRIMARY KEY,"+
                                                MOVIE_TITLE + " TEXT, "+
                                                MOVIE_RATING+ " REAL NOT NULL, "+
                                                MOVIE_SYNOPSIS+" TEXT NOT NULL" +
                                                MOVIE_RELEASE_DATE+ " TEXT NOT NULL" +
                                                MOVIE_POSTER_PATH+" TEXT"+
             ");";

     sqLiteDatabase.execSQL(SQL_CREATE_FAV_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
     sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME );
     onCreate(sqLiteDatabase);
    }
}
