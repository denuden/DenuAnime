package com.gmail.denuelle42.denuanime.ui.home

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Genre
import com.gmail.denuelle42.denuanime.data.remote.models.people.People
import com.gmail.denuelle42.denuanime.data.repositories.anime.request.GetTopAnimeRequest
import com.gmail.denuelle42.denuanime.data.repositories.season.request.GetSeasonNowRequest
import com.gmail.denuelle42.denuanime.data.repositories.season.request.GetSeasonUpcomingRequest
import com.gmail.denuelle42.denuanime.navigation.NavigationScreens
import com.gmail.denuelle42.denuanime.navigation.PeopleScreens
import com.gmail.denuelle42.denuanime.ui.common.AnimeListItemCard
import com.gmail.denuelle42.denuanime.ui.common.DetailedAnimeItemCard
import com.gmail.denuelle42.denuanime.ui.common.FilterDropdown
import com.gmail.denuelle42.denuanime.ui.common.skeleton.SkeletonAnimeDetailsCard
import com.gmail.denuelle42.denuanime.ui.common.skeleton.SkeletonEpisodesAndSeasonsList
import com.gmail.denuelle42.denuanime.ui.common.skeleton.SkeletonGenreList
import com.gmail.denuelle42.denuanime.ui.common.skeleton.SkeletonPeopleList
import com.gmail.denuelle42.denuanime.ui.common.skeleton.SkeletonRecommendationsList
import com.gmail.denuelle42.denuanime.ui.home.components.CategoriesFilterChip
import com.gmail.denuelle42.denuanime.ui.home.components.EpisodesAndSeasonsTab
import com.gmail.denuelle42.denuanime.ui.home.components.PeopleList
import com.gmail.denuelle42.denuanime.ui.home.components.Recommendations
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.gmail.denuelle42.denuanime.utils.OneTimeEvents
import com.gmail.denuelle42.denuanime.utils.calculateScrolledDistance

