package com.gmail.denuelle42.denuanime.ui.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Genre
import com.gmail.denuelle42.denuanime.data.remote.models.people.People
import com.gmail.denuelle42.denuanime.data.repositories.anime.request.GetAnimeSearchRequest
import com.gmail.denuelle42.denuanime.data.repositories.anime.request.GetTopAnimeRequest
import com.gmail.denuelle42.denuanime.navigation.NavigationScreens
import com.gmail.denuelle42.denuanime.ui.common.AnimeListItemCard
import com.gmail.denuelle42.denuanime.ui.common.DetailedAnimeItemCard
import com.gmail.denuelle42.denuanime.ui.common.FilterDropdown
import com.gmail.denuelle42.denuanime.ui.common.skeleton.SkeletonAnimeDetailsCard
import com.gmail.denuelle42.denuanime.ui.common.skeleton.SkeletonGenreList
import com.gmail.denuelle42.denuanime.ui.common.skeleton.SkeletonPeopleList
import com.gmail.denuelle42.denuanime.ui.home.components.CategoriesFilterChip
import com.gmail.denuelle42.denuanime.ui.home.components.EpisodesAndSeasonsTab
import com.gmail.denuelle42.denuanime.ui.home.components.PeopleList
import com.gmail.denuelle42.denuanime.ui.home.components.Recommendations
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.gmail.denuelle42.denuanime.utils.calculateScrolledDistance

@Composable
fun HomeScreen(
    onPopBackStack: () -> Unit,
    onNavigation: (route: NavigationScreens) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeScreenState by viewModel.homeScreenState.collectAsStateWithLifecycle()
    val peopleState by viewModel.peopleState.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    LazyColumn(state = lazyListState) {
        item {
            TopPeopleSection(isLoading = peopleState.isGetTopPeopleSearchLoading, topPeopleList = peopleState.topPeopleList.orEmpty())
        }
        item{
            AnimeGenresSection(
                onEvent = viewModel::onEvent,
                isLoading = homeScreenState.isGetAnimeGenresLoading,
                genres = homeScreenState.animeGenres.orEmpty()
            )
        }
        item{
            AnimeCardListSection(
                isLoading = homeScreenState.isGetAnimeListLoading,
                animeList = homeScreenState.animeList.orEmpty(),
                click = {viewModel.onEvent(HomeScreenEvents.OnGetTopAnime(GetTopAnimeRequest()))}
            )
        }
        item{
            RecommendationsSection(
                onEvent = viewModel::onEvent,
                list = homeScreenState.animeRecommendationsShown.orEmpty()
            )
        }
        item{
            RecentEpisodesAndSeasonsSection(animeList = homeScreenState.animeList.orEmpty())
        }
        items(homeScreenState.animeList.orEmpty()){ anime ->
            AnimeListItemCard(
                animeDetails = anime,
                modifier = Modifier.padding(vertical = 3.dp, horizontal = 8.dp)
            )
        }

    }
}

