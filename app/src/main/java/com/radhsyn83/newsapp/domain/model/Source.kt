package com.radhsyn83.newsapp.domain.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    var category: String?,
    var id: String?,
    var name: String?,
) : Parcelable