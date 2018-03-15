package com.example.android.popularmoviesstage2.loaders;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.popularmoviesstage2.pojo.Movie;
import com.example.android.popularmoviesstage2.utils.QueryUtils;
import com.example.android.popularmoviesstage2.utils.QueryUtils2;

import java.util.List;

/**
 * Created by emkayx on 30-10-2017.
 */

public class MoviePosterLoader extends AsyncTaskLoader<List<Movie>> {
    private String mUrl;
    private LoaderManager loaderManager;
    private Context context;
    private String sort_by;
    public MoviePosterLoader(Context context, String mUrl, LoaderManager loaderManager, String sort_by) {

        super(context);
        this.context=context;
        this.mUrl = mUrl;
        this.loaderManager=loaderManager;
        this.sort_by= sort_by;
    }

    @Override
    public List<Movie> loadInBackground() {
        List<Movie> movieList;
        if(!sort_by.equals("favorite")){
         movieList = QueryUtils.fetchMovieData(mUrl,context);

        // added changes

        }
        else{
           movieList= QueryUtils2.fetchFavoriteMovies();
        }
        return movieList;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
