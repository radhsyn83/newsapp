package com.radhsyn83.newsapp.presentation.sources

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radhsyn83.newsapp.common.Resource
import com.radhsyn83.newsapp.domain.use_case.GetSourcesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(
    private val getSourcesUseCase: GetSourcesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(SourcesState())
    val state: State<SourcesState> = _state

    init {
        savedStateHandle.get<String>("id")?.let {
            getSource(it)
        }
    }

    private fun getSource(category: String) {
        getSourcesUseCase(category).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value =
                        SourcesState(source = result.data ?: listOf(), title = category.uppercase())
                }

                is Resource.Error -> {
                    _state.value = SourcesState(
                        error = result.message ?: "An unexpected error occurred",
                        title = category.uppercase()
                    )
                }

                is Resource.Loading -> {
                    _state.value = SourcesState(isLoading = true, title = category.uppercase())
                }
            }
        }.launchIn(viewModelScope)
    }
}