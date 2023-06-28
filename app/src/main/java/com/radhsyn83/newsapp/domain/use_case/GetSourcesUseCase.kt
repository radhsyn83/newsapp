package com.radhsyn83.newsapp.domain.use_case

import com.radhsyn83.newsapp.common.Resource
import com.radhsyn83.newsapp.data.remote.dto.toSources
import com.radhsyn83.newsapp.domain.model.Source
import com.radhsyn83.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSourcesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(category: String = ""): Flow<Resource<List<Source>>> = flow {
        try {
            emit(Resource.Loading<List<Source>>())
            val res = repository.sources(category).toSources()
            emit(Resource.Success<List<Source>>(res))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Source>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error<List<Source>>("Couldn't reach server. Check your internet connection."))
        }
    }
}