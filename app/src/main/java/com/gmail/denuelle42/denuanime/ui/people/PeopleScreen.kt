package com.gmail.denuelle42.denuanime.ui.people

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.data.repositories.people.request.GetPeopleSearchRequest
import com.gmail.denuelle42.denuanime.navigation.NavigationScreens
import com.gmail.denuelle42.denuanime.ui.people.components.PeopleItemCardList
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.gmail.denuelle42.denuanime.utils.ObserveAsEvents
import com.gmail.denuelle42.denuanime.utils.OneTimeEvents
import com.gmail.denuelle42.denuanime.utils.SnackBarController
import kotlinx.coroutines.launch

@Composable
fun PeopleScreen(
    onPopBackStack : () -> Unit,
    onNavigate : (NavigationScreens) -> Unit,
    viewModel: PeopleViewModel = hiltViewModel()
) {
    val peopleScreenState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val uiState = peopleScreenState

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    // Collects the query from search bar, with debounced applied(delayed event)
    LaunchedEffect(Unit) {
        viewModel.debouncedQuery.collect { query ->
            viewModel.onEvent(PeopleEvents.OnGetPeopleSearch(GetPeopleSearchRequest(q = query)))
        }
    }

    //One time events listener
    ObserveAsEvents(flow = viewModel.channel) { event ->
        when (event) {
            is OneTimeEvents.OnNavigate -> onNavigate(event.route)
            OneTimeEvents.OnPopBackStack -> onPopBackStack()
            is OneTimeEvents.ShowSnackbar ->  {
                scope.launch {
                    SnackBarController.sendEvent(event.snackbarEvent)
                }
            }
            is OneTimeEvents.ShowToast -> {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
            is OneTimeEvents.ShowInputError -> {

            }
        }
    }
    PeopleScreenContent(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun PeopleScreenContent(
    modifier: Modifier = Modifier,
    uiState: PeopleState,
    onEvent : (PeopleEvents) -> Unit
) {
    var searchState by remember { mutableStateOf("") }
    val lazyState = rememberLazyListState()


    LazyColumn(
        state = lazyState,
        contentPadding = PaddingValues(8.dp)
    ) {
        item {
            // Place TextField here
            TextField(
                value = searchState,
                onValueChange = {
                    searchState = it
                    onEvent(PeopleEvents.OnSearchQueryChanged(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(1.dp, MaterialTheme.shapes.extraLarge), // Add slight elevation
                shape = MaterialTheme.shapes.extraLarge, // Rounded corners like SearchBar
                placeholder = {
                    Text(text = stringResource(R.string.label_search))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.label_search)
                    )
                },
                trailingIcon = {
                    if (searchState.isNotEmpty()) {
                        IconButton(onClick = {
                            searchState = ""
                            onEvent(PeopleEvents.OnSearchQueryChanged(""))
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear Search")
                        }
                    }
                },
                colors = TextFieldDefaults.colors(
                    // The main change is using surfaceContainerLow for the background
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    errorContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,

                    // Your indicator colors are already perfect for a SearchBar look
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,

                    // These content colors are also appropriate for a SearchBar look
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,

                    unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,

                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,

                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                singleLine = true
            )

        }

        if(uiState.isGetPeopleSearchLoading) { // show loading
            item {
                Box(contentAlignment = Alignment.Center,  modifier = Modifier
                    .padding(vertical = 24.dp)
                    .fillMaxWidth()){
                    CircularProgressIndicator(modifier = Modifier.width(40.dp),)
                }
            }
        } else{
            if (uiState.peopleList?.isNotEmpty() == true){ //show list
                items(uiState.peopleList) {
                    PeopleItemCardList(
                        people = it,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ){
                        onEvent(PeopleEvents.OnNavigateToPersonDetailsScreen(it.mal_id ?: -1))
                    }
                }
            } else { //if empty, show placeholder
                item {
                    //if list is empty
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding( top = 16.dp)
                            .height(400.dp)
                            .fillMaxWidth()
                            .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
                    ) {
                        Text(text = stringResource(R.string.text_no_search_results_found))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PeopleScreenPreview() {
    DenuAnimeTheme {
        Surface(modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)) {
            PeopleScreenContent(uiState = PeopleState(),onEvent = {})
        }
    }
}
