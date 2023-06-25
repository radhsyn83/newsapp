package com.radhsyn83.newsapp.ui.sources

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.radhsyn83.newsapp.data.models.SourcesModel
import com.radhsyn83.newsapp.net.ApiServices
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SourceViewModel @Inject constructor(
    private val dataSource: ApiServices
) : ViewModel() {

    var sources by mutableStateOf(listOf<SourcesModel.Source>())
        private set

    fun load(sc: List<SourcesModel.Source>) {
        sources = sc
    }

}