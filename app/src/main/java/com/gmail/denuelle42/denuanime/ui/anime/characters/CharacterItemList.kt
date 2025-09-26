package com.gmail.denuelle42.denuanime.ui.anime.characters

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.denuelle42.denuanime.data.remote.models.BaseImages
import com.gmail.denuelle42.denuanime.data.remote.models.ImageType
import com.gmail.denuelle42.denuanime.data.remote.models.animecharacters.AnimeCharacter
import com.gmail.denuelle42.denuanime.data.remote.models.animecharacters.Character
import com.gmail.denuelle42.denuanime.data.remote.models.animecharacters.Person
import com.gmail.denuelle42.denuanime.data.remote.models.animecharacters.VoiceActor
import com.gmail.denuelle42.denuanime.ui.anime.characters.components.CharacterItem
import com.gmail.denuelle42.denuanime.ui.anime.characters.components.PersonItem
import com.gmail.denuelle42.denuanime.ui.common.SimpleSwipeableRow
import com.gmail.denuelle42.denuanime.ui.theme.DenuAnimeTheme
import com.gmail.denuelle42.denuanime.utils.AsyncImageAvatarWithErrorHandler
import com.gmail.denuelle42.denuanime.utils.clickableDelayed
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterItemList(
    modifier: Modifier = Modifier,
    data: AnimeCharacter,
) {
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .padding(bottom = 12.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceDim,
                shape = MaterialTheme.shapes.medium
            )
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            CharacterItem(
                data = data,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            // Voice Actor/Actress Row
            LazyRow(
                state = state,
                flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
                modifier = Modifier.weight(1f)
            ) {
                items(data.voice_actors.orEmpty()) {
                    PersonItem(
                        data = it,
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }
            }
        }

        SimpleSwipeableRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            onSwipeLeft = {
                scope.launch {
                    val currentItem = state.firstVisibleItemIndex
                    val scrollTo =
                        if (currentItem == data.voice_actors?.size) data.voice_actors.size else currentItem + 1
                    state.animateScrollToItem(scrollTo)
                }
            },
            onSwipeRight = {
                scope.launch {
                    val currentItem = state.firstVisibleItemIndex
                    val scrollTo = if (currentItem == 0) 0 else currentItem - 1
                    state.animateScrollToItem(scrollTo)
                }
            }
        ) {
            // Content of your original Row
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Previous Voice Actor"
            )
            HorizontalDivider(
                modifier = Modifier.width(100.dp)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Next Voice Actor",
            )
        }

        Row{

            data.voice_actors?.forEachIndexed { index, voice ->
                AsyncImageAvatarWithErrorHandler(
                    model = voice.person?.images?.jpg?.image_url,
                    shouldShowEnlargeButton = false,
                    modifier = Modifier
                         .weight(1f)
                        .height(30.dp)
                        .padding(start = 6.dp)
                        .clickableDelayed {
                            scope.launch {
                                state.animateScrollToItem(index)
                            }
                        }
                )
            }
        }
    }
}


@Preview
@Composable
private fun CharacterItemListPreview() {
    DenuAnimeTheme {
        Surface(
            color = Color.White, modifier = Modifier
                .background(color = Color.White)
                .padding(30.dp)
        ) {
            CharacterItemList(
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.surfaceDim
                ),
                data = AnimeCharacter(
                    character = Character(
                        mal_id = 131,
                        url = "https://myanimelist.net/character/131/Kohei_Watanabe",
                        images = ImageType(
                            jpg = BaseImages(
                                image_url = "https://cdn.myanimelist.net/images/characters/11/253723.webp?s=6c8a19a79a88c46ae15f30e3ef5fd839"
                            )
                        ),
                        name = "Black, Jedvwevsvsddfrfet"
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
                                        image_url = "https://cdn.myanimelist.net/images/characters/11/253723.webp?s=6c8a19a79a88c46ae15f30e3ef5fd839"
                                    )
                                ),
                                name = "Billingslea, Beau",
                            ),
                            language = "English"
                        ),
                    )
                )
            )
        }

    }
}