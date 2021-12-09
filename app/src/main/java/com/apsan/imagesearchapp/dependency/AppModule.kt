package com.apsan.imagesearchapp.dependency

import com.apsan.imagesearchapp.api.APIInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    var loggingInterceptor = HttpLoggingInterceptor()
    var okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                        .build()
                chain.proceed(newRequest)
            })
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
            Retrofit.Builder()
                    .baseUrl("https://api.unsplash.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()

    @Provides
    @Singleton
    fun provideUnsplashApi(retrofit: Retrofit): APIInterface =
            retrofit.create(APIInterface::class.java)
}