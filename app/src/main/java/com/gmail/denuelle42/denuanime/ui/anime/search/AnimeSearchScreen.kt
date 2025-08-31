package com.gmail.denuelle42.denuanime.ui.anime.search

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.navigation.NavigationScreens
import com.gmail.denuelle42.denuanime.ui.anime.components.FullSearchFilters
import com.gmail.denuelle42.denuanime.ui.common.FilterDropdown
import com.gmail.denuelle42.denuanime.ui.common.cards.AnimeItemCard
import com.gmail.denuelle42.denuanime.ui.common.cards.AnimeListItemCard
import com.gmail.denuelle42.denuanime.ui.common.cards.DetailedAnimeListItemCard
import com.gmail.denuelle42.denuanime.ui.common.dialog.ErrorDialog
import com.gmail.denuelle42.denuanime.ui.common.dialog.ModalBottomSheetDialog
import com.gmail.denuelle42.denuanime.ui.common.skeleton.SkeletonAnimeList
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.gmail.denuelle42.denuanime.utils.CoroutineHelper
import com.gmail.denuelle42.denuanime.utils.ObserveAsEvents
import com.gmail.denuelle42.denuanime.utils.OneTimeEvents
import com.gmail.denuelle42.denuanime.utils.SnackBarController
import com.gmail.denuelle42.denuanime.utils.clickableDelayed
import com.gmail.denuelle42.denuanime.utils.handleInputError
import kotlinx.coroutines.launch

