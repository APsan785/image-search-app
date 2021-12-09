package com.apsan.imagesearchapp.api;

import com.apsan.imagesearchapp.api.models.UnsplashPhotos;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APIResponse {

    @SerializedName("results")
    private List<UnsplashPhotos> photosList;

    public List<UnsplashPhotos> getPhotolist() {
        return photosList;
    }

    public void setPhotolist(List<UnsplashPhotos> photolist) {
        this.photosList = photolist;
    }

}
