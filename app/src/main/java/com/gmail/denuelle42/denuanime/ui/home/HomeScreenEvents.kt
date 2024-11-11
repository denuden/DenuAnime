package com.gmail.denuelle42.denuanime.ui.home

import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Genre
import com.gmail.denuelle42.denuanime.data.repositories.anime.request.GetAnimeSearchRequest
import com.gmail.denuelle42.denuanime.data.repositories.anime.request.GetTopAnimeRequest
import com.gmail.denuelle42.denuanime.data.repositories.genre.request.GetAnimeGenresRequest
import com.gmail.denuelle42.denuanime.data.repositories.people.request.GetPeopleSearchRequest

sealed class HomeScreenEvents {
    data class OnGetTopPeopleSearch(val request: GetPeopleSearchRequest) :  HomeScreenEvents()
    data class OnGetTopAnime(val request: GetTopAnimeRequest) :  HomeScreenEvents()
    data class OnChangeMainAnimeListFilter(val type : String, val rating : String, val event : (HomeScreenEvents) -> Unit) :  HomeScreenEvents()
    data class OnGetAnimeGenres(val request: GetAnimeGenresRequest) :  HomeScreenEvents()
    data class OnGetAnimeSearch(val request: GetAnimeSearchRequest) :  HomeScreenEvents()
    object OnGetAnimeRecommendations : HomeScreenEvents()
    data class OnSelectAnimeGenre(val genre: Genre) : HomeScreenEvents()
}