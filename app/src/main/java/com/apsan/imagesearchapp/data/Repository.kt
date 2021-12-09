package com.apsan.imagesearchapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.apsan.imagesearchapp.api.APIInterface
import com.apsan.imagesearchapp.api.models.UnsplashPhotos
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val apiInterface: APIInterface){

    fun getSearchResults(q:String) : LiveData<PagingData<UnsplashPhotos>>{
        return Pager(
                PagingConfig( pageSize = 20,
                        maxSize = 100,
                        enablePlaceholders = false),
                null,
                {UnsplashPagingSource(apiInterface, q)}
        ).liveData
    }
}