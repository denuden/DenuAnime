package com.gmail.denuelle42.denuanime.ui.anime.search

import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Genre

@Keep
data class AnimeSearchScreenState(
    //filters
    val typeFilter : String? = null,
    val searchQuery : String? = null,
    val scoreFilter : String? = null,
    val minScoreFilter : String? = null,
    val maxScoreFilter : String? = null,
    val statusFilter : String? = null,
    val ratingFilter : String? = null,
    val sfwFilter : String? = null, //true or false
    val orderByFilter : String? = null,
    val sortFilter : String? = null,
    val startDateFilter : Long? = null,
    val endDateFilter : Long? = null,

    val toggleScoreFilter : Boolean = false,

    val animeList: List<AnimeDetails>? = null,
    val isGetAnimeSearchLoading : Boolean = false,

    val genreList : List<Genre>? = null,
    val isGetGenreLoading : Boolean = false,
)
