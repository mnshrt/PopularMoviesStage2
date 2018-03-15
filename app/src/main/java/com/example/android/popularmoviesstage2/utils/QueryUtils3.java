package com.example.android.popularmoviesstage2.utils;

import android.database.Cursor;

import com.example.android.popularmoviesstage2.pojo.Movie;

import java.util.List;
import static com.example.android.popularmoviesstage2.data.FavoriteMovieContract.FavoriteMovieEntry.*;

/**
 * Created by emkayx on 03-03-2018.
 */

public class QueryUtils3 {

    public static List<Movie> fetchFavoriteMovieData(Cursor cursor) {

        String movieTitle, movieReleaseDate, movieSynopsis,moviePosterPath;
        int movieId;
        double movieRating;
        List<Movie> favoriteMovieList = null;

        if(cursor.getCount()!=0 && cursor.moveToFirst()){
            while(cursor.moveToNext()){
               movieId=  cursor.getInt(cursor.getColumnIndex(MOVIE_ID));
               movieTitle=cursor.getString(cursor.getColumnIndex(MOVIE_TITLE));
               movieSynopsis=cursor.getString(cursor.getColumnIndex(MOVIE_SYNOPSIS));
               moviePosterPath= cursor.getString(cursor.getColumnIndex(MOVIE_POSTER_PATH));
               movieReleaseDate= cursor.getString(cursor.getColumnIndex(MOVIE_RELEASE_DATE));
               movieRating= cursor.getDouble(cursor.getColumnIndex(MOVIE_RATING));


               Movie movie = new Movie(movieTitle,moviePosterPath,movieReleaseDate,movieSynopsis,movieRating,movieId);
               favoriteMovieList.add(movie);
            }

        }
        return favoriteMovieList;


    }
}
