package com.gmail.denuelle42.denuanime.ui.home

import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Genre
import com.gmail.denuelle42.denuanime.data.remote.models.people.People

data class HomeScreenState(
    val topPeopleList: List<People>? = null,
    val isGetTopPeopleSearchLoading: Boolean = false,

    val topAnimeList : List<AnimeDetails>? = null,
    val isGetTopAnimeLoading : Boolean = false,
    val rating : String? = null,
    val type : String? = null,

    val animeGenres : List<Genre>? = null,
    val isGetAnimeGenresLoading : Boolean = false,
)