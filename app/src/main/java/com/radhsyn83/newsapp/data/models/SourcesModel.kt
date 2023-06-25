package com.radhsyn83.newsapp.data.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SourcesModel(
    @SerializedName("sources")
    val sources: List<Source>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?
) {
    @Parcelize
    data class Source(
        @SerializedName("category")
        val category: String?,
        @SerializedName("country")
        val country: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("language")
        val language: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("url")
        val url: String?
    ) : Parcelable
}