package com.radhsyn83.newsapp.ui.sources

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.radhsyn83.newsapp.R
import com.radhsyn83.newsapp.data.models.SourcesModel
import com.radhsyn83.newsapp.ui.news.NewsActivity
import com.radhsyn83.newsapp.ui.theme.NewsAppTheme
import com.radhsyn83.newsapp.utils.parcelableArrayList
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SourceActivity : ComponentActivity() {
    var title = "Movies"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = viewModels<SourceViewModel>().value

        setContent {
            title = intent.getStringExtra("title") ?: "Movies"
            val sc = intent.parcelableArrayList<SourcesModel.Source>("sources") ?: arrayListOf()
            NewsAppTheme() {
                viewModel.load(sc.toList())
                val sources = viewModel.sources
                Screen(
                    title,
                    sources = sources,
                )
            }
        }
    }
}

@Composable
fun Screen(
    title: String,
    sources: List<SourcesModel.Source>,
) {
    val context = LocalContext.current as SourceActivity
    Scaffold(backgroundColor = MaterialTheme.colorScheme.background, topBar = {
        TopAppBar(
            title = {
                Row {
                    Image(
                        modifier = Modifier
                            .height(40.dp)
                            .clickable {
                                context.onBackPressedDispatcher.onBackPressed()
                            },
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back),
                        contentDescription = "back"
                    )
                    Text(text = title.uppercase(), Modifier.padding(start = 10.dp), maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            },
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            elevation = 10.dp
        )
    }, content = { padding ->
        Box(modifier = Modifier.padding(padding)){
            Items(sources = sources)
        }
    })
}

@Composable
private fun Items(sources: List<SourcesModel.Source>) {
    val context = LocalContext.current
    LazyColumn(state = rememberLazyListState(), modifier = Modifier.padding(5.dp)) {
        items(count = sources.size) { i ->
            Card(
                elevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {
                        val intent = Intent(context, NewsActivity::class.java)
                        intent.putExtra("id", sources[i].id)
                        intent.putExtra("title", sources[i].name)
                        context.startActivity(intent)
                    }
            ) {
                Text(text = sources[i].name ?: "", modifier = Modifier.padding(10.dp))
            }
        }
    }
}