package com.radhsyn83.newsapp.data.repository

import com.radhsyn83.newsapp.data.remote.NewsApi
import com.radhsyn83.newsapp.data.remote.dto.NewsDTO
import com.radhsyn83.newsapp.data.remote.dto.SourcesDTO
import com.radhsyn83.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
) : NewsRepository {

    override suspend fun sources(category: String): SourcesDTO = api.sources(category)

    override suspend fun news(q: String, source: String, page: Int, pageSize: Int): NewsDTO =
        api.news(q, source, page, pageSize)
}