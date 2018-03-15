package com.example.android.popularmoviesstage2;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmoviesstage2.loaders.FavoriteMoviePosterLoader;
import com.example.android.popularmoviesstage2.pojo.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieActivity extends AppCompatActivity implements MovieListAdapter.PosterClickListener,LoaderManager.LoaderCallbacks<List<Movie>> {
    ProgressBar fMovieProgressBar;
    private TextView emptyTextView;
    private ArrayList<Movie> movieList;
    private MovieListAdapter movieListAdapter;
    private RecyclerView favMovieListRecyclerView;
    private static final int FAV_MOVIEPOSTER_LOADER_INT=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);
       fMovieProgressBar = (ProgressBar) findViewById(R.id.movie_progressbar);
       emptyTextView= (TextView) findViewById(R.id.empty_view);
       favMovieListRecyclerView= (RecyclerView) findViewById(R.id.fav_movie_list_rv);


        favMovieListRecyclerView.setHasFixedSize(true);

        emptyTextView = (TextView) findViewById(R.id.empty_view);
        // set the layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,getSpan());
        favMovieListRecyclerView.setLayoutManager(gridLayoutManager);
        //set the adapter
        movieListAdapter = new MovieListAdapter(new ArrayList<Movie>(),this);
        favMovieListRecyclerView.setAdapter(movieListAdapter);


    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        fMovieProgressBar.setVisibility(View.VISIBLE);

        return new FavoriteMoviePosterLoader(this);

    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader,List<Movie> movies) {
        fMovieProgressBar.setVisibility(View.GONE);
        emptyTextView.setText(getString(R.string.no_movies_found));

        movieList=(ArrayList<Movie>) movies;
        movieListAdapter.clear();

        if (movieList !=null || !movieList.isEmpty()) {

            emptyTextView.setVisibility(View.GONE);
            movieListAdapter.setMovieList(movieList);

        }else{
            favMovieListRecyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
            emptyTextView.setText("no favorites saved yet");

        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }

    @Override
    public void onResume() {
        super.onResume();

        LoaderManager loaderManager = getLoaderManager();

            loaderManager.restartLoader(FAV_MOVIEPOSTER_LOADER_INT, null, this);


    }


    @Override
    public void onMoviePosterClick(int clickedItemIndex) {
        Intent detailsActivityIntent= new Intent(FavoriteMovieActivity.this,MovieDetailActivity.class);
        if(!movieList.isEmpty()){
            Movie clickedMovie = movieList.get(clickedItemIndex);
            detailsActivityIntent.putExtra("clickedMovie",clickedMovie);
            startActivity(detailsActivityIntent);

        }
        else{
            emptyTextView.setText("unable to retrieve data");
            Log.i("FavoriteMovieActivity","movieList not updated");
        }

    }
    public int getSpan(){
        Display display = this.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpWidth  = outMetrics.widthPixels / density;
        int columns = Math.round(dpWidth/450);
        return columns;
    }
}
