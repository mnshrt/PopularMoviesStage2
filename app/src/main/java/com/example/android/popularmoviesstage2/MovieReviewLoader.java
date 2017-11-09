package com.example.android.popularmoviesstage2;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.popularmoviesstage2.pojo.Review;
import com.example.android.popularmoviesstage2.utils.QueryUtils1;

import java.util.List;

/**
 * Created by emkayx on 01-11-2017.
 */

public class MovieReviewLoader extends AsyncTaskLoader<List<Review>> {
    private String mUrl;
    public MovieReviewLoader(Context context, String mUrl) {
        super(context);
        this.mUrl=mUrl;
    }

    @Override
    public List<Review> loadInBackground() {
        List<Review> movieReviewList = QueryUtils1.fetchMovieReviewData(mUrl);
        return movieReviewList;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
