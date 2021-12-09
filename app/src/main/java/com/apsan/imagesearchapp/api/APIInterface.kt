package com.apsan.imagesearchapp.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIInterface {

    @Headers( "Accept-Version: v1", "Authorization: Client-ID pKAmzmfgKvtpoGhP4J0fUpjMmEnOv68arCyJl9AKsUU" )
    @GET("search/photos")
    suspend fun getResponse(
            @Query("query") query: String, @Query("page") page: Int, @Query("per_page") per_page: Int
    ):APIResponse

}