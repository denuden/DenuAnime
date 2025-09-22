package com.gmail.denuelle42.denuanime.ui.anime

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class AnimeViewModel @Inject constructor(
    private val animeUseCase: AnimeUseCase
) : ViewModel() {
    private val TAG = AnimeViewModel::class.java.simpleName
    private val _stateFlow = MutableStateFlow<AnimeState>(AnimeState())
    val stateFlow = _stateFlow.asStateFlow()

    private val _channel = Channel<OneTimeEvents>()
    val channel = _channel.receiveAsFlow()

    fun onEvent(event: AnimeEvents) {
        when (event) {
            is AnimeEvents.OnGetAnimeCharacters -> {
                viewModelScope.launch {
                    animeUseCase.getAnimeCharacters(event.id).asResult().onEach { res ->
                        when (res) {
                            ResultState.Completed -> _stateFlow.update {it.copy(isGetAnimeCharactersLoading = false)}
                            is ResultState.Error -> Log.e(TAG, res.exception.toString())
                            ResultState.Loading -> _stateFlow.update {it.copy(isGetAnimeCharactersLoading = true)}
                            is ResultState.Success -> _stateFlow.update { it.copy(listOfAnimeCharacters = res.data.data) }
                        }
                    }.collect()
                }
            }
            is AnimeEvents.OnGetAnimeFullById -> {
                viewModelScope.launch {
                    animeUseCase.getAnimeFullById(event.id).asResult().onEach { res ->
                        when (res) {
                            ResultState.Completed -> _stateFlow.update {it.copy(isGetAnimeFullByIdLoading = false)}
                            is ResultState.Error -> Log.e(TAG, res.exception.toString())
                            ResultState.Loading -> _stateFlow.update {it.copy(isGetAnimeFullByIdLoading = true)}
                            is ResultState.Success -> _stateFlow.update { it.copy(animeDetails = res.data.data) }
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