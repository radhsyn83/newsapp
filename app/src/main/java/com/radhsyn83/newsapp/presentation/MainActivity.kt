package com.radhsyn83.newsapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.radhsyn83.newsapp.presentation.categories.CategoriesScreen
import com.radhsyn83.newsapp.presentation.news.NewsScreen
import com.radhsyn83.newsapp.presentation.sources.SourcesScreen
import com.radhsyn83.newsapp.presentation.webview.WebViewScreen
import com.radhsyn83.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.CategoriesScreen.route
                    ) {
                        composable(
                            route = Screen.CategoriesScreen.route
                        ) {
                            CategoriesScreen(navController)
                        }
                        composable(
                            route = Screen.NewsScreen.route + "/{id}/{title}"
                        ) {
                            NewsScreen(navController)
                        }
                        composable(
                            route = Screen.SourcesScreen.route + "/{id}"
                        ) {
                            SourcesScreen(navController)
                        }
                        composable(
                            route = Screen.WebViewScreen.route + "/{url}"
                        ) {
                            WebViewScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

