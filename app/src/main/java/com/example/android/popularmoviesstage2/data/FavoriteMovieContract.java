package com.example.android.popularmoviesstage2.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by emkayx on 18-12-2017.
 */

public class FavoriteMovieContract {

    private FavoriteMovieContract(){}
    public static final String CONTENT_AUTHORITY = "com.example.android.popularmoviesstage2.data.FavoriteMovieProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_MOVIE= "favoritemovies";
    public static final class FavoriteMovieEntry implements BaseColumns{
     public static final Uri CONTENT_URI= Uri.withAppendedPath(BASE_CONTENT_URI,PATH_MOVIE);
        public static final String TABLE_NAME = "favoritemovies";
        public static final String MOVIE_ID = "_id";
        public static final String MOVIE_TITLE = "originaltitle";
        public static final String MOVIE_SYNOPSIS ="plotsynopsis";
        public static final String MOVIE_RELEASE_DATE ="releasedate";
        public static final String MOVIE_RATING = "movierating";
        public static final String MOVIE_POSTER_PATH="posterpath";
    }
}
