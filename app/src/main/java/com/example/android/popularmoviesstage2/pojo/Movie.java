package com.example.android.popularmoviesstage2.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by emkayx on 30-10-2017.
 */

public class Movie implements Parcelable {
    private  String moviePosterPath;
    private String movieOriginalTitle;
    private String movieDate;
    private double movieRating;
    private String moviePlotSynopsis;
    private int movieId;
    private List<Review> reviewList;
    private List<Trailer> trailerList;


    Movie(){}

 /*    Movie(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }*/

    public Movie(Parcel parcel){
        this.moviePosterPath=parcel.readString();
        this.movieOriginalTitle=parcel.readString();
        this.movieDate=parcel.readString();
        this.movieRating=parcel.readDouble();
        this.moviePlotSynopsis=parcel.readString();
        this.movieId=parcel.readInt();
        this.reviewList=parcel.createTypedArrayList(Review.CREATOR);
        this.trailerList=parcel.createTypedArrayList(Trailer.CREATOR);




    }
    public Movie(String movieOriginalTitle, String moviePosterPath, String movieDate, String moviePlotSynopsis, double movieRating, int movieId) {
        this.moviePosterPath = moviePosterPath;
        this.movieOriginalTitle = movieOriginalTitle;
        this.movieDate = movieDate;
        this.moviePlotSynopsis = moviePlotSynopsis;
        this.movieRating = movieRating;
        this.movieId = movieId;
    }


    public Movie(String movieOriginalTitle, String moviePosterPath, String movieDate, String moviePlotSynopsis, double movieRating, int movieId, List<Review> reviewList, List<Trailer> trailerList) {
        this.moviePosterPath = moviePosterPath;
        this.movieOriginalTitle = movieOriginalTitle;
        this.movieDate = movieDate;
        this.moviePlotSynopsis = moviePlotSynopsis;
        this.movieRating = movieRating;
        this.movieId=movieId;
        this.reviewList= reviewList;
        this.trailerList=trailerList;

    }
    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public String getMovieOriginalTitle() {
        return movieOriginalTitle;
    }

    public String getMoviePlotSynopsis() {
        return moviePlotSynopsis;
    }

    public double getMovieRating() {
        return movieRating;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public int getMovieId(){
        return movieId;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public List<Trailer> getTrailerList() {
        return trailerList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
    }

    @Override
    public String toString() {
        return movieDate+" "+movieOriginalTitle+" "+moviePlotSynopsis+" "+movieRating+" "+moviePosterPath+" "+movieId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.moviePosterPath);
        dest.writeString(this.movieOriginalTitle);
        dest.writeString(this.movieDate);
        dest.writeDouble(this.movieRating);
        dest.writeString(this.moviePlotSynopsis);
        dest.writeInt(this.movieId);
        dest.writeTypedList(this.reviewList);
        dest.writeTypedList(this.trailerList);



    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
