package com.gmail.denuelle42.denuanime.data.repositories.anime.response

import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.animecharacters.AnimeCharacter

@Keep
data class GetAnimeCharactersResponse(
    val `data`: List<AnimeCharacter>? = null

)
