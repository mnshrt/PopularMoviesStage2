package com.example.android.popularmoviesstage2.utils;

import android.app.LoaderManager;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.android.popularmoviesstage2.pojo.Movie;
import com.example.android.popularmoviesstage2.pojo.Review;
import com.example.android.popularmoviesstage2.pojo.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emkayx on 30-10-2017.
 */

public class QueryUtils{
    private static final String TMDB_REQUEST_URL="http://api.themoviedb.org/3/movie";
    private static final int LOADER_A_ID =1;
    private static final int LOADER_B_ID=2;
    private static final String TRAILER_PATH="videos";
    private static final String REVIEW_PATH = "reviews";
    private static final String API_KEY="e58cd6903218bfa3ff6cfe1c12977fc9";
    Movie clickedMovie=null;
    ArrayList<Review> reviewList;
    ArrayList<Trailer> trailerList;
    private static Context mContext;
    private static LoaderManager mloaderManager;
    private QueryUtils(){}
        private static final String LOG_TAG =QueryUtils.class.getName();

        public static List<Movie> fetchMovieData(String requestUrl, Context context){

            mContext=context;


            // Create URL object
            URL url = createUrl(requestUrl);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = null;
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem making the HTTP request.", e);
            }

            // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
            List<Movie> movies= extractFeatureFromJson(jsonResponse);

            // Return the list of {@link Earthquake}s
            return movies;



        }

        private static URL createUrl(String mUrl) {
            URL url = null;
            try {
                url = new URL(mUrl);
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Problem building the URL ", e);
            }

            return url;
        }

        private static String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";


            if (url == null) {
                return jsonResponse;
            }
            HttpURLConnection urlConnection;
            InputStream inputStream;

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();

                if (urlConnection.getResponseCode() == 200) {

                    inputStream = urlConnection.getInputStream();

                    jsonResponse = readFromStream(inputStream);


                }else{
                    jsonResponse=null;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return jsonResponse;
        }
        private static String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                String line = bufferedReader.readLine();
                while (line != null) {
                    output.append(line);
                    line = bufferedReader.readLine();
                }
            }
            return output.toString();
        }
        private static List<Movie> extractFeatureFromJson(String movieJson) {

            if(TextUtils.isEmpty(movieJson)){
                return null;
            }
            // Create an empty arraylist
            List<Movie> movies = new ArrayList<>();


            try {

                // Parse the response given by the SAMPLE_JSON_RESPONSE string and
                // build up a list of Earthquake objects with the corresponding data.
                JSONObject root = new JSONObject(movieJson);
                JSONArray resultsArray = root.getJSONArray("results");
//
                for (int i=0;i< resultsArray.length();i++){
                    JSONObject currentObject = resultsArray.getJSONObject(i);

                    String moviePosterUrl= currentObject.getString("poster_path");

                    String movieTitle = currentObject.getString("original_title");

                    String moviePlotSynopsis = currentObject.getString("overview");

                    String movieReleaseDate = currentObject.getString("release_date");

                    double movieRatings = currentObject.getDouble("vote_average");

                    int movieId = currentObject.getInt("id");

                    // fetch the review and trailer lists

                    List<Review> reviewList = getReviewList(Integer.toString(movieId));


                    List<Trailer> trailerList= getTrailerList(Integer.toString(movieId));



                   // Movie movie = new Movie(movieTitle,moviePosterUrl,movieReleaseDate,moviePlotSynopsis,movieRatings,movieId);

                      Movie movie = new Movie(movieTitle,moviePosterUrl,movieReleaseDate,moviePlotSynopsis,movieRatings,movieId,reviewList,trailerList);


                    movies.add(movie);

                }

            } catch (JSONException e) {

                Log.e(LOG_TAG, "Problem parsing the movie JSON results", e);
            }

            // Return the list of movies
            return movies;
        }

        public static  List<Review> getReviewList(String id){
            Uri baseUri = Uri.parse(TMDB_REQUEST_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();
            uriBuilder.appendPath(id);
            uriBuilder.appendPath(REVIEW_PATH);
            uriBuilder.appendQueryParameter("api_key",API_KEY);
            List<Review> movieReviewList = QueryUtils1.fetchMovieReviewData(uriBuilder.toString());

            return movieReviewList;
        }

        public static List<Trailer> getTrailerList(String id){

            Uri baseUri = Uri.parse(TMDB_REQUEST_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();
            uriBuilder.appendPath(id);
            uriBuilder.appendPath(TRAILER_PATH);
            uriBuilder.appendQueryParameter("api_key",API_KEY);
            List<Trailer> movieTrailerList = QueryUtils1.fetchMovieTrailerData(uriBuilder.toString());
            return movieTrailerList;
        }

/*    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        if(id==LOADER_A_ID){
            Uri baseUri = Uri.parse(TMDB_REQUEST_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();
            uriBuilder.appendPath(Integer.toString(clickedMovie.getMovieId()));
            uriBuilder.appendPath(TRAILER_PATH);
            uriBuilder.appendQueryParameter("api_key",API_KEY);
            return new MovieTrailerLoader(context,uriBuilder.toString());
        }
        else if(id==LOADER_B_ID){
            Uri baseUri = Uri.parse(TMDB_REQUEST_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();
            uriBuilder.appendPath(Integer.toString(clickedMovie.getMovieId()));
            uriBuilder.appendPath(REVIEW_PATH);
            uriBuilder.appendQueryParameter("api_key",API_KEY);
            return new MovieReviewLoader(context,uriBuilder.toString());
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object o) {
        int id  = loader.getId();
        if(id== LOADER_A_ID){
            trailerList = (ArrayList<Trailer>) o;

            if(o!=null || ((ArrayList<Trailer>) o).isEmpty()){

                clickedMovie.setTrailerList(trailerList);
            }
        }else if(id == LOADER_B_ID){
            reviewList = (ArrayList<Review>) o;

            if(reviewList!=null || reviewList.isEmpty()){

                clickedMovie.setReviewList(reviewList);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

       loader= null;
    }*/
}