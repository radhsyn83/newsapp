package com.radhsyn83.newsapp.presentation.sources.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.radhsyn83.newsapp.domain.model.Source

@Composable
fun SourceItems(
    source: Source,
    onItemClick: (Source) -> Unit
) {
    Card(
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
               onItemClick(source)
            }
    ) {
        Text(text = source.name ?: "", modifier = Modifier.padding(10.dp))
    }
}