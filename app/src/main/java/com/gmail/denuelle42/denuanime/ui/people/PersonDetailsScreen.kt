package com.gmail.denuelle42.denuanime.ui.people

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.navigation.NavigationScreens
import com.gmail.denuelle42.denuanime.ui.common.ImageSlider
import com.gmail.denuelle42.denuanime.ui.people.components.AnimeVoicesItemCardList
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.gmail.denuelle42.denuanime.utils.ComposableLifecycle
import com.gmail.denuelle42.denuanime.utils.clickableDelayed
import com.gmail.denuelle42.denuanime.utils.formatIsoDateAsLongDate
import com.gmail.denuelle42.denuanime.utils.goURL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailsScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (NavigationScreens) -> Unit,
    id : Int,
    viewModel: PeopleViewModel = hiltViewModel()
) {

    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    ComposableLifecycle { _, event ->
        when(event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.onEvent(PeopleScreenEvents.OnGetPersonFullById(id))
            }
            else -> Unit
        }
    }

    PersonDetailsScreenContent(uiState = uiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailsScreenContent(
    modifier: Modifier = Modifier,
    uiState: PeopleScreenState
) {
    val pictures = uiState.personDetails?.pictures
    val data = uiState.personDetails?.data

    val lazyListState = rememberLazyListState()
    var isAboutContentExpanded by remember { mutableStateOf(false) }
    var tabState by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Voices")

    val context = LocalContext.current
    LazyColumn(state = lazyListState) {
        //Image
        item {
            ImageSlider(
                images = pictures.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        }

        //Text Details
        item {
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                Row {
                    Text(
                        data?.name ?: stringResource(R.string.unknown_name),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    val givenName = data?.given_name?.takeIf { it.isNotEmpty() } ?: "---"
                    val familyName = data?.family_name?.takeIf { it.isNotEmpty() } ?: "---"

                    Text(
                        "(${givenName} ${familyName})", modifier = Modifier.padding(start = 6.dp),
                        style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Normal
                    )
                }

                Text(
                    "Alternate Names: ${data?.alternate_names?.takeIf { it.isNotEmpty() }?.joinToString(", ") ?: "---"}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Light
                )

                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = "Birthday"
                    )
                    Text(
                        text = formatIsoDateAsLongDate(data?.birthday),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite"
                    )
                    Text(
                        text = data?.favorites.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Link,
                        contentDescription = "Link",
                        tint = Color.Blue
                    )
                    Text(
                        text = data?.url?.takeIf { it.isNotEmpty() } ?: "----",
                        color = Color.Blue,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.clickableDelayed {
                            data?.url?.let {
                                context.goURL(data.url)
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .height(if (isAboutContentExpanded) Dp.Unspecified else 120.dp)
                        .animateContentSize()
                ) {
                    Text(
                        text = data?.about?.takeIf { it.isNotEmpty() } ?: "-----",
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                TextButton(
                    onClick =
                    {
                        isAboutContentExpanded = !isAboutContentExpanded
                    },
                    shape = MaterialTheme.shapes.medium,
                    contentPadding = PaddingValues(vertical = 4.dp, horizontal = 2.dp)
                ) {
                    Text("See more")
                }
            }
        }

        //Tab
        item {
            PrimaryTabRow(
                selectedTabIndex = tabState,
                indicator = {
                    TabRowDefaults.PrimaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(
                            tabState,
                            matchContentSize = false
                        ), width = Dp.Unspecified
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = tabState == index,
                        onClick = { tabState = index },
                        text = {
                            Text(
                                text = title,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                    )
                }
            }
        }

        //Tab items list
        if(data?.voices?.isNotEmpty() == true){
            items(data.voices) { voice ->
                AnimeVoicesItemCardList(
                    voices = voice,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        } else {
            item {
                Text("No voices found",  textAlign = TextAlign.Center,modifier = Modifier.padding(vertical = 24.dp, horizontal = 8.dp).fillMaxWidth())
            }
        }
    }
}
@Preview
@Composable
private fun PeopleDetailsReview() {
    DenuAnimeTheme {
        Surface(modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)) {
            PersonDetailsScreenContent(modifier = Modifier.fillMaxSize(), uiState = PeopleScreenState())
        }
    }
}