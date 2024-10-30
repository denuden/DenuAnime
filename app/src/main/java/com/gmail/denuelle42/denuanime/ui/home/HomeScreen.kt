package com.gmail.denuelle42.denuanime.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.data.remote.models.BaseImages
import com.gmail.denuelle42.denuanime.data.remote.models.ImageType
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Genre
import com.gmail.denuelle42.denuanime.data.remote.models.people.People
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
    onNavigation: (route: NavigationScreens) -> Unit
) {
    HomeScreenContent(modifier = Modifier.fillMaxSize())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(modifier: Modifier = Modifier) {
    val lazyListState = rememberLazyListState()
    val animes = listOf(
        AnimeDetails(
            title = "Kaguya sama Love is War",
            images = ImageType(jpg = BaseImages(large_image_url = "https://cdn.myanimelist.net/images/anime/1015/138006l.jpg")),
            genres = listOf(
                Genre(name = "Sci-fi"),
                Genre(name = "Adventure"),
                Genre(name = "Drama"),
                Genre(name = "Suspense"),
                Genre(name = "Suspense"),
                Genre(name = "Suspense"),
            )
        ),
        AnimeDetails(
            title = "Code Geass",
            images = ImageType(jpg = BaseImages(large_image_url = "https://cdn.myanimelist.net/images/anime/1455/146229l.jpg"))
        ),
        AnimeDetails(
            title = "One Punch Man",
            images = ImageType(jpg = BaseImages(large_image_url = "https://cdn.myanimelist.net/images/anime/1208/94745l.jpg"))
        ),
        AnimeDetails(
            title = "Nanatsu no Taizai",
            images = ImageType(jpg = BaseImages(large_image_url = "https://cdn.myanimelist.net/images/anime/1935/127974l.jpg"))
        ),
    )

    //Whole Device wdith subtracted its 1/4
    val configuration = LocalConfiguration.current
    val screenWidthDp = remember { configuration.screenWidthDp.dp }
    val carouselState = rememberCarouselState { animes.count() }

    LazyColumn(
        state = lazyListState,
    ) {
        item {
            val people = listOf(
                People(
                    image = "",
                    name = "Hiroshi Kamiya"
                ),
                People(
                    image = "",
                    name = "Ayane Sakura"
                ),
                People(
                    image = "",
                    name = "Inose Minari"
                ),
                People(
                    image = "",
                    name = "Kayano Ai"
                ),
                People(
                    image = "",
                    name = "Matsuoka Yoshitsugu"
                ),
                People(
                    image = "",
                    name = "Kenjiro Tsuda"
                ),
                People(
                    image = "",
                    name = "Kaji Yuki"
                ),
                People(
                    image = "",
                    name = "Aoi Koga"
                ),
                People(
                    image = "",
                    name = "Ayane Taketatsu"
                ),
                People(
                    image = "",
                    name = "Miku Ito"
                )
            )

            val birthdays = listOf(
                People(
                    image = "",
                    name = "Hiroshi Kamiya",
                    date = "Oct. 24"
                ),
                People(
                    image = "",
                    name = "Ayane Sakura",
                    date = "Oct. 24"
                ),
                People(
                    image = "",
                    name = "Inose Minari",
                    date = "Oct. 24"
                ),
                People(
                    image = "",
                    name = "Kayano Ai",
                    date = "Oct. 24"
                ),
                People(
                    image = "",
                    name = "Matsuoka Yoshitsugu",
                    date = "Oct. 24"
                ),
                People(
                    image = "",
                    name = "Kenjiro Tsuda",
                    date = "Oct. 24"
                ),
                People(
                    image = "",
                    name = "Kaji Yuki",
                    date = "Oct. 24"
                ),
                People(
                    image = "",
                    name = "Aoi Koga",
                    date = "Oct. 24"
                ),
                People(
                    image = "",
                    name = "Ayane Taketatsu",
                    date = "Oct. 24"
                ),
                People(
                    image = "",
                    name = "Miku Ito",
                    date = "Oct. 24"
                )
            )

            /**
             * TOP  PEOPLE SECTION
             */
            PeopleList(
                modifier = Modifier.fillMaxWidth(),
                items = people, title = stringResource(R.string.top_poeple)
            )
            HorizontalDivider(modifier = Modifier.padding(top = 10.dp, end = 16.dp, start = 16.dp))

            /**
             * INCOMING BIRTHDAY  PEOPLE SECTION
             */
            PeopleList(
                modifier = Modifier.fillMaxWidth(),
                items = birthdays, title = stringResource(R.string.incoming_birthdays)
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
            HorizontalMultiBrowseCarousel(
                state = carouselState,
                preferredItemWidth = screenWidthDp,
                itemSpacing = 4.dp,
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) { index ->
                val anime = animes[index]

                DetailedAnimeItemCard(
                    modifier = Modifier
                        .heightIn(min = 400.dp, max = 600.dp),
                    animeDetails = anime
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
                animes = animes
            )
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
        }

        //Scrollable Item
        items(animes) { anime ->
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
            HomeScreenContent()
        }
    }
}

