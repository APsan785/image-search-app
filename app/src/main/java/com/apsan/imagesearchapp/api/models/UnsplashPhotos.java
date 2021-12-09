package com.apsan.imagesearchapp.api.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UnsplashPhotos implements Parcelable {
    private String id;
    private String description;
    private PhotoURL urls;
    private PhotoUser user;

    protected UnsplashPhotos(Parcel in) {
        id = in.readString();
        description = in.readString();
    }

    public static final Creator<UnsplashPhotos> CREATOR = new Creator<UnsplashPhotos>() {
        @Override
        public UnsplashPhotos createFromParcel(Parcel in) {
            return new UnsplashPhotos(in);
        }

        @Override
        public UnsplashPhotos[] newArray(int size) {
            return new UnsplashPhotos[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PhotoURL getUrls() {
        return urls;
    }

    public void setUrls(PhotoURL urls) {
        this.urls = urls;
    }

    public PhotoUser getUser() {
        return user;
    }

    public void setUser(PhotoUser user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(description);
    }
}
