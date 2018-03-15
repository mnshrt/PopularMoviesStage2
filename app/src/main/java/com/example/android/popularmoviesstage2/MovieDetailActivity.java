package com.example.android.popularmoviesstage2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesstage2.pojo.Movie;
import com.squareup.picasso.Picasso;

import static com.example.android.popularmoviesstage2.data.FavoriteMovieContract.FavoriteMovieEntry.CONTENT_URI;
import static com.example.android.popularmoviesstage2.data.FavoriteMovieContract.FavoriteMovieEntry.MOVIE_ID;
import static com.example.android.popularmoviesstage2.data.FavoriteMovieContract.FavoriteMovieEntry.MOVIE_POSTER_PATH;
import static com.example.android.popularmoviesstage2.data.FavoriteMovieContract.FavoriteMovieEntry.MOVIE_RATING;
import static com.example.android.popularmoviesstage2.data.FavoriteMovieContract.FavoriteMovieEntry.MOVIE_RELEASE_DATE;
import static com.example.android.popularmoviesstage2.data.FavoriteMovieContract.FavoriteMovieEntry.MOVIE_SYNOPSIS;
import static com.example.android.popularmoviesstage2.data.FavoriteMovieContract.FavoriteMovieEntry.MOVIE_TITLE;



public class MovieDetailActivity extends AppCompatActivity implements TrailerListAdapter.TrailerListItemClickListener{


    Movie clickedMovie=null;

private static final String TAG = MovieDetailActivity.class.getSimpleName();

        private Button favoriteButton;
        private TextView synopsisTextView,dateTextView,originalTitleTextView,ratingsTextView;
        private ImageView moviePosterImageView;
        private boolean favoriteCheck = false;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_movie_detail);


            // getting the movie object
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                if (extras.containsKey("clickedMovie")) {
                    clickedMovie = extras.getParcelable("clickedMovie");
                    //getParcelableExtra("clickedMovie");
                } else {
                    return;
                }
            }

            // getting the view references
            synopsisTextView = (TextView) findViewById(R.id.movie_synopsis_text_view);
            dateTextView = (TextView) findViewById(R.id.movie_release_date_text_view);
            originalTitleTextView = (TextView) findViewById(R.id.movie_original_title_text_view);
            ratingsTextView = (TextView) findViewById(R.id.movie_ratings_text_view);
            moviePosterImageView = (ImageView) findViewById(R.id.movie_poster_image_view);
            favoriteButton = (Button) findViewById(R.id.add_to_fav_button);
            // trailerCardView= (CardView) findViewById(R.id.trailers_card_view);
            //reviewCardView= (CardView) findViewById(R.id.reviews_card_view);
           // reviewListView = (ListView) findViewById(R.id.reviews_list_view);

            //trailerListView = (ListView) findViewById(R.id.trailers_list_view);

            //setting the text values for each view

            originalTitleTextView.setText(clickedMovie.getMovieOriginalTitle());
            dateTextView.setText(clickedMovie.getMovieDate());
            ratingsTextView.setText(Double.toString(clickedMovie.getMovieRating()));
            synopsisTextView.setText(clickedMovie.getMoviePlotSynopsis());

            //using picasso to load the image in the imageview
            Picasso.with(this).load("http://image.tmdb.org/t/p/w500/" + clickedMovie.getMoviePosterPath()).into(moviePosterImageView);

           /* if (clickedMovie.getTrailerList() != null) {
                trailerListAdapter = new TrailerListAdapter(this, clickedMovie.getTrailerList());

            } else {
                //  trailerCardView.setVisibility(View.GONE);
            }*/
          //  trailerCardView.setVisibility(View.GONE);
            //TODO checking for reviews only so set trailerlistview gone

         /*   if (clickedMovie.getReviewList() != null || clickedMovie.getReviewList().isEmpty()) {
                //reviewListAdapter = new ReviewListAdapter(this, new ArrayList<Review>());
                reviewListAdapter = new ReviewListAdapter(this, clickedMovie.getReviewList());
            } else {
                  reviewListView.setVisibility(View.GONE);
            }


            reviewListView.setAdapter(reviewListAdapter);*/
          //  trailerListView.setAdapter(trailerListAdapter);




 /*     @Override
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
    }*/
            // recycler view for the trailer
            RecyclerView trailerListRv = (RecyclerView) findViewById(R.id.rv_trailers);
            LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(this);
            trailerListRv.setLayoutManager(trailerLayoutManager);
            trailerListRv.setHasFixedSize(true);

            TrailerListAdapter trailerListAdapter;
            if (clickedMovie.getReviewList() != null || clickedMovie.getReviewList().isEmpty()) {
                //reviewListAdapter = new ReviewListAdapter(this, new ArrayList<Review>());

                Toast.makeText(this,""+clickedMovie.getTrailerList().size(), Toast.LENGTH_LONG).show();
                trailerListAdapter = new TrailerListAdapter(clickedMovie.getTrailerList().size(),clickedMovie.getTrailerList(),this);
                trailerListRv.setAdapter(trailerListAdapter);
            }
            else{
                trailerListRv.setVisibility(View.GONE);
            }
            //recycler view for the reviews
            RecyclerView reviewListRv = (RecyclerView) findViewById(R.id.rv_reviews);
            LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(this);
            reviewListRv.setLayoutManager(reviewLayoutManager);
            reviewListRv.setHasFixedSize(true);

            ReviewListAdapter reviewListAdapter;
            if (clickedMovie.getReviewList() != null || clickedMovie.getReviewList().isEmpty()) {
                //reviewListAdapter = new ReviewListAdapter(this, new ArrayList<Review>());

                Toast.makeText(this,""+clickedMovie.getReviewList().size(), Toast.LENGTH_LONG).show();
                reviewListAdapter = new ReviewListAdapter(clickedMovie.getReviewList().size(),clickedMovie.getReviewList());
                reviewListRv.setAdapter(reviewListAdapter);
            }
            else{
                reviewListRv.setVisibility(View.GONE);
            }

            //favorite checking using content provider
            favoriteCheck= favoriteEntryChecker();
            if(favoriteCheck){
                favoriteButton.setText(R.string.string_unfavorite);
            }else{
                favoriteButton.setText(R.string.string_favorite);
            }
            // on favorite button clicked

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(favoriteButton.getText().toString().equals(R.string.string_unfavorite)){
                        removeMovieFromFavorites();
                        favoriteButton.setText(R.string.string_favorite);

                    }
                    else{
                        setMovieAsFavorite();
                        favoriteButton.setText(R.string.string_unfavorite);

                    }

                    //favoriteButton.setText("Favorited");
                 //  favoriteButton.setBackground("#FF7F50");
                }
            });

        }
