package com.radhsyn83.newsapp.presentation.categories

import com.radhsyn83.newsapp.domain.model.Source

data class CategoriesState(
    val isLoading: Boolean = false,
    val source: List<Source> = emptyList(),
    val error: String = "",
)