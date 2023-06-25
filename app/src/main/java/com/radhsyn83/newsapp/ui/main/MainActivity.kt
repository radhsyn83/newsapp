package com.radhsyn83.newsapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.radhsyn83.newsapp.data.models.SourcesModel
import com.radhsyn83.newsapp.ui.sources.SourceActivity
import com.radhsyn83.newsapp.ui.theme.Error
import com.radhsyn83.newsapp.ui.theme.Loading
import com.radhsyn83.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme() {
                val viewModel = viewModels<MainViewModel>().value
                val isLoading = viewModel.loading
                val sources = viewModel.filteredSource
                val error = viewModel.error
                val isRefresh = viewModel.isRefreshing
                val pullRefreshState =
                    rememberPullRefreshState(isRefresh, { viewModel.load() })
                MainScreen(
                    isLoading = isLoading,
                    sources = sources,
                    error = error,
                    isRefresh = isRefresh,
                    pullRefreshState = pullRefreshState,
                    onItemClicked = { sc ->
                        val filtered = viewModel.sources.filter { it.category == sc.category  }
                        val intent = Intent(this, SourceActivity::class.java)
                        intent.putExtra("title", sc.category)
                        intent.putParcelableArrayListExtra("sources", ArrayList(filtered))
                        startActivity(intent)
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    sources: List<SourcesModel.Source>,
    isLoading: Boolean,
    error: String,
    pullRefreshState: PullRefreshState,
    isRefresh: Boolean,
    onItemClicked: (SourcesModel.Source) -> Unit,
) {
    Scaffold(backgroundColor = MaterialTheme.colorScheme.background, topBar = {
        TopAppBar(
            title = {
                Text(text = "News Categories")
            },
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            elevation = 10.dp
        )
    }, content = { padding ->

        Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
            Column(
                modifier = Modifier
                    .padding(padding)
            ) {
                if (isLoading)
                    Loading()
                else if (error.isNotEmpty())
                    Error(error)
                else
                    Items(sources = sources, onItemClicked = onItemClicked)
            }
            PullRefreshIndicator(isRefresh, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    })
}

@Composable
private fun Items(
    sources: List<SourcesModel.Source>,
    onItemClicked: (SourcesModel.Source) -> Unit,
) {
    val context = LocalContext.current
    LazyColumn(state = rememberLazyListState(), modifier = Modifier.padding(5.dp)) {
        items(count = sources.size) { i ->
            Card(
                elevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {
                        onItemClicked(sources[i])
                    }
            ) {
                Text(text = sources[i].category.toString().uppercase(), modifier = Modifier.padding(10.dp))
            }
        }
    }
}