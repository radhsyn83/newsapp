package com.radhsyn83.newsapp.net


import com.radhsyn83.newsapp.data.models.NewsModel
import com.radhsyn83.newsapp.data.models.SourcesModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ApiServices {

    @GET("top-headlines/sources")
    fun sources(): Observable<SourcesModel>

    @GET("everything")
    fun news(
        @Query("q") q: String,
        @Query("sources") sources: String,
        @Query("page") page: Int,
    ):  Observable<NewsModel>
}
