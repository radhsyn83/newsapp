package com.radhsyn83.newsapp.data.models


import com.google.gson.annotations.SerializedName

data class NewsModel(
    @SerializedName("articles")
    val articles: List<ArticleModel>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalResults")
    val totalResults: Int?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?
)

data class ArticleModel(
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("source")
    val source: Source?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?
) {
    data class Source(
        @SerializedName("id")
        val id: String?,
        @SerializedName("name")
        val name: String?
    )
}