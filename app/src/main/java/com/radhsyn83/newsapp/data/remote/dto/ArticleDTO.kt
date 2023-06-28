package com.radhsyn83.newsapp.data.remote.dto

import com.radhsyn83.newsapp.domain.model.Article
import com.radhsyn83.newsapp.domain.model.Source


data class ArticleDTO(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) {
    data class Source(
        val id: String?,
        val name: String?
    )
}

fun ArticleDTO.toArticles() : Article {
    return Article(
        author = author,
        title = title,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source?.toSource(),
        url = url,
        urlToImage = urlToImage
    )
}

fun ArticleDTO.Source.toSource() : Source {
    return Source(
        category = null,
        name = name,
        id = id
    )
}