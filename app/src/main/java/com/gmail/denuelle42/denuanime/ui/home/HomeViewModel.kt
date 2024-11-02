package com.gmail.denuelle42.denuanime.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.denuelle42.bscode.util.ResultState
import com.gmail.denuelle42.bscode.util.asResult
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.data.remote.models.people.People
import com.gmail.denuelle42.denuanime.data.repositories.anime.request.GetTopAnimeRequest
import com.gmail.denuelle42.denuanime.data.repositories.people.request.GetPeopleSearchRequest
import com.gmail.denuelle42.denuanime.domain.repositories.anime.AnimeUseCase
import com.gmail.denuelle42.denuanime.domain.repositories.people.PeopleUseCase
import com.gmail.denuelle42.denuanime.utils.OneTimeEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val peopleUseCase: PeopleUseCase,
    private val animeUseCase : AnimeUseCase
) : ViewModel() {
    private val TAG = HomeViewModel::class.java.simpleName

    private val _channel = Channel<OneTimeEvents>()
    val channel = _channel.receiveAsFlow()

    private val topPeopleList = MutableStateFlow(emptyList<People>())
    private val isGetTopPeopleSearchLoading = MutableStateFlow(false)
    val peopleState = combine(
        topPeopleList, isGetTopPeopleSearchLoading
    ) { topPeopleList, isGetTopPeopleSearchLoading ->
        HomeScreenState(
            topPeopleList = topPeopleList,
            isGetTopPeopleSearchLoading = isGetTopPeopleSearchLoading,
        )
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeScreenState()
    )

    private val topAnimeList = MutableStateFlow(emptyList<AnimeDetails>())
    private val isGetTopAnimeLoading = MutableStateFlow(false)
    private val type = MutableStateFlow("tv")
    private val rating = MutableStateFlow("")

    val topAnimeState = combine(
        topAnimeList, isGetTopAnimeLoading, rating, type
    ) { topAnimeList, isGetTopAnimeLoading, rating, type ->
        HomeScreenState(
            topAnimeList = topAnimeList,
            isGetTopAnimeLoading = isGetTopAnimeLoading,
            rating = rating,
            type = type,
        )
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeScreenState()
    )


    init {
        onEvent(HomeScreenEvents.OnGetTopPeopleSearch(request = GetPeopleSearchRequest(order_by = "favorites", sort = "desc")))
        onEvent(HomeScreenEvents.OnGetTopAnime(request = GetTopAnimeRequest(type = topAnimeState.value.type, limit = 25)))
    }

    fun onEvent(event: HomeScreenEvents) {
        when(event) {
            is HomeScreenEvents.OnGetTopPeopleSearch -> {
                viewModelScope.launch {
                    peopleUseCase.getPeopleSearch(event.request).asResult().onEach { res ->
                        when(res){
                            ResultState.Completed -> isGetTopPeopleSearchLoading.update { false }
                            is ResultState.Error ->  Log.e(TAG, res.exception.toString())
                            ResultState.Loading -> isGetTopPeopleSearchLoading.update { true }
                            is ResultState.Success -> topPeopleList.update { res.data.data ?: emptyList() }
                        }
                    }.collect()
                }
            }
            is HomeScreenEvents.OnGetTopAnime -> {
                viewModelScope.launch {
                    animeUseCase.getTopAnime(event.request).asResult().onEach { res ->
                        when(res){
                            ResultState.Completed -> isGetTopAnimeLoading.update { false }
                            is ResultState.Error ->  Log.e(TAG, res.exception.toString())
                            ResultState.Loading -> isGetTopAnimeLoading.update { true }
                            is ResultState.Success -> topAnimeList.update { res.data.data ?: emptyList() }
                        }
                    }.collect()
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