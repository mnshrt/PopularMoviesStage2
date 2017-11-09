package com.example.android.popularmoviesstage2.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by emkayx on 01-11-2017.
 */

public class Trailer implements Parcelable {

    int videoId;

    public Trailer(int videoId) {
        this.videoId = videoId;
    }

    protected Trailer(Parcel in) {
        videoId = in.readInt();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public int getVideoId() {
        return videoId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(videoId);
    }
}
