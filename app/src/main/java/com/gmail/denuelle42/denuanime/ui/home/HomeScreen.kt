package com.gmail.denuelle42.denuanime.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.data.remote.models.BaseImages
import com.gmail.denuelle42.denuanime.data.remote.models.ImageType
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.navigation.NavigationScreens
import com.gmail.denuelle42.denuanime.ui.common.AnimeItemCard
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@Composable
fun HomeScreen(
    onPopBackStack: () -> Unit,
    onNavigation: (route: NavigationScreens) -> Unit
) {
    HomeScreenContent(
        list = listOf(
            AnimeDetails(
                images = ImageType(jpg = BaseImages(image_url = "https://cdn.myanimelist.net/images/anime/1295/106551.jpg")),
                title = "Kaguya-sama wa Kokurasetai: Tensai-tachi no Renai Zunousen"
            ),
            AnimeDetails(
                images = ImageType(jpg = BaseImages(image_url = "https://cdn.myanimelist.net/images/anime/1015/138006.jpg")),
                title = "Sousou no Frieren"
            ),
            AnimeDetails(
                images = ImageType(jpg = BaseImages(image_url = "https://cdn.myanimelist.net/images/anime/1032/135088.jpg")),
                title = "Code Geass: Hangyaku no Lelouch"
            ),
            AnimeDetails(
                images = ImageType(jpg = BaseImages(image_url = "https://cdn.myanimelist.net/images/anime/4/19644l.jpg")),
                title = "Cowboy Bepop"
            ),
            AnimeDetails(
                images = ImageType(jpg = BaseImages(image_url = "https://cdn.myanimelist.net/images/anime/5/87048.jpg")),
                title = "Kimi no Na Wa"
            ),
            AnimeDetails(
                images = ImageType(jpg = BaseImages(image_url = "https://cdn.myanimelist.net/images/anime/1021/95670.jpg")),
                title = "Domestic na Kanojo"
            ),
            AnimeDetails(
                images = ImageType(jpg = BaseImages(image_url = "https://cdn.myanimelist.net/images/anime/1935/127974.jpg")),
                title = "Steins;Gate"
            ),
        )
    )
}

@Composable
fun HomeScreenContent(modifier: Modifier = Modifier, list: List<AnimeDetails>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier
    ) {
        items(list) { anime ->
            AnimeItemCard(
                image = anime.images?.jpg?.image_url.orEmpty(),
                title = anime.title.orEmpty(),
                height = 250.dp
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    DenuAnimeTheme {
        Surface(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
            HomeScreenContent(
                modifier = Modifier.fillMaxSize(), list = listOf(
                    AnimeDetails(
                        images = ImageType(jpg = BaseImages(image_url = "https://cdn.myanimelist.net/images/anime/1295/106551.jpg")),
                        title = "Kaguya-sama wa Kokurasetai: Tensai-tachi no Renai Zunousen"
                    ),
                    AnimeDetails(
                        images = ImageType(jpg = BaseImages(image_url = "https://cdn.myanimelist.net/images/anime/1015/138006.jpg")),
                        title = "Sousou no Frieren"
                    ),
                    AnimeDetails(
                        images = ImageType(jpg = BaseImages(image_url = "https://cdn.myanimelist.net/images/anime/1032/135088.jpg")),
                        title = "Code Geass: Hangyaku no Lelouch"
                    ),
                    AnimeDetails(
                        images = ImageType(jpg = BaseImages(image_url = "https://cdn.myanimelist.net/images/anime/4/19644l.jpg")),
                        title = "Cowboy Bepop"
                    ),
                    AnimeDetails(
                        images = ImageType(jpg = BaseImages(image_url = "https://cdn.myanimelist.net/images/anime/5/87048.jpg")),
                        title = "Kimi no Na Wa"
                    ),
                    AnimeDetails(
                        images = ImageType(jpg = BaseImages(image_url = "https://cdn.myanimelist.net/images/anime/1021/95670.jpg")),
                        title = "Domestic na Kanojo"
                    ),
                    AnimeDetails(
                        images = ImageType(jpg = BaseImages(image_url = "https://cdn.myanimelist.net/images/anime/1935/127974.jpg")),
                        title = "Steins;Gate"
                    ),
                )
            )
        }
    }
}