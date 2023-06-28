package com.radhsyn83.newsapp.domain.use_case

import com.radhsyn83.newsapp.common.Resource
import com.radhsyn83.newsapp.data.remote.dto.toNews
import com.radhsyn83.newsapp.domain.model.Article
import com.radhsyn83.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(q: String, source: String, page: Int, pageSize: Int): Flow<Resource<List<Article>>> = flow {
        try {
            emit(Resource.Loading<List<Article>>())
            val res = repository.news(q, source, page, pageSize).toNews()
            emit(Resource.Success<List<Article>>(res))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Article>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error<List<Article>>("Couldn't reach server. Check your internet connection."))
        }
    }
}