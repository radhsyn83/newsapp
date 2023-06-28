package com.radhsyn83.newsapp.presentation.webview

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.radhsyn83.newsapp.R

@Composable
fun WebViewScreen(
    navController: NavController,
    viewModel: WebViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val url = Gson().fromJson(viewModel.url, String::class.java)
    Scaffold(backgroundColor = MaterialTheme.colorScheme.background, topBar = {
        TopAppBar(
            title = {
                Image(
                    modifier = Modifier
                        .height(40.dp)
                        .clickable {
                           navController.popBackStack()
                        },
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back),
                    contentDescription = "back"
                )

            },
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            elevation = 10.dp
        )
    }, content = { padding ->
        Box(
            Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (viewModel.url.isNotEmpty()) {
                AndroidView(modifier = Modifier.fillMaxSize(), factory = {
                    WebView(context).apply {
                        webViewClient = WebViewClient()
                        loadUrl(url)
                    }
                })
            }
        }
    })
}