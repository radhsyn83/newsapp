package com.radhsyn83.newsapp.data

import androidx.lifecycle.LiveData
import com.radhsyn83.newsapp.data.models.NewsModel
import com.radhsyn83.newsapp.data.models.SourcesModel
import com.radhsyn83.newsapp.vo.Resource

interface DataSource {
     fun sources() : LiveData<Resource<SourcesModel>>
     fun news(q: String, source: String, page: Int) : LiveData<Resource<NewsModel>>
}