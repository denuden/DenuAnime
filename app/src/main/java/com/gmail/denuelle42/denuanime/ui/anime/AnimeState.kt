package com.gmail.denuelle42.denuanime.ui.anime

import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails

@Keep
data class AnimeState(
    val animeDetails: AnimeDetails? = null,
    val isGetAnimeFullByIdLoading : Boolean = true,
)
