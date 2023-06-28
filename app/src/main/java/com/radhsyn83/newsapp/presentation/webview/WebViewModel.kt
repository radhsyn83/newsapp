package com.radhsyn83.newsapp.presentation.webview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var url by mutableStateOf("")
        private set

    init {
        savedStateHandle.get<String>("url")?.let { url ->
            this.url = url
        }
    }
}