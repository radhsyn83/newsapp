package com.radhsyn83.newsapp.presentation.news

import com.radhsyn83.newsapp.domain.model.Article

data class NewsState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String = "",
    var title: String = "",
    var page: Int = 1,
    val pageSize: Int = 20,
    var canLoadMore: Boolean = false
)