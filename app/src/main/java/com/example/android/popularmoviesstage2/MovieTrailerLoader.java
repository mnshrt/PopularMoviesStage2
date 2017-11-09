package com.example.android.popularmoviesstage2;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.popularmoviesstage2.pojo.Trailer;
import com.example.android.popularmoviesstage2.utils.QueryUtils1;

import java.util.List;

/**
 * Created by emkayx on 01-11-2017.
 */

public class MovieTrailerLoader extends AsyncTaskLoader<List<Trailer>> {
    private String mUrl;
    public MovieTrailerLoader(Context context, String mUrl) {

        super(context);
        this.mUrl=mUrl;
    }

    @Override
    public List<Trailer> loadInBackground() {
        List<Trailer> movieTrailerList = QueryUtils1.fetchMovieTrailerData(mUrl);
        return movieTrailerList;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
