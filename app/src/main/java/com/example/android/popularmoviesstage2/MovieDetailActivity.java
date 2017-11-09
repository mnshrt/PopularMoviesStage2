package com.example.android.popularmoviesstage2;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.popularmoviesstage2.pojo.Movie;
import com.example.android.popularmoviesstage2.pojo.Review;
import com.example.android.popularmoviesstage2.pojo.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks{

    private static final String TMDB_REQUEST_URL="http://api.themoviedb.org/3/movie";
    private static final int LOADER_A_ID =1;
    private static final int LOADER_B_ID=2;
    private static final String TRAILER_PATH="videos";
    private static final String REVIEW_PATH = "reviews";
    private static final String API_KEY="";
    Movie clickedMovie=null;
    ArrayList<Review> reviewList;
    ArrayList<Trailer> trailerList;

    ListView reviewListView,trailerListView;
    private ReviewListAdapter reviewListAdapter;
    private TrailerListAdapter trailerListAdapter;


        private TextView synopsisTextView,dateTextView,originalTitleTextView,ratingsTextView;
        private ImageView moviePosterImageView;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_movie_detail);


            // Intent intent = getIntent();
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                if (extras.containsKey("clickedMovie")) {
                    clickedMovie = extras.getParcelable("clickedMovie");
                    //getParcelableExtra("clickedMovie");
                }
                else{
                    return;
                }
            }


            synopsisTextView= (TextView) findViewById(R.id.movie_synopsis_text_view);
            dateTextView= (TextView) findViewById(R.id.movie_release_date_text_view);
            originalTitleTextView= (TextView) findViewById(R.id.movie_original_title_text_view);
            ratingsTextView= (TextView) findViewById(R.id.movie_ratings_text_view);

            moviePosterImageView= (ImageView) findViewById(R.id.movie_poster_image_view);

            reviewListView = (ListView) findViewById(R.id.reviews_list_view);

            trailerListView= (ListView) findViewById(R.id.trailers_list_view);

            //fetching the movie details using the parceable extra

            originalTitleTextView.setText(clickedMovie.getMovieOriginalTitle());
            dateTextView.setText(clickedMovie.getMovieDate());
            ratingsTextView.setText(Double.toString(clickedMovie.getMovieRating()));
            synopsisTextView.setText(clickedMovie.getMoviePlotSynopsis());

            Picasso.with(this).load("http://image.tmdb.org/t/p/w500/"+clickedMovie.getMoviePosterPath()).into(moviePosterImageView);

            trailerListAdapter = new TrailerListAdapter(this, new ArrayList<Trailer>());
            reviewListAdapter = new ReviewListAdapter(this,new ArrayList<Review>());



            reviewListView.setAdapter(reviewListAdapter);
            trailerListView.setAdapter(trailerListAdapter);


            getLoaderManager().initLoader(LOADER_A_ID, null,this);
            getLoaderManager().initLoader(LOADER_B_ID, null, this);
        }



      @Override
      public Loader onCreateLoader(int id, Bundle args) {
          if(id==LOADER_A_ID){
              Uri baseUri = Uri.parse(TMDB_REQUEST_URL);
              Uri.Builder uriBuilder = baseUri.buildUpon();
              uriBuilder.appendPath(Integer.toString(clickedMovie.getMovieId()));
              uriBuilder.appendPath(TRAILER_PATH);
              uriBuilder.appendQueryParameter("api_key",API_KEY);
              return new MovieTrailerLoader(this,uriBuilder.toString());
          }
          else if(id==LOADER_B_ID){
              Uri baseUri = Uri.parse(TMDB_REQUEST_URL);
              Uri.Builder uriBuilder = baseUri.buildUpon();
              uriBuilder.appendPath(Integer.toString(clickedMovie.getMovieId()));
              uriBuilder.appendPath(REVIEW_PATH);
              uriBuilder.appendQueryParameter("api_key",API_KEY);
              return new MovieReviewLoader(this,uriBuilder.toString());
          }
          return null;
      }

    @Override
    public void onLoadFinished(Loader loader, Object o) {

        int id  = loader.getId();
         if(id== LOADER_A_ID){
             trailerList = (ArrayList<Trailer>) o;
             trailerListAdapter.clear();
             if(o!=null || ((ArrayList<Trailer>) o).isEmpty()){
                 trailerListAdapter.addAll(trailerList);
                 clickedMovie.setTrailerList(trailerList);
             }
         }else if(id == LOADER_B_ID){
             reviewList = (ArrayList<Review>) o;
             reviewListAdapter.clear();
             if(reviewList!=null || reviewList.isEmpty()){
                 reviewListAdapter.addAll(reviewList);
                 clickedMovie.setReviewList(reviewList);
             }
         }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        int id = loader.getId();
        if(id== LOADER_A_ID){
            trailerListAdapter.clear();
        }else if(id == LOADER_B_ID){
            reviewListAdapter.clear();
        }
    }


}

