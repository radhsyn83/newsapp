package com.radhsyn83.newsapp.presentation.sources

import com.radhsyn83.newsapp.domain.model.Source

data class SourcesState(
    val isLoading: Boolean = false,
    val source: List<Source> = emptyList(),
    val error: String = "",
    var title: String = "",
)