@Composable
fun AnimeSearchScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (route: NavigationScreens) -> Unit,
    viewModel: AnimeSearchScreenViewModel = hiltViewModel()
) {
    val animeSearchScreenState by viewModel.stateFlow.collectAsStateWithLifecycle()

    AnimeSearchScreenContent(
        modifier = Modifier.fillMaxSize(),
        state = animeSearchScreenState,
        onEvent = viewModel::onEvent
    )

    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ErrorDialog(
        text = errorMessage,
        showDialog = showErrorDialog
    ) {
        showErrorDialog = false
    }

    //One time events listener
    ObserveAsEvents(flow = viewModel.channel) { event ->
        when (event) {
            is OneTimeEvents.OnNavigate -> onNavigate(event.route)
            OneTimeEvents.OnPopBackStack -> onPopBackStack()
            is OneTimeEvents.ShowSnackbar -> {
                scope.launch {
                    SnackBarController.sendEvent(event.snackbarEvent)
                }
            }

            is OneTimeEvents.ShowToast -> {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }

            is OneTimeEvents.ShowInputError -> {
                showErrorDialog = true
                errorMessage = handleInputError(event.errors)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeSearchScreenContent(
    modifier: Modifier = Modifier,
    state: AnimeSearchScreenState,
    onEvent: (AnimeSearchScreenEvents) -> Unit
) {
    var searchState by remember { mutableStateOf("") }
    val lazyState = rememberLazyListState()
    var expanded by remember { mutableStateOf(false) }

    var showFilterDialog by remember { mutableStateOf(false) }
    val recentSearches = remember { mutableStateListOf<String>() }

    var selectedListViewType by remember { mutableIntStateOf(0) }

    val coroutineScope = rememberCoroutineScope()
    val coroutineHelper = remember { CoroutineHelper(coroutineScope) }

    //Runs only once, initializer of state
    LaunchedEffect(Unit) {
        //set State for filters
        onEvent(
            AnimeSearchScreenEvents.OnSetInitialState(
                AnimeSearchScreenState(
                )
            )
        )
    }

    //shows filters
    ModalBottomSheetDialog(
        showDialog = showFilterDialog,
        rightSideContent = { modalModifier ->
            TextButton(
                modifier = modalModifier,
                onClick = {
                    onEvent(AnimeSearchScreenEvents.OnSetLoadingSearchAnime)

                    //trigger search anime after debouncing delay
                    coroutineHelper.debouncer {
                        onEvent(AnimeSearchScreenEvents.OnSearchAnime)
                    }
                    showFilterDialog = false
                }
            ) {
                Text(
                    text = stringResource(R.string.btn_apply),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        leftSideContent = { modalModifier ->
            TextButton(
                modifier = modalModifier,
                onClick = {
                    onEvent(
                        AnimeSearchScreenEvents.OnSetInitialState(
                            AnimeSearchScreenState(
                                typeFilter = null,
                                searchQuery = null,
                                scoreFilter = null,
                                minScoreFilter = null,
                                maxScoreFilter = null,
                                statusFilter = null,
                                ratingFilter = null,
                                sfwFilter = null,
                                toggleScoreFilter = false,
                                genreList = null,
                                isGetGenreLoading = false,
                            )
                        )
                    )
                    showFilterDialog = false
                }
            ) {
                Text(
                    text = stringResource(R.string.btn_reset_filters),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        onDismissRequest = {
            showFilterDialog = false
        }) {
        FullSearchFilters(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
            animeSearchScreenState = state,
            onEvent = onEvent
        )
    }

    //shows results
    Box(
        Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            inputField = {
                SearchBarDefaults.InputField(
                    query = searchState,
                    onQueryChange = {
                        searchState = it
                        onEvent(AnimeSearchScreenEvents.OnSetLoadingSearchAnime)
                        onEvent(AnimeSearchScreenEvents.OnChangeSearchQuery(searchState))
                        coroutineHelper.debouncer(delayMs = 1000) {
                            onEvent(AnimeSearchScreenEvents.OnSearchAnime)
                        }
                    },
                    onSearch = {
                        onEvent(AnimeSearchScreenEvents.OnSetLoadingSearchAnime)
                        expanded = false
                        onEvent(AnimeSearchScreenEvents.OnChangeSearchQuery(searchState))
                        onEvent(AnimeSearchScreenEvents.OnSearchAnime)
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text(stringResource(R.string.hint_search_anime)) },
                    leadingIcon = {
                        IconButton(onClick = {
                            showFilterDialog = true
                        }) {
                            Icon(Icons.Default.FilterAlt, contentDescription = null)
                        }
                    },
                    trailingIcon = {
                        //If search bar is expanded, show close icon to close it
                        //else show menu icon for filter
                        if (expanded) {
                            IconButton(
                                onClick = { expanded = false }
                            ) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = null)
                            }
                        } else {
                            FilterDropdown(
                                icon = Icons.Default.MoreVert,
                                type = listOf("Card Row", "Card Thumbnail", "Card List"),
                                onFilterClick = { typeIndex, _ ->
                                    selectedListViewType = typeIndex
                                }
                            )
                        }
                    },
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            //Recent Searches
            Column(Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    text = stringResource(R.string.label_recent_searches),
                    modifier = Modifier.height(4.dp)
                )
                repeat(recentSearches.size) { index ->
                    val resultText = recentSearches[index]
                    ListItem(
                        headlineContent = { Text(resultText) },
                        supportingContent = { Text("01.24.25") },
                        leadingContent = { Icon(Icons.Filled.Schedule, contentDescription = null) },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        modifier =
                            Modifier
                                .clickable {
                                    expanded = false
                                }
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                    )
                }
            }
        }

        val list = state.animeList

        //================== List of Searches ====================

        //Only visible for non thumbnail view type
        AnimatedVisibility(
            visible = selectedListViewType != ViewTypes.VIEW_TYPE_CARD_THUMBNAIL && list.orEmpty()
                .isNotEmpty() && !state.isGetAnimeSearchLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyColumn(
                state = lazyState,
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 72.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier,
            ) {
                when (selectedListViewType) {
                    ViewTypes.VIEW_TYPE_CARD_ROW -> {
                        cardRow(list.orEmpty()){
                            onEvent(AnimeSearchScreenEvents.OnNavigateToAnimeDetails(it))
                        }
                    }

                    ViewTypes.VIEW_TYPE_LIST -> {
                        cardList(list.orEmpty()){
                            onEvent(AnimeSearchScreenEvents.OnNavigateToAnimeDetails(it))
                        }
                    }
                }
            }
        }

        //Only visible for thumbnail view type (Grid cells)
        AnimatedVisibility(
            visible = selectedListViewType == ViewTypes.VIEW_TYPE_CARD_THUMBNAIL && list.orEmpty()
                .isNotEmpty() && !state.isGetAnimeSearchLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(top = 72.dp, bottom = 16.dp),
            ) {
                cardThumbnail(list.orEmpty()){
                    onEvent(AnimeSearchScreenEvents.OnNavigateToAnimeDetails(it))
                }
            }
        }

        //if list is loading
        AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            visible = state.isGetAnimeSearchLoading
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 72.dp)
            ) {
                repeat(5) {
                    SkeletonAnimeList(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

        }

        //if list is empty
        AnimatedVisibility(
            visible = list.isNullOrEmpty() && !state.isGetAnimeSearchLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 16.dp, top = 72.dp, end = 16.dp)
                    .height(400.dp)
                    .fillMaxWidth()
                    .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
            ) {
                Text(text = stringResource(R.string.text_no_search_results_found))
            }
        }

    }
}

/**
 * View Type Card Row
 */
fun LazyListScope.cardRow(list: List<AnimeDetails>, onClickItem : (Int) -> Unit) {
    items(list) {
        DetailedAnimeListItemCard(
            animeDetails = it,
            modifier = Modifier
                .clickableDelayed {
                    onClickItem(it.mal_id ?: 0)
                }
        )
        Spacer(Modifier.height(4.dp))
    }
}

/**
 * View Type Card Thumbnail
 */
fun LazyGridScope.cardThumbnail(list: List<AnimeDetails>, onClickItem : (Int) -> Unit) {
    items(list) {
        AnimeItemCard(
            image = it.images?.jpg?.medium_image_url.orEmpty(),
            title = it.title_japanese.orEmpty(),
            modifier = Modifier
                .clickableDelayed {
                    onClickItem(it.mal_id ?: 0)
                }
        )
    }
}

fun LazyListScope.cardList(list: List<AnimeDetails>, onClickItem : (Int) -> Unit) {
    items(list) {
        AnimeListItemCard(
            animeDetails = it,
            onClick = { id ->
                onClickItem(id)
            }
        )
    }
}

object ViewTypes {
    const val VIEW_TYPE_CARD_ROW = 0
    const val VIEW_TYPE_CARD_THUMBNAIL = 1
    const val VIEW_TYPE_LIST = 2
}

@Preview
@Composable
private fun AnimeSearchScreenPreview() {
    DenuAnimeTheme {
        Surface(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)
        ) {
            AnimeSearchScreenContent(
                state = AnimeSearchScreenState(),
                onEvent = {}
            )
        }
    }
}