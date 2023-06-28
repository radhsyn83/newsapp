package com.radhsyn83.newsapp.presentation.webview

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.radhsyn83.newsapp.R
import com.radhsyn83.newsapp.ui.theme.NewsAppTheme

class WebViewActivity : ComponentActivity() {
    var url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = intent.getStringExtra("title") ?: "Movies"
        url = intent.getStringExtra("url") ?: ""

        Log.d("URL", " url $url")

        setContent {
            NewsAppTheme() {
                Screen(url)
            }
        }
    }

}

@Composable
fun Screen(
    url: String
) {
    val context = LocalContext.current as WebViewActivity
    Scaffold(backgroundColor = MaterialTheme.colorScheme.background, topBar = {
        TopAppBar(
            title = {
                Image(
                    modifier = Modifier
                        .height(40.dp)
                        .clickable {
                            context.onBackPressedDispatcher.onBackPressed()
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
            AndroidView(modifier = Modifier.fillMaxSize(), factory = {
                WebView(context).apply {
                    webViewClient = WebViewClient()
                    loadUrl(url)
                }
            })
        }
    })
}