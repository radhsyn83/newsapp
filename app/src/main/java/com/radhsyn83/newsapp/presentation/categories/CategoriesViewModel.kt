package com.radhsyn83.newsapp.presentation.categories

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radhsyn83.newsapp.common.Resource
import com.radhsyn83.newsapp.domain.model.Source
import com.radhsyn83.newsapp.domain.use_case.GetSourcesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getSourcesUseCase: GetSourcesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CategoriesState())
    val state: State<CategoriesState> = _state

    init {
        getSource()
    }

    private fun getSource() {
        getSourcesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value =
                        CategoriesState(source = groupCategories(result.data ?: listOf()))
                }

                is Resource.Error -> {
                    _state.value = CategoriesState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }

                is Resource.Loading -> {
                    _state.value = CategoriesState(isLoading = true)
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