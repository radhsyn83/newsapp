package com.radhsyn83.newsapp.di

import com.radhsyn83.newsapp.data.DataSourcesImpl
import com.radhsyn83.newsapp.data.response.RemoteDataSource
import com.radhsyn83.newsapp.net.ApiServices
import com.radhsyn83.newsapp.net.NetworkInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAPI() : ApiServices {
        return NetworkInstance.api()
    }

    @Provides
    @Singleton
    fun provideDataSource(apiServices: ApiServices): RemoteDataSource =
        RemoteDataSource(apiServices)

    @Provides
    @Singleton
    fun provideDataSourceImpl(remoteDataSource: RemoteDataSource): DataSourcesImpl =
        DataSourcesImpl(remoteDataSource)
}