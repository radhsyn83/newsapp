package com.radhsyn83.newsapp.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.radhsyn83.newsapp.data.models.SourcesModel
import com.radhsyn83.newsapp.net.ApiServices
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataSource: ApiServices
) : ViewModel() {

    var error by mutableStateOf("")
        private set

    var loading by mutableStateOf(false)
        private set

    var isRefreshing by mutableStateOf(false)
        private set

    var filteredSource by mutableStateOf(listOf<SourcesModel.Source>())
        private set

    var sources by mutableStateOf(listOf<SourcesModel.Source>())
        private set

    init {
        load()
    }

    fun load() {
        dataSource.sources()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<SourcesModel> {
                override fun onSubscribe(d: Disposable) {
                    loading = true
                    error = ""
                }

                override fun onNext(t: SourcesModel) {
                    loading = false
                    isRefreshing = false
                    if (t.message.isNullOrEmpty()) {
                        sources = t.sources ?: listOf()
                        filteredSource = groupCategories(t.sources ?: listOf())
                    }
                    else {
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

    fun groupCategories(sc: List<SourcesModel.Source>) : List<SourcesModel.Source> {
        val newCats = mutableListOf<SourcesModel.Source>()
        sc.map { s ->
            val isExist = newCats.find { it.category.toString().contains(s.category.toString()) }
            if (isExist == null) newCats += s
        }
        return newCats
    }
}