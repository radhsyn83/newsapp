package com.radhsyn83.newsapp.data.remote.dto


import android.os.Parcelable
import com.radhsyn83.newsapp.domain.model.Source
import kotlinx.parcelize.Parcelize

data class SourcesDTO(
    val sources: List<Source>?,
    val status: String?,
    val message: String?
) {
    @Parcelize
    data class Source(
        val category: String?,
        val id: String?,
        val name: String?,
    ) : Parcelable
}

fun SourcesDTO.toSources():List<Source> {
    var dt: List<Source> = emptyList()
    sources?.map {
        dt += Source(
            id = it.id,
            name = it.name,
            category = it.category
        )
    }
    return dt
}