package com.example.android.popularmoviesstage2;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmoviesstage2.loaders.MoviePosterLoader;
import com.example.android.popularmoviesstage2.pojo.Movie;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.popularmoviesstage2.R.id.movie_progressbar;

public class MovieActivity extends AppCompatActivity implements MovieListAdapter.PosterClickListener,LoaderManager.LoaderCallbacks<List<Movie>> {

    private static final String TMDB_REQUEST_URL="http://api.themoviedb.org/3/movie";
    private static final String API_KEY="e58cd6903218bfa3ff6cfe1c12977fc9";


    private MovieListAdapter movieListAdapter;

    private ArrayList<Movie> movieList;
    private static final int MOVIEPOSTER_LOADER_INT=1;

    private TextView emptyTextView;
    private ProgressBar movieProgressBar;

    private RecyclerView movieListRecyclerView;
    private String sort_by;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieProgressBar = (ProgressBar) findViewById(movie_progressbar);
        movieListRecyclerView = (RecyclerView) findViewById(R.id.movie_list_rv);
        movieListRecyclerView.setHasFixedSize(true);

        emptyTextView = (TextView) findViewById(R.id.empty_view);
        // set the layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,getSpan());
        movieListRecyclerView.setLayoutManager(gridLayoutManager);
        //set the adapter
        movieListAdapter = new MovieListAdapter(new ArrayList<Movie>(),this);
        movieListRecyclerView.setAdapter(movieListAdapter);



        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        sort_by = sharedPrefs.getString(
                "listPref",
                getString(R.string.settings_order_by_default_value));
// TODO: add code here to retrieve based on preference and also add a condition for cursor loader

        //network call based on sortby, if favorite get from Json
        if(!sort_by.equals("favorite")) {
            if (connectionChecker(this)) {
                LoaderManager loaderManager = getLoaderManager();

                //initialise the loader passing in the parameters ,pass in the id defined ,for bundle pass in null and for loader
                //callbacks send the activity context


                loaderManager.initLoader(MOVIEPOSTER_LOADER_INT, null, this);
            } else {
                movieProgressBar.setVisibility(View.GONE);
                emptyTextView.setText(R.string.no_internet_connection);
            }
        }else{

        }
    }

    // creating the settings option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
// onOptions selected implementation
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }else if(id == R.id.action_favorites){
            Intent favoritesIntent= new Intent (this,FavoriteMovieActivity.class);
            startActivity(favoritesIntent);
        }
        return super.onOptionsItemSelected(item);
    }
    // after getting back from settings activity if preference is change reset the loader
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        sort_by = sharedPrefs.getString(
                "listPref",
                getString(R.string.settings_order_by_popularity_value));
        LoaderManager loaderManager = getLoaderManager();
        if(connectionChecker(getApplicationContext()) && !sort_by.equals(getString(R.string.settings_order_by_popularity_value))) {
            loaderManager.restartLoader(MOVIEPOSTER_LOADER_INT, null, this);
        }else{return;}

    }

    //method to check for the connectivity
    public boolean connectionChecker(Context context){

        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;

    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {

        movieProgressBar.setVisibility(View.VISIBLE);


            Uri baseUri = Uri.parse(TMDB_REQUEST_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();
            uriBuilder.appendPath(sort_by);



            uriBuilder.appendQueryParameter("api_key", API_KEY);

            return new MoviePosterLoader(this, uriBuilder.toString(), getLoaderManager(),sort_by);


    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        movieProgressBar.setVisibility(View.GONE);
        emptyTextView.setText(getString(R.string.no_movies_found));

        movieList=(ArrayList<Movie>) movies;
        movieListAdapter.clear();

        if (movieList !=null || !movieList.isEmpty()) {

            emptyTextView.setVisibility(View.GONE);
            movieListAdapter.setMovieList(movieList);

        }else{
            movieListRecyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }

    @Override
    public void onMoviePosterClick(int position) {
       // Intent detailsActivityIntent= new Intent(MovieActivity.this,MovieDetailActivity.class);
      //  Toast.makeText(this, position+" clicked", Toast.LENGTH_SHORT).show();
        Intent detailsActivityIntent= new Intent(MovieActivity.this,MovieDetailActivity.class);
        if(!movieList.isEmpty()){
            Movie clickedMovie = movieList.get(position);
            detailsActivityIntent.putExtra("clickedMovie",clickedMovie);
            startActivity(detailsActivityIntent);

        }
        else{
            emptyTextView.setText(getString(R.string.no_internet_connection));
            Log.i("MovieActivity","movieList not updated");
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
