package com.example.android.popularmoviesstage2;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.popularmoviesstage2.pojo.Movie;
import com.example.android.popularmoviesstage2.utils.QueryUtils;

import java.util.List;

/**
 * Created by emkayx on 30-10-2017.
 */

public class MoviePosterLoader extends AsyncTaskLoader<List<Movie>> {
    private String mUrl;
    public MoviePosterLoader(Context context,String mUrl) {

        super(context);
        this.mUrl = mUrl;
    }

    @Override
    public List<Movie> loadInBackground() {

        List<Movie> movieList = QueryUtils.fetchMovieData(mUrl);

        // added changes

        return movieList;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
