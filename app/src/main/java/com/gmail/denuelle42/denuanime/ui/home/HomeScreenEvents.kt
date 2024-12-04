package com.gmail.denuelle42.denuanime.ui.home

import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Genre
import com.gmail.denuelle42.denuanime.data.repositories.anime.request.GetAnimeSearchRequest
import com.gmail.denuelle42.denuanime.data.repositories.anime.request.GetTopAnimeRequest
import com.gmail.denuelle42.denuanime.data.repositories.genre.request.GetAnimeGenresRequest
import com.gmail.denuelle42.denuanime.data.repositories.people.request.GetPeopleSearchRequest
import com.gmail.denuelle42.denuanime.data.repositories.season.request.GetSeasonNowRequest
import com.gmail.denuelle42.denuanime.data.repositories.season.request.GetSeasonUpcomingRequest

sealed class HomeScreenEvents {
    //API Related
    data class OnGetTopPeopleSearch(val request: GetPeopleSearchRequest) :  HomeScreenEvents()
    data class OnGetTopAnime(val request: GetTopAnimeRequest) :  HomeScreenEvents()
    data class OnGetAnimeGenres(val request: GetAnimeGenresRequest) :  HomeScreenEvents()
    data class OnGetAnimeSearch(val request: GetAnimeSearchRequest) :  HomeScreenEvents()
    object OnGetAnimeRecommendations : HomeScreenEvents()
    data object OnGetMangaRecommendations : HomeScreenEvents()
    data class OnSelectAnimeGenre(val genre: Genre) : HomeScreenEvents()
    data class OnChangeAnimeFilters(val type: String, val rating : String) : HomeScreenEvents()
    data class OnSelectNextAnimeRecommendations(val page : Int) : HomeScreenEvents()
    data class OnSelectPreviousAnimeRecommendations(val page : Int) : HomeScreenEvents()
    object OnGetRecentEpisodes : HomeScreenEvents()
    data class OnGetSeasonNow(val request : GetSeasonNowRequest) : HomeScreenEvents()
    data class OnGetSeasonUpcoming(val request : GetSeasonUpcomingRequest) : HomeScreenEvents()

    //Navigation
    object OnNavigateToSeeMorePeople : HomeScreenEvents()
    data class OnNavigateToPersonDetails(val id :Int) : HomeScreenEvents()
    data class OnNavigateToAnimeDetails(val id :Int) : HomeScreenEvents()
}