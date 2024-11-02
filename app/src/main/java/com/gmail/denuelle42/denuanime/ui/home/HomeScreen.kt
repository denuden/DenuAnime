package com.gmail.denuelle42.denuanime.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.navigation.NavigationScreens
import com.gmail.denuelle42.denuanime.ui.common.AnimeListItemCard
import com.gmail.denuelle42.denuanime.ui.common.DetailedAnimeItemCard
import com.gmail.denuelle42.denuanime.ui.common.FilterDropdown
import com.gmail.denuelle42.denuanime.ui.home.components.CategoriesFilterChip
import com.gmail.denuelle42.denuanime.ui.home.components.EpisodesAndSeasons
import com.gmail.denuelle42.denuanime.ui.home.components.PeopleList
import com.gmail.denuelle42.denuanime.ui.home.components.Recommendations
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@Composable
fun HomeScreen(
    onPopBackStack: () -> Unit,
    onNavigation: (route: NavigationScreens) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val peopleState by viewModel.peopleState.collectAsState()
    val topAnimeState by viewModel.topAnimeState.collectAsState()
    HomeScreenContent(
        peopleState = peopleState,
        topAnimeState = topAnimeState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(modifier: Modifier = Modifier, peopleState : HomeScreenState, topAnimeState : HomeScreenState) {
    val lazyListState = rememberLazyListState()

    //Whole Device wdith subtracted its 1/4
    val configuration = LocalConfiguration.current
    val screenWidthDp = remember { configuration.screenWidthDp.dp }
    val carouselState = rememberCarouselState { 25 }

    LazyColumn(
        state = lazyListState,
        modifier = modifier
    ) {
        item {
            /**
             * TOP  PEOPLE SECTION
             */
            PeopleList(
                modifier = Modifier.fillMaxWidth(),
                items = peopleState.topPeopleList ?: emptyList(), title = stringResource(R.string.top_poeple),
                shouldShowBirthDate = true
            )
            HorizontalDivider(modifier = Modifier.padding(top = 10.dp, end = 16.dp, start = 16.dp))

            /**
             * CATEGORIES SECTION
             */
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 6.dp)
            ) {
                FilterDropdown(
                    shape = RectangleShape,
                    label = "Type",
                    type = listOf("TV", "Movie", "Series"),
                ) {
//                TODO Selected Filter Type
                }
                VerticalDivider(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .height(ButtonDefaults.MinHeight)
                        .align(Alignment.CenterVertically)
                )
                CategoriesFilterChip(
                    modifier = Modifier.fillMaxWidth(),
                    categoryList = listOf("Top", "Upcoming", "All", "Adventure")
                )
            }


            /**
             * ANIME CARD SECTION
             */
            if(topAnimeState.topAnimeList?.isNotEmpty() == true){
                HorizontalMultiBrowseCarousel(
                    state = carouselState,
                    preferredItemWidth = screenWidthDp,
                    itemSpacing = 4.dp,
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) { index ->
                    val anime = topAnimeState.topAnimeList?.get(index)
                    DetailedAnimeItemCard(
                        modifier = Modifier
                            .heightIn(min = 400.dp, max = 600.dp),
                        animeDetails = anime ?: AnimeDetails()
                    )
                }
                Text(
                    text = "See more",
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Light,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, end = 12.dp)
                )
            }


            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            /**
             * RECOMMENDATIONS SECTION
             */
            Recommendations(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.padding(vertical = 12.dp))

            /**
             * EPISODES AND SEASONS SECTION
             */
            EpisodesAndSeasons(
                modifier = Modifier .padding(horizontal = 8.dp),
                animes = topAnimeState.topAnimeList ?: emptyList()
            )
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
        }

        //Scrollable Item
        items(topAnimeState.topAnimeList ?: emptyList()) { anime ->
            AnimeListItemCard(animeDetails = anime, modifier = Modifier.padding(vertical = 3.dp, horizontal = 8.dp))
        }
    }
}

enum class BorderSide {
    Top, Bottom, Left, Right
}

@Preview
@Composable
private fun HomeScreenPreview() {
    DenuAnimeTheme {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            HomeScreenContent(
                peopleState = HomeScreenState(),
                topAnimeState = HomeScreenState(),
                )
        }
    }
}

