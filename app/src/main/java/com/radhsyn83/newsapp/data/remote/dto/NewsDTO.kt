package com.radhsyn83.newsapp.data.remote.dto

import com.radhsyn83.newsapp.domain.model.Article


data class NewsDTO(
    val articles: List<ArticleDTO>?,
    val status: String?,
    val message: String?
)

fun NewsDTO.toNews() : List<Article> {
    var dt: List<Article> = emptyList()
    articles?.map {
        dt += it.toArticles()
    }
    return dt
}