package com.gmail.denuelle42.denuanime.ui.anime.search

import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails

@Keep
data class AnimeSearchScreenState(
    //filters
    val typeFilter : String? = null,
    val searchQuery : String? = null,

    val animeList: List<AnimeDetails>? = null,
    val isGetAnimeSearchLoading : Boolean = false,
)
