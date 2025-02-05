package com.gmail.denuelle42.denuanime.ui.anime

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.navigation.NavigationScreens
import com.gmail.denuelle42.denuanime.ui.anime.components.FullSearchFilters
import com.gmail.denuelle42.denuanime.ui.common.FilterDropdown
import com.gmail.denuelle42.denuanime.ui.common.cards.AnimeItemCard
import com.gmail.denuelle42.denuanime.ui.common.cards.AnimeListItemCard
import com.gmail.denuelle42.denuanime.ui.common.cards.DetailedAnimeListItemCard
import com.gmail.denuelle42.denuanime.ui.common.dialog.ModalBottomSheetDialog
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme

@Composable
fun AnimeSearchScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (route: NavigationScreens) -> Unit
) {

    AnimeSearchScreenContent(modifier = Modifier.fillMaxSize())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeSearchScreenContent(modifier: Modifier = Modifier) {
    var searchState by remember { mutableStateOf("") }
    val lazyState = rememberLazyListState()
    var expanded by remember { mutableStateOf(false) }

    var showFilterDialog by remember { mutableStateOf(false) }
    val recentSearches = listOf(
        "Shingeki", "Good life anime", "spice of life"
    )
    var selectedListViewType by remember { mutableStateOf(ViewTypes.VIEW_TYPE_CARD_ROW) }

    ModalBottomSheetDialog(showDialog = showFilterDialog, onDismissRequest = {
        showFilterDialog = false
    }) {
        FullSearchFilters(modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp))
    }

    Box(
        Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }) {
        SearchBar(
            modifier = Modifier.align(Alignment.TopCenter),
            inputField = {
                SearchBarDefaults.InputField(
                    query = searchState,
                    onQueryChange = {
                        searchState = it
                    },
                    onSearch = { expanded = false },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Search Anime") },
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
                        if(expanded){
                            IconButton(
                                onClick = {expanded = false}
                            ) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = null)
                            }
                        }else {
                            FilterDropdown(
                                icon = Icons.Default.MoreVert,
                                type = listOf(ViewTypes.VIEW_TYPE_CARD_ROW, ViewTypes.VIEW_TYPE_CARD_THUMBNAIL, ViewTypes.VIEW_TYPE_LIST),
                                onFilterClick = { type, _ ->
                                    selectedListViewType = type
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
                    text = "Recent Searches",
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

        val list = List(0) { "Text $it" }

        //================== List of Searches ====================

        //Only visible for non thumbnail view type
        AnimatedVisibility(
            visible = selectedListViewType != ViewTypes.VIEW_TYPE_CARD_THUMBNAIL && list.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyColumn(
                state = lazyState,
                contentPadding = PaddingValues(start = 16.dp, top = 72.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier,
            ) {
                when (selectedListViewType) {
                    ViewTypes.VIEW_TYPE_CARD_ROW -> {
                        cardRow(list)
                    }
                    ViewTypes.VIEW_TYPE_LIST -> {
                        cardList(list)
                    }
                }
            }
        }

        //Only visible for thumbnail view type (Grid cells)
        AnimatedVisibility(
            visible = selectedListViewType == ViewTypes.VIEW_TYPE_CARD_THUMBNAIL && list.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(top = 72.dp, bottom = 16.dp),
            ) {
                cardThumbnail(list)
            }
        }

        //if list is empty
        AnimatedVisibility(
            visible = list.isEmpty(),
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
                Text(text = stringResource(R.string.no_search_results_found))
            }
        }

    }
}

/**
 * View Type Card Row
 */
fun LazyListScope.cardRow(list : List<String>){
    items(list) {
        DetailedAnimeListItemCard()
        Spacer(Modifier.height(4.dp))
    }
}

/**
 * View Type Card Thumbnail
 */
fun LazyGridScope.cardThumbnail(list : List<String>){
    items(list){
        AnimeItemCard(
            image = "sample",
            title = it,
        )
    }
}

fun LazyListScope.cardList(list : List<String>){
    items(list) {
        AnimeListItemCard(
            animeDetails = AnimeDetails(),
            onClick = {
            }
        )
    }
}

object ViewTypes {
    const val VIEW_TYPE_CARD_ROW = "Card Row"
    const val VIEW_TYPE_CARD_THUMBNAIL = "Card Thumbnails"
    const val VIEW_TYPE_LIST = "Card List"
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