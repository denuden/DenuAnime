package com.gmail.denuelle42.denuanime.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.data.remote.models.people.People
import com.gmail.denuelle42.denuanime.navigation.NavigationScreens
import com.gmail.denuelle42.denuanime.ui.home.components.CategoriesFilterChip
import com.gmail.denuelle42.denuanime.ui.home.components.PeopleList
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
    Column(
        modifier = modifier.verticalScroll(state = state)
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

