package com.apsan.imagesearchapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apsan.imagesearchapp.api.APIInterface
import com.apsan.imagesearchapp.api.APIResponse
import com.apsan.imagesearchapp.api.models.UnsplashPhotos
import retrofit2.HttpException
import java.io.IOException

class UnsplashPagingSource(
        private val apiInterface: APIInterface,
        private val query: String
): PagingSource<Int, UnsplashPhotos>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhotos> {
        var position = params.key
        if(position==null) position = 1
        return try {
            val response = apiInterface.getResponse(query, position, params.loadSize)
            LoadResult.Page(
                    data = response.photolist,
                    prevKey = null, // Only paging forward.
                    nextKey = if(response.photolist == null) null else position+1
            )
        }
        catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhotos>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}