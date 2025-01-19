package com.gmail.denuelle42.denuanime.ui.people

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
            TextField(
                value = searchState,
                onValueChange = {
                    searchState = it
                    onEvent(PeopleEvents.OnSearchQueryChanged(it))
                },
                shape = MaterialTheme.shapes.large,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.LightGray,
                    focusedContainerColor = Color.LightGray,
                    errorContainerColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search, contentDescription = stringResource( R.string.search)
                    )
                },
                label = { Text(stringResource( R.string.search))  },
                modifier = Modifier.fillMaxWidth()
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
                        //TODO
                        onEvent(PeopleEvents.OnNavigateToPersonDetailsScreen(it.mal_id ?: -1))
                    }
                }
            } else { //if empty, show placeholder
                item {
                    Text(
                        text = stringResource(R.string.no_results_found),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = 24.dp)
                            .fillMaxWidth()
                    )
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
