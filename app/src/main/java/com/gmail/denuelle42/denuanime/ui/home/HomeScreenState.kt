package com.gmail.denuelle42.denuanime.ui.home

import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Genre
import com.gmail.denuelle42.denuanime.data.remote.models.people.People
import com.gmail.denuelle42.denuanime.data.repositories.anime.response.RecentEpisodesList

data class HomeScreenState(
    val topPeopleList: List<People>? = null,
    val isGetTopPeopleSearchLoading: Boolean = true,

    val animeList : List<AnimeDetails>? = null,
    val isGetAnimeListLoading : Boolean = true,
    val rating : String? = null,
    val type : String? = null,

    val animeGenres : List<Genre>? = null,
    val isGetAnimeGenresLoading : Boolean = true,

    val recommendationsList : List<AnimeDetails>? = null,
    val recommendationsShown : List<AnimeDetails>? = null,
    val isGetRecentRecommendationsLoading : Boolean = true,

    val recentEpisodesList : List<RecentEpisodesList>? = null,
    val isRecentEpisodesLoading : Boolean = true,
)