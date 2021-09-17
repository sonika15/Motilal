package com.motilal.project.utils

import com.motilal.project.model.TrendingItems
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NetworkCall {
    companion object {
        operator fun invoke(): NetworkCall {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/search/")
                .build()
                .create(NetworkCall::class.java)
        }
    }

    @GET("repositories?q=language:Java")
    suspend fun getTrendingRepo(): Response<TrendingItems>

}