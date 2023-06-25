package com.radhsyn83.newsapp.ui.news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.radhsyn83.newsapp.data.models.ArticleModel
import com.radhsyn83.newsapp.data.models.NewsModel
import com.radhsyn83.newsapp.net.ApiServices
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val dataSource: ApiServices
) : ViewModel() {

    var page by mutableStateOf(1)
        private set

    var isRefreshing by mutableStateOf(false)
        private set

    var canLoadMore by mutableStateOf(true)
        private set

    var error by mutableStateOf("")
        private set

    var loading by mutableStateOf(false)
        private set

    var articles by mutableStateOf(listOf<ArticleModel>())
        private set

    fun load(id: String, q: String = "", isLoadMore: Boolean = false) {
        if (!isLoadMore) {
            page = 1
            canLoadMore = true
        }
        dataSource.news(q, id, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<NewsModel> {
                override fun onSubscribe(d: Disposable) {
                    if (!isLoadMore) {
                        loading = true
                    }
                }

                override fun onNext(t: NewsModel) {
                    loading = false
                    isRefreshing = false
                    if (t.message.isNullOrEmpty()) {
                        page++
                        articles = if (isLoadMore) {
                            articles + (t.articles ?: listOf())
                        } else {
                            t.articles ?: listOf()
                        }
                    } else {
                        error = t.message.toString()
                    }

                }

                override fun onError(e: Throwable) {
                    loading = false
                    isRefreshing = false
                    error = e.message.toString()
                }

                override fun onComplete() {
                    loading = false
                    isRefreshing = false
                }
            })
    }

    fun setErrorMessage(msg: String) {
        error = msg
    }
}