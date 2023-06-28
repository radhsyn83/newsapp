package com.radhsyn83.newsapp.domain.repository

import com.radhsyn83.newsapp.data.remote.dto.NewsDTO
import com.radhsyn83.newsapp.data.remote.dto.SourcesDTO

interface NewsRepository {
    suspend fun sources(category: String) : SourcesDTO
    suspend fun news(q: String, source: String, page: Int, pageSize: Int) : NewsDTO
}