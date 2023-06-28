package com.radhsyn83.newsapp.di

import com.radhsyn83.newsapp.common.NetworkInstance
import com.radhsyn83.newsapp.data.remote.NewsApi
import com.radhsyn83.newsapp.data.repository.NewsRepositoryImpl
import com.radhsyn83.newsapp.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideAPI() : NewsApi {
        return NetworkInstance.api()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: NewsApi): NewsRepository {
        return NewsRepositoryImpl(api)
    }
}