@Composable
fun HomeScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (route: NavigationScreens) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeScreenState by viewModel.homeScreenState.collectAsStateWithLifecycle()
    val peopleState by viewModel.peopleState.collectAsStateWithLifecycle()

    val lifecycle  = LocalLifecycleOwner.current.lifecycle
    val context = LocalContext.current
    HomeScreenContent(
        homeScreenState = homeScreenState,
        peopleState = peopleState,
        onEvent = viewModel::onEvent,
        currentStartPage = viewModel.currentStartPage.intValue,
        updateCurrentStartPage = { viewModel.updateCurrentStartPage(it) },
        selectedEpisodesAndSeasonTab = viewModel.getSelectedEpisodesAndSeasonTab()
    )

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            viewModel.channel.collect { event ->
                when(event){
                    is OneTimeEvents.OnNavigate -> onNavigate(event.route)
                    OneTimeEvents.OnPopBackStack -> onPopBackStack()
                    is OneTimeEvents.ShowSnackbar -> {
                        TODO()
                    }
                    is OneTimeEvents.ShowToast -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    homeScreenState: HomeScreenState,
    peopleState: HomeScreenState,
    onEvent: (HomeScreenEvents) -> Unit,
    currentStartPage: Int,//For Recommendations Section
    updateCurrentStartPage : (Int) -> Unit,//For Recommendations Section
    selectedEpisodesAndSeasonTab : Int

) {
    val lazyListState = rememberLazyListState()

    LazyColumn(state = lazyListState, modifier = modifier) {
        item {
            TopPeopleSection(isLoading = peopleState.isGetTopPeopleSearchLoading, topPeopleList = peopleState.topPeopleList.orEmpty(), onClickSeeMore = {
                onEvent(HomeScreenEvents.OnNavigateToSeeMorePeople(PeopleScreens.PeopleNavigation))
            })
        }
        item{
            AnimeGenresSection(
                onEvent = onEvent,
                isLoading = homeScreenState.isGetAnimeGenresLoading,
                genres = homeScreenState.animeGenres.orEmpty(),
            )
        }
        item{
            AnimeCardListSection(
                isLoading = homeScreenState.isGetAnimeListLoading,
                animeList = homeScreenState.animeList.orEmpty(),
                click = {onEvent(HomeScreenEvents.OnGetTopAnime(GetTopAnimeRequest()))}
            )
        }
        item{
            RecommendationsSection(
                onEvent = onEvent,
                list = homeScreenState.recommendationsShown.orEmpty(),
                maxRecommendationsListSize = homeScreenState.recommendationsList.orEmpty().size,
                isLoading = homeScreenState.isGetRecentRecommendationsLoading,
                currentStartPage = currentStartPage,
                updateCurrentStartPage = { updateCurrentStartPage(it) }
            )
        }

        // ========= START OF RELATED COMPONENTS ==============
        item{
            /**
             * EPISODES AND SEASONS SECTION
             */
            EpisodesAndSeasonsTab(
                modifier = Modifier.padding(horizontal = 8.dp),
                state = selectedEpisodesAndSeasonTab
            ){ tabIndex ->
                when(tabIndex){
                    0 -> onEvent(HomeScreenEvents.OnGetRecentEpisodes)
                    1 -> onEvent(HomeScreenEvents.OnGetSeasonNow(GetSeasonNowRequest(continuing = true)))
                    2 -> onEvent(HomeScreenEvents.OnGetSeasonUpcoming(
                        GetSeasonUpcomingRequest()
                    ))
                }
            }
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
        }

        if (!homeScreenState.isEpisodesAndSeasonsLoading){
            items(homeScreenState.episodesAndSeasonsList.orEmpty()){ anime ->
                AnimeListItemCard(
                    animeDetails = anime.entry ?: AnimeDetails(),
                    modifier = Modifier.padding(vertical = 3.dp, horizontal = 8.dp),
                    recentEpisodesList = anime.episodes.orEmpty()
                )
            }
        } else {
            items(5){
                SkeletonEpisodesAndSeasonsList(modifier =Modifier.padding(vertical = 2.dp, horizontal = 8.dp))
            }
        }

        // ========= END OF RELATED COMPONENTS ==============
    }
}

@Composable
fun TopPeopleSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    topPeopleList: List<People>,
    onClickSeeMore: () -> Unit
) {
    /**
     * TOP  PEOPLE SECTION
     */
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = !isLoading
    ) {
        if(topPeopleList.isNotEmpty()){
            PeopleList(
                modifier = modifier.fillMaxWidth(),
                items = topPeopleList,
                title = stringResource(R.string.top_poeple),
                shouldShowBirthDate = true,
                onClickSeeMore = onClickSeeMore
            )
        }else{
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth().background(
                    color = Color.LightGray,
                    shape = MaterialTheme.shapes.small
                )
                    .height(52.dp)
            ){
              Text(text = "No People Found")
            }
        }
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
            onEvent(HomeScreenEvents.OnChangeAnimeFilters(type =  type, rating = secondaryType.orEmpty()))
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


    LaunchedEffect(animeList) {
        carouselCount = animeList.size
        carouselState.animateScrollBy(-calculateScrolledDistance(screenWidthPx, carouselCount))
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

                    if(index < animeList.size){
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
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                .height(400.dp)
                .fillMaxWidth()
                .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)){
                Text(text = stringResource(R.string.no_anime_found))
            }
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
fun RecommendationsSection(
    modifier: Modifier = Modifier,
    onEvent: (HomeScreenEvents) -> Unit,
    list: List<AnimeDetails>,
    maxRecommendationsListSize: Int,
    isLoading: Boolean,
    currentStartPage : Int,
    updateCurrentStartPage : (Int) -> Unit
) {
    /**
     * RECOMMENDATIONS SECTION
     */
    var state by remember { mutableIntStateOf(0) }
    val titles by remember { mutableStateOf( listOf("Anime", "Manga")) }

    Column(modifier = modifier
        .clip(MaterialTheme.shapes.extraSmall)
        .padding(horizontal = 8.dp)) {
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            titles.forEachIndexed { index, title ->
                Log.d("wegw", state.toString())

                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = titles.size,
                        baseShape = MaterialTheme.shapes.small
                    ),
                    onClick = {
                        state = index
                        if (index == 0) { //anime tab
                            onEvent(HomeScreenEvents.OnGetAnimeRecommendations)
                            updateCurrentStartPage(0)
                        } else if (index == 1) { //manga tab
                            onEvent(HomeScreenEvents.OnGetMangaRecommendations)
                            updateCurrentStartPage(0)
                        }
                    },
                    selected = index == state,
                    border = BorderStroke(width = 1.dp, color = Color.Gray)
                ) {
                    Text(title)
                }
            }
        }

        AnimatedVisibility(
            visible = !isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Recommendations(
                isPrevButtonDisabled = currentStartPage > 0,
                isNextButtonDisabled = currentStartPage < maxRecommendationsListSize,
                onClickPrevButton = {
                    onEvent(HomeScreenEvents.OnSelectPreviousAnimeRecommendations(currentStartPage))
                    updateCurrentStartPage(currentStartPage - 7)//update local state after viewmodel process
                },
                onClickNextButton = {
                    onEvent(HomeScreenEvents.OnSelectNextAnimeRecommendations(currentStartPage))
                    updateCurrentStartPage(currentStartPage + 7)//update local state after viewmodel process

                },
                list = list.map {
                    Pair(it.images?.jpg?.image_url.orEmpty(), it.title ?: "Unknown Title")
                },
            )
        }

        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            SkeletonRecommendationsList(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp))
        }
    }

    Spacer(modifier = Modifier.padding(vertical = 12.dp))
}



@Preview
@Composable
private fun HomeScreenPreview() {
    DenuAnimeTheme {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {

        }
    }
}

