package com.radhsyn83.newsapp.data.response

import com.radhsyn83.newsapp.data.models.NewsModel
import com.radhsyn83.newsapp.data.models.SourcesModel
import com.radhsyn83.newsapp.net.ApiServices
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
                       private val api: ApiServices) {

    fun news(q: String, source: String, page: Int): Observable<NewsModel> =
        api.news(q, source, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun sources(): Observable<SourcesModel> =
        api.sources()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}