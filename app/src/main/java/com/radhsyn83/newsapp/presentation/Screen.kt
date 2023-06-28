package com.radhsyn83.newsapp.presentation

sealed class Screen(val route: String) {
    object NewsScreen: Screen("NewsScreen")
    object SourcesScreen: Screen("SourcesScreen")
    object CategoriesScreen: Screen("CategoriesScreen")
}