public boolean favoriteEntryChecker(){
            String selection = MOVIE_ID+" =?";
            String[] selectionArgs = {Integer.toString(clickedMovie.getMovieId())};
            Cursor cursor = getContentResolver().query(CONTENT_URI,null,selection,selectionArgs,null);

            if(cursor.getCount()>0){
                return true;
            }
            else{
                return false;
            }
}

    @Override
    public void onListItemClick(int clickedItemIndex) {
        String videoKey = clickedMovie.getTrailerList().get(clickedItemIndex).getVideoId();

        Uri uri = Uri.parse("https://www.youtube.com/watch?v="+videoKey);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }

    private void setMovieAsFavorite(){

        ContentValues contentValues = new ContentValues();

        contentValues.put(MOVIE_ID,clickedMovie.getMovieId());
        contentValues.put(MOVIE_TITLE,clickedMovie.getMovieOriginalTitle());
        contentValues.put(MOVIE_RATING,clickedMovie.getMovieRating());
        contentValues.put(MOVIE_SYNOPSIS,clickedMovie.getMoviePlotSynopsis());
        contentValues.put(MOVIE_RELEASE_DATE,clickedMovie.getMovieDate());
        contentValues.put(MOVIE_POSTER_PATH,clickedMovie.getMoviePosterPath());

        Uri uriRowInserted = getContentResolver().insert(CONTENT_URI,contentValues);
        Log.i(TAG," Items inserted at "+uriRowInserted);

    }

    private void removeMovieFromFavorites(){

        String selection = MOVIE_ID+" =?";
        String[] selectionArgs={Integer.toString(clickedMovie.getMovieId())};
        int rowsDeleted= getContentResolver().delete(CONTENT_URI,selection,selectionArgs);
        Log.i(TAG, "Number of rows deleted: " + rowsDeleted);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return  true;
        }
        return super.onOptionsItemSelected(item);

    }
}

