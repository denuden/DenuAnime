package com.gmail.denuelle42.denuanime.ui.anime.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.denuelle42.denuanime.data.repositories.anime.request.GetAnimeSearchRequest
import com.gmail.denuelle42.denuanime.domain.repositories.anime.AnimeUseCase
import com.gmail.denuelle42.denuanime.utils.OneTimeEvents
import com.gmail.denuelle42.denuanime.utils.ResultState
import com.gmail.denuelle42.denuanime.utils.asResult
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
class AnimeSearchScreenViewModel @Inject constructor(
    private val animeUseCase: AnimeUseCase
) : ViewModel(){
    private val TAG = AnimeSearchScreenViewModel::class.java.simpleName

    private var initialState: AnimeSearchScreenState? = null

    private val _stateFlow = MutableStateFlow<AnimeSearchScreenState>(AnimeSearchScreenState())
    val stateFlow = _stateFlow.asStateFlow()

    private val _channel = Channel<OneTimeEvents>()
    val channel = _channel.receiveAsFlow()

    private fun getInitialState(state: AnimeSearchScreenState) {
        if (initialState == null) { // Ensure it is set only once
            initialState = state
            _stateFlow.value = state
        }
    }

    private fun formatTypeFilter(type : String) : String {
        return when(type) {
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
    }

    fun onEvent(event : AnimeSearchScreenEvents) {
        when(event) {
            is AnimeSearchScreenEvents.OnSetLoadingSearchAnime -> {
                _stateFlow.update {
                    it.copy(isGetAnimeSearchLoading = true)
                }
            }
            is AnimeSearchScreenEvents.OnSetInitialState -> {
                getInitialState(event.value)
            }
            is AnimeSearchScreenEvents.OnChangeTypeFilter -> {
                _stateFlow.update {
                    it.copy(typeFilter = event.value)
                }
            }
            is AnimeSearchScreenEvents.OnChangeSearchQuery -> {
                _stateFlow.update {
                    it.copy(searchQuery = event.value)
                }
            }
            is AnimeSearchScreenEvents.OnSearchAnime -> {
                viewModelScope.launch {
                    val request = GetAnimeSearchRequest(
                        type = formatTypeFilter(_stateFlow.value.typeFilter.orEmpty()),
                        q = _stateFlow.value.searchQuery
                    )
                    animeUseCase.getAnimeSearch(request).asResult().onEach { res ->
                        when(res) {
                            ResultState.Completed -> _stateFlow.update {it.copy(isGetAnimeSearchLoading = false)}
                            is ResultState.Error -> Log.e(TAG, res.exception.toString())
                            ResultState.Loading -> _stateFlow.update {it.copy(isGetAnimeSearchLoading = true)}
                            is ResultState.Success -> _stateFlow.update { it.copy(animeList = res.data.data) }
                        }
                    }.collect()
                }
            }
        }
    }

    private fun sendEvent(event : OneTimeEvents) {
        viewModelScope.launch {
            _channel.send(event)
        }
    }
}