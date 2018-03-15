package com.example.android.popularmoviesstage2.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.example.android.popularmoviesstage2.data.FavoriteMovieContract.*;

/**
 * Created by emkayx on 01-03-2018.
 */

public class FavoriteMovieProvider extends ContentProvider {
    private FavoriteMovieDbHelper favoriteMovieDbHelper;
private static final String TAG = FavoriteMovieProvider.class.getSimpleName();
    private static final int MOVIES=1;
    private static final int MOVIE_ID=2;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{

        uriMatcher.addURI(CONTENT_AUTHORITY,PATH_MOVIE,MOVIES);// the whole table
        uriMatcher.addURI(CONTENT_AUTHORITY,PATH_MOVIE+"/#",MOVIE_ID);// to check movie is in favorites table
    }
    @Override
    public boolean onCreate() {
        favoriteMovieDbHelper= new FavoriteMovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {


        switch (uriMatcher.match(uri)){
            case MOVIES:
                return insertRecord(uri,contentValues,FavoriteMovieEntry.TABLE_NAME);

            case MOVIE_ID:
                break;
            default:
                throw new IllegalArgumentException(TAG +" insert unknown uri "+uri);
        }

        return null;
    }

    private Uri insertRecord(Uri uri, ContentValues contentValues, String tableName) {
        SQLiteDatabase sqLiteDatabase= favoriteMovieDbHelper.getWritableDatabase();
        long rowId= sqLiteDatabase.insert(tableName,null,contentValues);
        if(rowId==-1){
            Log.e(TAG, "insert error of uri " + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri,rowId);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
