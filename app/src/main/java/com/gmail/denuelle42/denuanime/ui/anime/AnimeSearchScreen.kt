package com.gmail.denuelle42.denuanime.ui.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@Composable
fun AnimeSearchScreen(modifier: Modifier = Modifier) {

}

@Composable
fun AnimeSearchScreenContent(modifier: Modifier = Modifier) {
    var searchState by remember { mutableStateOf("") }
    val lazyState = rememberLazyListState()

    LazyColumn(
        state = lazyState,
        contentPadding = PaddingValues(8.dp)
    ) {

    }
}

@Preview
@Composable
private fun AnimeSearchScreenPreview() {
    DenuAnimeTheme {
        Surface(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)
        ) {
            AnimeSearchScreenContent()
        }
    }
}