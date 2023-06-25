package com.radhsyn83.newsapp.ui.news

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.radhsyn83.newsapp.R
import com.radhsyn83.newsapp.data.models.ArticleModel
import com.radhsyn83.newsapp.ui.WebViewActivity
import com.radhsyn83.newsapp.ui.theme.Error
import com.radhsyn83.newsapp.ui.theme.Loading
import com.radhsyn83.newsapp.ui.theme.NewsAppTheme
import com.radhsyn83.newsapp.ui.theme.OnBottomReached
import com.radhsyn83.newsapp.ui.theme.Typography
import com.radhsyn83.newsapp.utils.Tools
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterialApi::class)
@AndroidEntryPoint
class NewsActivity : ComponentActivity() {
    var title = "Movies"
    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = viewModels<NewsViewModel>().value

        title = intent.getStringExtra("title") ?: "Movies"
        id = intent.getStringExtra("id") ?: ""
        viewModel.load(id)

        setContent {
            NewsAppTheme() {
                val isLoading = viewModel.loading
                val articles = viewModel.articles
                val error = viewModel.error
                val canLoadMore = viewModel.canLoadMore
                val isRefresh = viewModel.isRefreshing
                val pullRefreshState =
                    rememberPullRefreshState(isRefresh, { viewModel.load(id) })


                if (id == "") {
                    viewModel.setErrorMessage("News movie tidak ditemukan")
                }

                Screen(
                    title,
                    isLoading = isLoading,
                    articles = articles,
                    error = error,
                    isRefresh = isRefresh,
                    pullRefreshState = pullRefreshState,
                    loadMore = {
                        if (canLoadMore) viewModel.load(id, isLoadMore = true)
                    },
                    onSearch = { q: String ->
                        viewModel.load(id, q = q)
                    })
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Screen(
    title: String,
    articles: List<ArticleModel>,
    isLoading: Boolean,
    error: String,
    loadMore: () -> Unit,
    pullRefreshState: PullRefreshState,
    isRefresh: Boolean,
    onSearch: (q: String) -> Unit

) {
    val context = LocalContext.current as NewsActivity
    Scaffold(backgroundColor = MaterialTheme.colorScheme.background, topBar = {
        TopAppBar(
            title = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(5.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .height(40.dp)
                            .padding(end = 10.dp)
                            .clickable {
                                context.onBackPressedDispatcher.onBackPressed()
                            },
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back),
                        contentDescription = "back"
                    )

                    SearchField(title = title, onSearch = onSearch)

                }
            },
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            elevation = 10.dp
        )
    }, content = { padding ->
        Box(Modifier.pullRefresh(pullRefreshState)) {
            Column(
                modifier = Modifier
                    .padding(padding)
            ) {

                if (isLoading)
                    Loading()
                else if (error.isNotEmpty())
                    Error(error)
                else if (articles.isEmpty())
                    Error("No data")
                else
                    Items(articles, loadMore)
            }
            PullRefreshIndicator(isRefresh, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchField(title: String, onSearch: (q: String) -> Unit) {
    var searchValue by remember {
        mutableStateOf("")
    }
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
            .padding(start = 10.dp)
    ) {
        BasicTextField(
            value = searchValue,
            onValueChange = { searchValue = it },
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            enabled = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(searchValue)
                }),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        ) { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = searchValue,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                singleLine = true,
                enabled = true,
                placeholder = {
                    Text(text = "Search in $title")
                },
                interactionSource = interactionSource,
                contentPadding = PaddingValues(0.dp),
                trailingIcon = {
                    IconButton(onClick = {
                        onSearch(searchValue)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null
                        )
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Gray,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Items(articles: List<ArticleModel>, loadMore: () -> Unit) {
    val listState = rememberLazyListState()
    val context = LocalContext.current
    LazyColumn(state = listState, modifier = Modifier.padding(5.dp)) {
        items(count = articles.size) { i ->
            Card(
                elevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {
                        val intent = Intent(context, WebViewActivity::class.java)
                        intent.putExtra("url", articles[i].url)
                        context.startActivity(intent)
                    }
            ) {
                Column(verticalArrangement = Arrangement.Top) {
                    GlideImage(
                        model = articles[i].urlToImage,
                        contentDescription = articles[i].title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.aspectRatio(1.5f)
                    )

                    Text(
                        text = articles[i].title.toString(),
                        modifier = Modifier.padding(10.dp),
                        style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = articles[i].description.toString(),
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                        style = Typography.bodyMedium
                    )
                    Text(
                        text = Tools.dateFormat(
                            articles[i].publishedAt!!.replace("T", " ").replace("Z", "")
                        ),
                        modifier = Modifier.padding(10.dp),
                        style = Typography.bodySmall.copy(color = Color.Gray)
                    )
                }
            }
        }
    }
    // call the extension function
    listState.OnBottomReached(buffer = 2, loadMore = loadMore)
}
