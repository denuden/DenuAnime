package com.gmail.denuelle42.denuanime.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.denuelle42.bscode.util.ResultState
import com.gmail.denuelle42.bscode.util.asResult
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Genre
import com.gmail.denuelle42.denuanime.data.repositories.anime.request.GetTopAnimeRequest
import com.gmail.denuelle42.denuanime.data.repositories.genre.request.GetAnimeGenresRequest
import com.gmail.denuelle42.denuanime.data.repositories.people.request.GetPeopleSearchRequest
import com.gmail.denuelle42.denuanime.domain.repositories.anime.AnimeUseCase
import com.gmail.denuelle42.denuanime.domain.repositories.genre.GenreUseCase
import com.gmail.denuelle42.denuanime.domain.repositories.people.PeopleUseCase
import com.gmail.denuelle42.denuanime.domain.repositories.recommendations.RecommendationsUseCase
import com.gmail.denuelle42.denuanime.utils.OneTimeEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val peopleUseCase: PeopleUseCase,
    private val animeUseCase: AnimeUseCase,
    private val genreUseCase: GenreUseCase,
    private val recommendationsUseCase: RecommendationsUseCase
) : ViewModel() {
    private val TAG = HomeViewModel::class.java.simpleName

    private val _channel = Channel<OneTimeEvents>()
    val channel = _channel.receiveAsFlow()

    private val _homeScreenState = MutableStateFlow<HomeScreenState>(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()

    private val _peopleState = MutableStateFlow<HomeScreenState>(HomeScreenState())
    val peopleState = _peopleState.asStateFlow()

    init {
        onEvent(
            HomeScreenEvents.OnGetTopPeopleSearch(
                request = GetPeopleSearchRequest(
                    order_by = "favorites",
                    sort = "desc"
                )
            )
        )
        onEvent(
            HomeScreenEvents.OnGetTopAnime(
                request = GetTopAnimeRequest(
                    type = homeScreenState.value.type,
                    limit = 25
                )
            )
        )
        onEvent(
            HomeScreenEvents.OnGetAnimeGenres(
                request = GetAnimeGenresRequest(filter = "genres")
            )
        )
    }

    fun onEvent(event: HomeScreenEvents) {
        when (event) {
            is HomeScreenEvents.OnGetTopPeopleSearch -> {
                viewModelScope.launch {
                    peopleUseCase.getPeopleSearch(event.request).asResult().onEach { res ->
                        when (res) {
                            ResultState.Completed -> _peopleState.update { it.copy(isGetTopPeopleSearchLoading =  false) }
                            is ResultState.Error -> Log.e(TAG, res.exception.toString())
                            ResultState.Loading -> _peopleState.update { it.copy(isGetTopPeopleSearchLoading =  true) }
                            is ResultState.Success -> _peopleState.update {
                                it.copy(topPeopleList = res.data.data)
                            }
                        }
                    }.collect()
                }
            }

            is HomeScreenEvents.OnGetTopAnime -> {
                viewModelScope.launch {
                    animeUseCase.getTopAnime(event.request).asResult().onEach { res ->
                        when (res) {
                            ResultState.Completed -> _homeScreenState.update { it.copy(isGetAnimeListLoading = false) }
                            is ResultState.Error -> Log.e(TAG, res.exception.toString())
                            ResultState.Loading -> _homeScreenState.update { it.copy(isGetAnimeListLoading = true) }
                            is ResultState.Success -> _homeScreenState.update {
                                it.copy(animeList = res.data.data)
                            }
                        }
                    }.collect()
                }
            }
            is HomeScreenEvents.OnGetAnimeSearch -> {
                viewModelScope.launch {
                    animeUseCase.getAnimeSearch(event.request).asResult().onEach { res ->
                        when (res) {
                            ResultState.Completed -> _homeScreenState.update { it.copy(isGetAnimeListLoading = false) }
                            is ResultState.Error -> Log.e(TAG, res.exception.toString())
                            ResultState.Loading -> _homeScreenState.update { it.copy(isGetAnimeListLoading = true) }
                            is ResultState.Success -> _homeScreenState.update {
                                it.copy(animeList = res.data.data)
                            }
                        }
                    }.collect()
                }
            }
            is HomeScreenEvents.OnChangeMainAnimeListFilter -> {
                viewModelScope.launch {
                    val type = when (event.type) {
                        "All" -> ""
                        "TV" -> "tv"
                        "Movie" -> "movie"
                        "OVA" -> "ova"
                        "Special" -> "special"
                        "ONA" -> "ona"
                        "Music" -> "music"
                        "CM" -> "cm"
                        "PV" -> "pv"
                        "TV Special" -> "tv_special"
                        else -> ""
                    }
                    val rating = when (event.rating) {
                        "All" -> ""
                        "G" -> "g"
                        "PG" -> "pg"
                        "PG-13" -> "pg13"
                        "R-17+" -> "r17"
                        "R-Mild Nudity" -> "r"
                        "Rx-Hentai" -> "rx"
                        else -> ""
                    }
//                    animeUseCase.getTopAnime(
//                        GetTopAnimeRequest(
//                            type = type,
//                            rating = rating,
//                            limit = 25
//                        )
//                    ).asResult().onEach { res ->
//                        when (res) {
//                            ResultState.Completed -> isGetTopAnimeLoading.update { false }
//                            is ResultState.Error -> Log.e(TAG, res.exception.toString())
//                            ResultState.Loading -> isGetTopAnimeLoading.update { true }
//                            is ResultState.Success -> topAnimeList.update {
//                                res.data.data ?: emptyList()
//                            }
//                        }
//                    }.collect()
                }
            }

            is HomeScreenEvents.OnGetAnimeGenres -> {
                viewModelScope.launch {
                    genreUseCase.getAnimeGenres(event.request).asResult().onEach { res ->
                        when (res) {
                            ResultState.Completed -> _homeScreenState.update { it.copy(isGetAnimeGenresLoading = false) }
                            is ResultState.Error -> Log.e(TAG, res.exception.toString())
                            ResultState.Loading -> _homeScreenState.update { it.copy(isGetAnimeGenresLoading = true) }
                            is ResultState.Success -> {
                                val genres = listOf(
                                    Genre(mal_id = -1, name = "Top"),
                                    Genre(mal_id = -2, name = "Upcoming")
                                ) + (res.data.data ?: emptyList())

                                _homeScreenState.update {
                                    it.copy(animeGenres = genres)
                                }
                            }
                        }
                    }.collect()
                }
            }

            is HomeScreenEvents.OnSelectAnimeGenre -> {
                _homeScreenState.update {
                    it.copy(
                        animeGenres =  it.animeGenres?.map { genre ->
                            if(event.genre.mal_id!! > 0){
                                if (genre.mal_id == event.genre.mal_id) {
                                    genre.copy(isSelected = !genre.isSelected)
                                } else {
                                    genre
                                }
                            } else{
                                if (genre.mal_id == event.genre.mal_id) {
                                    genre.copy(isSelected = true)
                                } else {
                                    genre.copy(isSelected = false)
                                }
                            }

                        }
                    )
                }
            }
            is HomeScreenEvents.OnGetAnimeRecommendations -> {
                viewModelScope.launch {
                    recommendationsUseCase.getRecentAnimeRecommendations().asResult().onEach { res ->
                        when(res){
                            ResultState.Completed -> _homeScreenState.update { it.copy(isGetRecentAnimeRecommendationsLoading = false) }
                            is ResultState.Error ->  Log.e(TAG, res.exception.toString())
                            ResultState.Loading ->  _homeScreenState.update { it.copy(isGetRecentAnimeRecommendationsLoading = true) }
                            is ResultState.Success -> _homeScreenState.update { it.copy(animeRecommendationsList = res.data.data ?: emptyList()) }
                        }
                    }
                }
            }
        }
    }

    private fun sendEvent(event: OneTimeEvents) {
        viewModelScope.launch {
            _channel.send(event)
        }
    }
}