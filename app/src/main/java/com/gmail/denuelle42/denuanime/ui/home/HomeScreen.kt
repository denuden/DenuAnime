package com.gmail.denuelle42.denuanime.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.data.remote.models.people.People
import com.gmail.denuelle42.denuanime.navigation.NavigationScreens
import com.gmail.denuelle42.denuanime.ui.common.DetailedAnimeItemCard
import com.gmail.denuelle42.denuanime.ui.home.components.CategoriesFilterChip
import com.gmail.denuelle42.denuanime.ui.home.components.PeopleList
import com.gmail.denuelle42.denuanime.ui.home.components.Recommendations
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@Composable
fun HomeScreen(
    onPopBackStack: () -> Unit,
    onNavigation: (route: NavigationScreens) -> Unit
) {
    HomeScreenContent(modifier = Modifier.fillMaxSize() )
}

@Composable
fun HomeScreenContent(modifier: Modifier = Modifier) {
    val state = rememberScrollState()
    val lazyRowState = rememberLazyListState()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState =  lazyRowState)

    Column(
        modifier = modifier.verticalScroll(state = state).fillMaxSize()
    ) {
        PeopleList(modifier = Modifier.fillMaxWidth(),
            items = listOf(
                People(
                    image= "",
                    name = "Hiroshi Kamiya"
                ),
                People(
                    image= "",
                    name = "Ayane Sakura"
                ),
                People(
                    image= "",
                    name = "Inose Minari"
                ),
                People(
                    image= "",
                    name = "Kayano Ai"
                ),
                People(
                    image= "",
                    name = "Matsuoka Yoshitsugu"
                ),
                People(
                    image= "",
                    name = "Kenjiro Tsuda"
                ),
                People(
                    image= "",
                    name = "Kaji Yuki"
                ),
                People(
                    image= "",
                    name = "Aoi Koga"
                ),
                People(
                    image= "",
                    name = "Ayane Taketatsu"
                ),
                People(
                    image= "",
                    name = "Miku Ito"
                ),
            )
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))

        CategoriesFilterChip(modifier = Modifier.fillMaxWidth(), categoryList = listOf("Top", "Upcoming", "All", "Adventure"))


        val animes = listOf(
            AnimeDetails(title = "Kaguya sama Love is War"),
            AnimeDetails(title = "Code Geass"),
            AnimeDetails(title = "One Punch Man"),
            AnimeDetails(title = "Nanatsu no Taizai"),
        )
        LazyRow(
            state =lazyRowState,
            flingBehavior = snapBehavior,
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(animes){ anime ->
                DetailedAnimeItemCard(
                    modifier = Modifier.fillParentMaxWidth().heightIn(min = 350.dp, max = 550.dp),
                    animeDetails = anime
                )
            }
        }
        Text(text = "See more", textDecoration = TextDecoration.Underline, modifier = Modifier.align(Alignment.End).padding(top = 4.dp, end = 8.dp))

        Spacer(modifier = Modifier.padding(vertical = 6.dp))

        Recommendations(modifier = Modifier.fillMaxHeight())
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    DenuAnimeTheme {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            HomeScreenContent()
        }
    }
}

