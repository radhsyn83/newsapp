package com.radhsyn83.newsapp.data.remote


import com.radhsyn83.newsapp.data.remote.dto.NewsDTO
import com.radhsyn83.newsapp.data.remote.dto.SourcesDTO
import retrofit2.http.*

interface NewsApi {
    @GET("top-headlines/sources")
    suspend fun sources(
        @Query("category") category: String
    ): SourcesDTO

    @GET("everything")
    suspend fun news(
        @Query("q") q: String,
        @Query("sources") sources: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): NewsDTO
}
