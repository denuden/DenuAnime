package com.gmail.denuelle42.denuanime.ui.anime.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AnimeSearchScreenViewModel @Inject constructor() : ViewModel(){
    private val TAG = AnimeSearchScreenViewModel::class.java.simpleName

    private var initialState: AnimeSearchScreenState? = null

    private val _stateFlow = MutableStateFlow<AnimeSearchScreenState>(AnimeSearchScreenState())
    val stateFlow = _stateFlow.asStateFlow()


    fun getInitialState(state: AnimeSearchScreenState) {
        if (initialState == null) { // Ensure it is set only once
            initialState = state
            _stateFlow.value = state
        }
    }

    fun onEvent(event : AnimeSearchScreenEvents) {
        when(event) {
            is AnimeSearchScreenEvents.OnChangeTypeFilter -> {
                _stateFlow.update {
                    it.copy(typeFilter = event.value)
                }
            }
        }
    }

}