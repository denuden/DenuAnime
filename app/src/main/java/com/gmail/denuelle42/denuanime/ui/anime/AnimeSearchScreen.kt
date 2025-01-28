package com.gmail.denuelle42.denuanime.ui.anime

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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.navigation.NavigationScreens
import com.gmail.denuelle42.denuanime.ui.common.DetailedAnimeListItemCard
import com.gmail.denuelle42.denuanime.ui.common.FilterDropdown
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

    val recentSearches = listOf(
        "Shingeki", "Good life anime", "spice of life"
    )

    var selectedListViewType by remember { mutableStateOf(ViewTypes.VIEW_TYPE_CARD_ROW) }

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
                    placeholder = { Text("Hinted search text") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        FilterDropdown(
                            icon = Icons.Default.MoreVert,
                            type = listOf("Card Thumbnails", "Card Row", "List"),
                            onFilterClick = { type, _ ->

                            }
                        )
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

        LazyColumn(
            state = lazyState,
            contentPadding = PaddingValues(start = 16.dp, top = 72.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier,
        ) {
            val list = List(100) { "Text $it" }
            when (selectedListViewType) {
                ViewTypes.VIEW_TYPE_CARD_ROW -> {
                    cardRow(list)
                }
                ViewTypes.VIEW_TYPE_CARD_THUMBNAIL -> {
                    items(count = list.size) {
                        DetailedAnimeListItemCard()
                    }
                }
                ViewTypes.VIEW_TYPE_LIST -> {
                    items(count = list.size) {
                        DetailedAnimeListItemCard()
                    }
                }
            }
        }
    }
}

/**
 * View Type Card Row
 */
fun LazyListScope.cardRow(list : List<String>){
    items(count = list.size) {
        DetailedAnimeListItemCard()
        Spacer(Modifier.height(4.dp))
    }
}

fun LazyListScope.cardThumbnail(list : List<String>){

}


object ViewTypes {
    const val VIEW_TYPE_CARD_ROW = "VIEW_TYPE_CARD_ROW"
    const val VIEW_TYPE_CARD_THUMBNAIL = "VIEW_TYPE_CARD_THUMBNAIL"
    const val VIEW_TYPE_LIST = "VIEW_TYPE_LIST"
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