package com.radhsyn83.newsapp.presentation.news

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radhsyn83.newsapp.common.Resource
import com.radhsyn83.newsapp.domain.model.Source
import com.radhsyn83.newsapp.domain.use_case.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var id by mutableStateOf("")
        private set

    var title by mutableStateOf("Movies")
        private set

    private val _state = mutableStateOf(NewsState())
    val state: State<NewsState> = _state

    init {
        savedStateHandle.get<String>("id")?.let { id ->
            this.id = id
            getNews()
        }
        savedStateHandle.get<String>("title")?.let { title ->
            this.title = title
        }
        getNews()
    }

    fun getNews(q: String = "", isLoadMore: Boolean = false) {
        var currentPage = _state.value.page
        var pageSize = _state.value.pageSize

        getNewsUseCase(q, id, currentPage, pageSize).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val ar = result.data ?: emptyList()
                    val isNoData = ar.isEmpty()

                    currentPage++

                    if (!isLoadMore) {
                        if (!isNoData) {
                            _state.value = NewsState(
                                articles = ar,
                                canLoadMore = isNoData,
                                page = currentPage,
                            )
                        } else {
                            _state.value = NewsState(
                                error = result.message ?: "Data not found."
                            )
                        }
                    } else {
                        val newArticles = _state.value.articles + ar
                        _state.value = _state.value.copy(
                            articles = newArticles,
                            page = currentPage,
                        )
                    }
                }

                is Resource.Error -> {
                    _state.value = NewsState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }

                is Resource.Loading -> {
                    if (!isLoadMore)
                        _state.value = NewsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun groupCategories(sc: List<Source>): List<Source> {
        val newCats = mutableListOf<Source>()
        sc.map { s ->
            val isExist = newCats.find { it.category.toString().contains(s.category.toString()) }
            if (isExist == null) newCats += s
        }
        return newCats
    }
}