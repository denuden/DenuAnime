package com.gmail.denuelle42.denuanime.ui.anime.characters

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.denuelle42.denuanime.R
import com.gmail.denuelle42.denuanime.data.remote.models.BaseImages
import com.gmail.denuelle42.denuanime.data.remote.models.ImageType
import com.gmail.denuelle42.denuanime.data.remote.models.animecharacters.AnimeCharacter
import com.gmail.denuelle42.denuanime.data.remote.models.animecharacters.Character
import com.gmail.denuelle42.denuanime.data.remote.models.animecharacters.Person
import com.gmail.denuelle42.denuanime.data.remote.models.animecharacters.VoiceActor
import com.gmail.denuelle42.denuanime.navigation.NavigationScreens
import com.gmail.denuelle42.denuanime.ui.anime.AnimeEvents
import com.gmail.denuelle42.denuanime.ui.anime.AnimeState
import com.gmail.denuelle42.denuanime.ui.anime.AnimeViewModel
import com.gmail.denuelle42.denuanime.ui.anime.characters.components.CharacterItemList
import com.gmail.denuelle42.denuanime.ui.common.skeleton.SkeletonCharacterListScreen
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.gmail.denuelle42.denuanime.utils.ComposableLifecycle
import com.gmail.denuelle42.denuanime.utils.ObserveAsEvents
import com.gmail.denuelle42.denuanime.utils.OneTimeEvents
import com.gmail.denuelle42.denuanime.utils.SnackBarController
import kotlinx.coroutines.launch

@Composable
fun CharacterListScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (NavigationScreens) -> Unit,
    id: Int,
    viewModel: AnimeViewModel = hiltViewModel()
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    CharacterListScreenContent(uiState = uiState)

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

            }
        }
    }

    ComposableLifecycle { _, lifecycleEvent ->
        when (lifecycleEvent) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.onEvent(AnimeEvents.OnGetAnimeCharacters(id))
            }

            else -> Unit
        }
    }

}

@Composable
private fun CharacterListScreenContent(
    uiState: AnimeState,
) {
    val lazyListState = rememberLazyListState()


    AnimatedVisibility(
        visible = !uiState.isGetAnimeCharactersLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.padding(8.dp)
        ){
            if(uiState.listOfAnimeCharacters.isNullOrEmpty()){
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .height(400.dp)
                            .fillMaxWidth()
                            .background(color = Color.LightGray, shape = MaterialTheme.shapes.small)
                    ) {
                        Text(text = stringResource(R.string.error_no_anime_found))
                    }
                }
            } else {
                items(uiState.listOfAnimeCharacters) { character ->
                    CharacterItemList(
                        data = character
                    )
                }
            }
        }
    }

    AnimatedVisibility(
        visible = uiState.isGetAnimeCharactersLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column (
            modifier = Modifier.padding(8.dp)
        ){
            SkeletonCharacterListScreen(modifier = Modifier.padding(bottom = 8.dp))
            SkeletonCharacterListScreen(modifier = Modifier.padding(bottom = 8.dp))
            SkeletonCharacterListScreen(modifier = Modifier.padding(bottom = 8.dp))
            SkeletonCharacterListScreen(modifier = Modifier.padding(bottom = 8.dp))
        }
    }
}

