package com.example.android.popularmoviesstage2.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.popularmoviesstage2.pojo.Movie;

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
    private QueryUtils(){}
        private static final String LOG_TAG =QueryUtils.class.getName();

        public static List<Movie> fetchMovieData(String requestUrl){

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

                for (int i=0;i<resultsArray.length();i++){
                    JSONObject currentObject = resultsArray.getJSONObject(i);

                    String moviePosterUrl= currentObject.getString("poster_path");

                    String movieTitle = currentObject.getString("original_title");

                    String moviePlotSynopsis = currentObject.getString("overview");

                    String movieReleaseDate = currentObject.getString("release_date");

                    double movieRatings = currentObject.getDouble("vote_average");

                    int movieId = currentObject.getInt("id");
                    // implement sub Loaders

                    Movie movie = new Movie(movieTitle,moviePosterUrl,movieReleaseDate,moviePlotSynopsis,movieRatings,movieId);


                    movies.add(movie);

                }

            } catch (JSONException e) {

                Log.e(LOG_TAG, "Problem parsing the movie JSON results", e);
            }

            // Return the list of movies
            return movies;
        }
}