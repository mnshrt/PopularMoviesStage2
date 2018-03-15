package com.example.android.popularmoviesstage2.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

import com.example.android.popularmoviesstage2.pojo.Movie;
import com.example.android.popularmoviesstage2.utils.QueryUtils3;

import java.util.List;

import static com.example.android.popularmoviesstage2.data.FavoriteMovieContract.FavoriteMovieEntry.CONTENT_URI;

/**
 * Created by emkayx on 03-03-2018.
 */

public class FavoriteMoviePosterLoader extends AsyncTaskLoader<List<Movie>> {

    private Context context;

    public FavoriteMoviePosterLoader(Context context) {

        super(context);
        this.context=context;

    }

    @Override
    public List<Movie> loadInBackground() {
       Cursor cursor= context.getContentResolver().query(CONTENT_URI,null,null,null,null);
       return QueryUtils3.fetchFavoriteMovieData(cursor);

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