@Preview
@Composable
private fun CharacterListScreenPreview() {
    DenuAnimeTheme {
        Surface(color = MaterialTheme.colorScheme.surface) {
            CharacterListScreenContent(
                AnimeState(
                    listOfAnimeCharacters = listOf(
                        AnimeCharacter(
                            character = Character(
                                mal_id = 131,
                                url = "https://myanimelist.net/character/131/Kohei_Watanabe",
                                images = ImageType(
                                    jpg = BaseImages(
                                        image_url = "https://cdn.myanimelist.net/images/characters/11/253723.webp?s=6c8a19a79a88c46ae15f30e3ef5fd839"
                                    )
                                ),
                                name = "Black, Jet"
                            ),
                            role = "Main",
                            favorites = 2248,
                            voice_actors = listOf(
                                VoiceActor(
                                    person = Person(
                                        mal_id = 2422,
                                        url = "https://myanimelist.net/person/2422/Kohei_Watanabe",
                                        images = ImageType(
                                            jpg = BaseImages(
                                                image_url = "https://cdn.myanimelist.net/images/voiceactors/3/43568.jpg?s=99aac1d18ec5b55645260c6395b1da08"
                                            )
                                        ),
                                        name = "Billingslea, Beau",
                                    ),
                                    language = "English"
                                ),
                                VoiceActor(
                                    person = Person(
                                        mal_id = 2422,
                                        url = "https://myanimelist.net/person/2422/Kohei_Watanabe",
                                        images = ImageType(
                                            jpg = BaseImages(
                                                image_url = "https://cdn.myanimelist.net/images/voiceactors/3/43568.jpg?s=99aac1d18ec5b55645260c6395b1da08"
                                            )
                                        ),
                                        name = "Billingslea, Beau",
                                    ),
                                    language = "English"
                                ),
                            )
                        ),
                        AnimeCharacter(
                            character = Character(
                                mal_id = 131,
                                url = "https://myanimelist.net/character/131/Kohei_Watanabe",
                                images = ImageType(
                                    jpg = BaseImages(
                                        image_url = "https://cdn.myanimelist.net/images/characters/11/253723.webp?s=6c8a19a79a88c46ae15f30e3ef5fd839"
                                    )
                                ),
                                name = "Black, Jet"
                            ),
                            role = "Main",
                            favorites = 2248,
                            voice_actors = listOf(
                                VoiceActor(
                                    person = Person(
                                        mal_id = 2422,
                                        url = "https://myanimelist.net/person/2422/Kohei_Watanabe",
                                        images = ImageType(
                                            jpg = BaseImages(
                                                image_url = "https://cdn.myanimelist.net/images/voiceactors/3/43568.jpg?s=99aac1d18ec5b55645260c6395b1da08"
                                            )
                                        ),
                                        name = "Billingslea, Beau",
                                    ),
                                    language = "English"
                                ),
                                VoiceActor(
                                    person = Person(
                                        mal_id = 2422,
                                        url = "https://myanimelist.net/person/2422/Kohei_Watanabe",
                                        images = ImageType(
                                            jpg = BaseImages(
                                                image_url = "https://cdn.myanimelist.net/images/voiceactors/3/43568.jpg?s=99aac1d18ec5b55645260c6395b1da08"
                                            )
                                        ),
                                        name = "Billingslea, Beau",
                                    ),
                                    language = "English"
                                ),
                            )
                        ),
                        AnimeCharacter(
                            character = Character(
                                mal_id = 131,
                                url = "https://myanimelist.net/character/131/Kohei_Watanabe",
                                images = ImageType(
                                    jpg = BaseImages(
                                        image_url = "https://cdn.myanimelist.net/images/characters/11/253723.webp?s=6c8a19a79a88c46ae15f30e3ef5fd839"
                                    )
                                ),
                                name = "Black, Jet"
                            ),
                            role = "Main",
                            favorites = 2248,
                            voice_actors = listOf(
                                VoiceActor(
                                    person = Person(
                                        mal_id = 2422,
                                        url = "https://myanimelist.net/person/2422/Kohei_Watanabe",
                                        images = ImageType(
                                            jpg = BaseImages(
                                                image_url = "https://cdn.myanimelist.net/images/voiceactors/3/43568.jpg?s=99aac1d18ec5b55645260c6395b1da08"
                                            )
                                        ),
                                        name = "Billingslea, Beau",
                                    ),
                                    language = "English"
                                ),
                                VoiceActor(
                                    person = Person(
                                        mal_id = 2422,
                                        url = "https://myanimelist.net/person/2422/Kohei_Watanabe",
                                        images = ImageType(
                                            jpg = BaseImages(
                                                image_url = "https://cdn.myanimelist.net/images/voiceactors/3/43568.jpg?s=99aac1d18ec5b55645260c6395b1da08"
                                            )
                                        ),
                                        name = "Billingslea, Beau",
                                    ),
                                    language = "English"
                                ),
                            )
                        ),
                        AnimeCharacter(
                            character = Character(
                                mal_id = 131,
                                url = "https://myanimelist.net/character/131/Kohei_Watanabe",
                                images = ImageType(
                                    jpg = BaseImages(
                                        image_url = "https://cdn.myanimelist.net/images/characters/11/253723.webp?s=6c8a19a79a88c46ae15f30e3ef5fd839"
                                    )
                                ),
                                name = "Black, Jet"
                            ),
                            role = "Main",
                            favorites = 2248,
                            voice_actors = listOf(
                                VoiceActor(
                                    person = Person(
                                        mal_id = 2422,
                                        url = "https://myanimelist.net/person/2422/Kohei_Watanabe",
                                        images = ImageType(
                                            jpg = BaseImages(
                                                image_url = "https://cdn.myanimelist.net/images/voiceactors/3/43568.jpg?s=99aac1d18ec5b55645260c6395b1da08"
                                            )
                                        ),
                                        name = "Billingslea, Beau",
                                    ),
                                    language = "English"
                                ),
                                VoiceActor(
                                    person = Person(
                                        mal_id = 2422,
                                        url = "https://myanimelist.net/person/2422/Kohei_Watanabe",
                                        images = ImageType(
                                            jpg = BaseImages(
                                                image_url = "https://cdn.myanimelist.net/images/voiceactors/3/43568.jpg?s=99aac1d18ec5b55645260c6395b1da08"
                                            )
                                        ),
                                        name = "Billingslea, Beau",
                                    ),
                                    language = "English"
                                ),
                            )
                        ),
                        AnimeCharacter(
                            character = Character(
                                mal_id = 131,
                                url = "https://myanimelist.net/character/131/Kohei_Watanabe",
                                images = ImageType(
                                    jpg = BaseImages(
                                        image_url = "https://cdn.myanimelist.net/images/characters/11/253723.webp?s=6c8a19a79a88c46ae15f30e3ef5fd839"
                                    )
                                ),
                                name = "Black, Jet"
                            ),
                            role = "Main",
                            favorites = 2248,
                            voice_actors = listOf(
                                VoiceActor(
                                    person = Person(
                                        mal_id = 2422,
                                        url = "https://myanimelist.net/person/2422/Kohei_Watanabe",
                                        images = ImageType(
                                            jpg = BaseImages(
                                                image_url = "https://cdn.myanimelist.net/images/voiceactors/3/43568.jpg?s=99aac1d18ec5b55645260c6395b1da08"
                                            )
                                        ),
                                        name = "Billingslea, Beau",
                                    ),
                                    language = "English"
                                ),
                                VoiceActor(
                                    person = Person(
                                        mal_id = 2422,
                                        url = "https://myanimelist.net/person/2422/Kohei_Watanabe",
                                        images = ImageType(
                                            jpg = BaseImages(
                                                image_url = "https://cdn.myanimelist.net/images/voiceactors/3/43568.jpg?s=99aac1d18ec5b55645260c6395b1da08"
                                            )
                                        ),
                                        name = "Billingslea, Beau",
                                    ),
                                    language = "English"
                                ),
                            )
                        ),
                        AnimeCharacter(
                            character = Character(
                                mal_id = 131,
                                url = "https://myanimelist.net/character/131/Kohei_Watanabe",
                                images = ImageType(
                                    jpg = BaseImages(
                                        image_url = "https://cdn.myanimelist.net/images/characters/11/253723.webp?s=6c8a19a79a88c46ae15f30e3ef5fd839"
                                    )
                                ),
                                name = "Black, Jet"
                            ),
                            role = "Main",
                            favorites = 2248,
                            voice_actors = listOf(
                                VoiceActor(
                                    person = Person(
                                        mal_id = 2422,
                                        url = "https://myanimelist.net/person/2422/Kohei_Watanabe",
                                        images = ImageType(
                                            jpg = BaseImages(
                                                image_url = "https://cdn.myanimelist.net/images/voiceactors/3/43568.jpg?s=99aac1d18ec5b55645260c6395b1da08"
                                            )
                                        ),
                                        name = "Billingslea, Beau",
                                    ),
                                    language = "English"
                                ),
                                VoiceActor(
                                    person = Person(
                                        mal_id = 2422,
                                        url = "https://myanimelist.net/person/2422/Kohei_Watanabe",
                                        images = ImageType(
                                            jpg = BaseImages(
                                                image_url = "https://cdn.myanimelist.net/images/voiceactors/3/43568.jpg?s=99aac1d18ec5b55645260c6395b1da08"
                                            )
                                        ),
                                        name = "Billingslea, Beau",
                                    ),
                                    language = "English"
                                ),
                            )
                        ),
                        AnimeCharacter(
                            character = Character(
                                mal_id = 131,
                                url = "https://myanimelist.net/character/131/Kohei_Watanabe",
                                images = ImageType(
                                    jpg = BaseImages(
                                        image_url = "https://cdn.myanimelist.net/images/characters/11/253723.webp?s=6c8a19a79a88c46ae15f30e3ef5fd839"
                                    )
                                ),
                                name = "Black, Jet"
                            ),
                            role = "Main",
                            favorites = 2248,
                            voice_actors = listOf(
                                VoiceActor(
                                    person = Person(
                                        mal_id = 2422,
                                        url = "https://myanimelist.net/person/2422/Kohei_Watanabe",
                                        images = ImageType(
                                            jpg = BaseImages(
                                                image_url = "https://cdn.myanimelist.net/images/voiceactors/3/43568.jpg?s=99aac1d18ec5b55645260c6395b1da08"
                                            )
                                        ),
                                        name = "Billingslea, Beau",
                                    ),
                                    language = "English"
                                ),
                                VoiceActor(
                                    person = Person(
                                        mal_id = 2422,
                                        url = "https://myanimelist.net/person/2422/Kohei_Watanabe",
                                        images = ImageType(
                                            jpg = BaseImages(
                                                image_url = "https://cdn.myanimelist.net/images/voiceactors/3/43568.jpg?s=99aac1d18ec5b55645260c6395b1da08"
                                            )
                                        ),
                                        name = "Billingslea, Beau",
                                    ),
                                    language = "English"
                                ),
                            )
                        ),
                    ),
                )
            )
        }
    }
}