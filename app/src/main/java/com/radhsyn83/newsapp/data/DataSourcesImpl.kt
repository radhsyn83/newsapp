package com.radhsyn83.newsapp.data

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.radhsyn83.newsapp.data.models.NewsModel
import com.radhsyn83.newsapp.data.models.SourcesModel
import com.radhsyn83.newsapp.data.response.RemoteDataSource
import com.radhsyn83.newsapp.vo.Resource
import javax.inject.Inject

@SuppressLint("CheckResult")
class DataSourcesImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : DataSource {

    @SuppressLint("CheckResult")
    override  fun news(q: String, source: String, page: Int): LiveData<Resource<NewsModel>> {
        val res = MutableLiveData<Resource<NewsModel>>()
        res.value = Resource.loading(null)
        remoteDataSource.news(q, source, page)
            .subscribe({
                res.value = if (it.status == "ok")
                    Resource.success(it)
                else
                    Resource.error(it.message)
            }, {
                res.value = Resource.error(it.message)
            })
        return res
    }

    override fun sources(): LiveData<Resource<SourcesModel>> {
        val res = MutableLiveData<Resource<SourcesModel>>()
        res.value = Resource.loading(null)
        remoteDataSource.sources()
            .subscribe({
                res.value = if (it.status == "ok")
                    Resource.success(it)
                else
                    Resource.error(it.message)
            }, {
                res.value = Resource.error(it.message)
            })
        return res
    }
}