@Composable
fun TopPeopleSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    topPeopleList: List<People>
) {
    /**
     * TOP  PEOPLE SECTION
     */
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = !isLoading
    ) {
        PeopleList(
            modifier = modifier.fillMaxWidth(),
            items = topPeopleList,
            title = stringResource(R.string.top_poeple),
            shouldShowBirthDate = true
        )
    }
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = isLoading
    ) {
        SkeletonPeopleList(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
    HorizontalDivider(modifier = Modifier.padding(top = 10.dp, end = 16.dp, start = 16.dp))
}

@Composable
fun AnimeGenresSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    genres: List<Genre>,
    onEvent: (HomeScreenEvents) -> Unit
) {
    val selectedGenreIsTopOrUpcoming by remember { mutableIntStateOf(-1) }
    /**
     * CATEGORIES SECTION
     */
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(start = 6.dp)
    ) {
        FilterDropdown(
            shape = RectangleShape,
            buttonLabel = "Filter",
            typeLabel = "Type",
            secondaryTypeLabel = "Rating",
            type = listOf(
                "All",
                "TV",
                "Movie",
                "OVA",
                "Special",
                "ONA",
                "Music",
                "CM",
                "PV",
                "TV Special"
            ),
            secondaryType = listOf(
                "All",
                "G",
                "PG",
                "PG-13",
                "R-17+",
                "R-Mild Nudity",
                "Rx-Hentai"
            ),
        ) { type, secondaryType ->
            //Recall Get Anime
            onEvent(
                HomeScreenEvents.OnChangeMainAnimeListFilter(
                    type = type,
                    rating = secondaryType.orEmpty()
                ) {
                    if (selectedGenreIsTopOrUpcoming == -1) {
                        onEvent(HomeScreenEvents.OnGetTopAnime(GetTopAnimeRequest(filter = "favorite", type = type, rating = secondaryType)))
                        return@OnChangeMainAnimeListFilter
                    }
                    if (selectedGenreIsTopOrUpcoming == -2) {
                        onEvent(
                            HomeScreenEvents.OnGetAnimeSearch(
                                GetAnimeSearchRequest(
                                    status = "upcoming",
                                    order_by = "popularity",
                                    type = type,
                                    rating = secondaryType
                                )
                            )
                        )
                        return@OnChangeMainAnimeListFilter
                    }
                    if (selectedGenreIsTopOrUpcoming > 0) {
                        val formattedGenres = genres.filter { it.isSelected }
                            .joinToString(separator = ",") { it.mal_id.toString() }
                        onEvent(HomeScreenEvents.OnGetAnimeSearch(GetAnimeSearchRequest(genres = formattedGenres, type = type, rating = secondaryType)))
                        return@OnChangeMainAnimeListFilter
                    }
                }
            )
        }
        VerticalDivider(
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .height(ButtonDefaults.MinHeight)
                .align(Alignment.CenterVertically)
        )

        /**
         * Anime Genres
         */
        AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            visible = !isLoading
        ) {
            CategoriesFilterChip(
                modifier = Modifier.fillMaxWidth(),
                categoryList = genres
            ) { genre ->
                if (genre.mal_id == null) {
                    //TODO show toast
                    return@CategoriesFilterChip
                }
                onEvent(HomeScreenEvents.OnSelectAnimeGenre(genre))
                if (genre.mal_id == -1) {
                    onEvent(HomeScreenEvents.OnGetTopAnime(GetTopAnimeRequest(filter = "favorite")))
                    return@CategoriesFilterChip
                }
                if (genre.mal_id == -2) {
                    onEvent(
                        HomeScreenEvents.OnGetAnimeSearch(
                            GetAnimeSearchRequest(
                                status = "upcoming",
                                order_by = "popularity"
                            )
                        )
                    )
                    return@CategoriesFilterChip
                }
                if (genre.mal_id > 0) {
                    val formattedGenres = genres.filter { it.isSelected }
                        .joinToString(separator = ",") { it.mal_id.toString() }
                    onEvent(HomeScreenEvents.OnGetAnimeSearch(GetAnimeSearchRequest(genres = formattedGenres)))
                    return@CategoriesFilterChip
                }
            }
        }
        AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            visible = isLoading
        ) {
            SkeletonGenreList(modifier = Modifier.fillMaxWidth())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeCardListSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    animeList: List<AnimeDetails>,
    click : () -> Unit
) {
    //Whole Device wdith subtracted its 1/4
    val configuration = LocalConfiguration.current
    val screenWidthDp = remember { configuration.screenWidthDp.dp }

    var carouselCount by remember { mutableIntStateOf(animeList.size) }
    val carouselState = rememberCarouselState(0) { carouselCount }
    val screenWidthPx =  with(LocalDensity.current) { screenWidthDp.toPx() }

    LaunchedEffect(carouselCount) {
        carouselState.animateScrollBy(-calculateScrolledDistance(screenWidthPx, carouselCount))
    }

    LaunchedEffect(animeList) {
        carouselCount = animeList.size
    }
    /**
     * ANIME CARD SECTION
     */
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = !isLoading
    ) {
        if (animeList.isNotEmpty()) {
            Column(modifier = modifier) {
                HorizontalMultiBrowseCarousel(
                    state = carouselState,
                    preferredItemWidth = screenWidthDp,
                    itemSpacing = 4.dp,
                    contentPadding = PaddingValues(horizontal = 8.dp),
                ) { index ->
                    val anime = animeList[index]
                    DetailedAnimeItemCard(
                        animeDetails = anime,
                        modifier = Modifier
                            .heightIn(min = 400.dp, max = 600.dp)
                            .clickable {
                                click()
                            }
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
        } else {
//                    TODO PUT NOTHING TO SHOW DETAILS
        }
    }
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = isLoading
    ) {
        SkeletonAnimeDetailsCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .size(width = screenWidthDp, height = 500.dp)
        )
    }
    Spacer(modifier = Modifier.padding(vertical = 10.dp))
}

@Composable
fun RecommendationsSection(modifier: Modifier = Modifier, onEvent: (HomeScreenEvents) -> Unit, list: List<AnimeDetails>) {
    Log.d("recommendation", list.toString())
    var currentStartPage by remember { mutableIntStateOf(0) }
    /**
     * RECOMMENDATIONS SECTION
     */
    Recommendations(
        onSelectAnimeTab = {},
        onSelectMangaTab = {},
        onClickPrevButton = {
            currentStartPage -= 7
            onEvent(HomeScreenEvents.OnSelectPreviousAnimeRecommendations(currentStartPage))
        },
        onClickNextButton = {
           currentStartPage +=7
            onEvent(HomeScreenEvents.OnSelectNextAnimeRecommendations(currentStartPage))
        },
        list = list.map {
            Pair(it.images?.jpg?.image_url.orEmpty(), it.title ?: "Unknown Title")
        },
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = 8.dp)
    )
    Spacer(modifier = Modifier.padding(vertical = 12.dp))
}

@Composable
fun RecentEpisodesAndSeasonsSection(modifier: Modifier = Modifier,animeList: List<AnimeDetails>) {
    /**
     * EPISODES AND SEASONS SECTION
     */
    EpisodesAndSeasonsTab(
        modifier = modifier.padding(horizontal = 8.dp),
        animes = emptyList()
    )
    Spacer(modifier = Modifier.padding(bottom = 8.dp))
}


enum class BorderSide {
    Top, Bottom, Left, Right
}

@Preview
@Composable
private fun HomeScreenPreview() {
    DenuAnimeTheme {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {

        }
    }
}

