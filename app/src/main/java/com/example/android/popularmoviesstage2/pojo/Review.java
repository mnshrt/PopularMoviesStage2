package com.example.android.popularmoviesstage2.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by emkayx on 31-10-2017.
 */

public class Review implements Parcelable {

    private String authorName;
    private String review;

    public Review(String authorName, String review) {
        this.authorName = authorName;
        this.review = review;
    }

    protected Review(Parcel in) {
        authorName = in.readString();
        review = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String getAuthorName() {
        return authorName;
    }

    public String getReview() {
        return review;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(authorName);
        parcel.writeString(review);
    }
}
