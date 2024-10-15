package com.gmail.denuelle42.denuanime.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gmail.denuelle42.denuanime.navigation.NavigationScreens
import com.gmail.denuelle42.denuanime.ui.common.AnimeItemCard
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@Composable
fun HomeScreen(
    onPopBackStack : () -> Unit,
    onNavigation : (route : NavigationScreens) -> Unit
) {
    HomeScreenContent()
}

@Composable
fun HomeScreenContent(modifier: Modifier = Modifier) {
    AnimeItemCard(image = "https://cdn.myanimelist.net/images/anime/4/19644l.jpg", title = "Kimi no nawa")
}

@Preview
@Composable
private fun HomeScreenPreview() {
    DenuAnimeTheme {
        Surface(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
            HomeScreenContent(modifier = Modifier.fillMaxSize())
        }
    }
}