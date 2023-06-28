package com.radhsyn83.newsapp.presentation.news.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.radhsyn83.newsapp.common.Tools
import com.radhsyn83.newsapp.domain.model.Article
import com.radhsyn83.newsapp.ui.theme.Typography

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsItems(
    article: Article,
    onItemClick: (Article) -> Unit
) {
    Card(
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
               onItemClick(article)
            }
    ) {
        Column(verticalArrangement = Arrangement.Top) {
            GlideImage(
                model = article.urlToImage,
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.aspectRatio(1.5f)
            )

            Text(
                text = article.title.toString(),
                modifier = Modifier.padding(10.dp),
                style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = article.description.toString(),
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                style = Typography.bodyMedium
            )
            Text(
                text = Tools.dateFormat(
                    article.publishedAt!!.replace("T", " ").replace("Z", "")
                ),
                modifier = Modifier.padding(10.dp),
                style = Typography.bodySmall.copy(color = Color.Gray)
            )
        }
    }
}