package com.gmail.denuelle42.denuanime.ui.anime.characters

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

@Composable
fun CharacterItemList(
    modifier: Modifier = Modifier,
    data: AnimeCharacter,
) {
    val context = LocalContext.current
    var personIndex by remember { mutableIntStateOf(0) }

    Row(modifier = modifier.padding(8.dp)) {
        // Anime Character
        CharacterItem(
            modifier = Modifier.weight(1f),
            data = data
        )

        // Voice Actor/Actress
        PersonItem(
            modifier = Modifier.weight(1f),
            data = data.voice_actors?.get(personIndex) ?: VoiceActor(),
        )
    }
}

@Preview
@Composable
private fun CharacterItemListPreview() {
    Surface(color = MaterialTheme.colorScheme.surface) {
        CharacterItemList(
            data = AnimeCharacter(
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
                    )
                )
            )
        )
    }
}