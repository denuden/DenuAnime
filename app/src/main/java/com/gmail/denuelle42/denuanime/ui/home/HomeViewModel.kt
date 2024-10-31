package com.gmail.denuelle42.denuanime.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.denuelle42.bscode.util.ResultState
import com.gmail.denuelle42.bscode.util.asResult
import com.gmail.denuelle42.denuanime.data.remote.models.people.People
import com.gmail.denuelle42.denuanime.data.repositories.people.request.GetPeopleSearchRequest
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
    private val peopleUseCase: PeopleUseCase
) : ViewModel() {
    private val TAG = HomeViewModel::class.java.simpleName

    private val _channel = Channel<OneTimeEvents>()
    val channel = _channel.receiveAsFlow()

    private val people = MutableStateFlow(emptyList<People>())
    private val isGetPeopleSearchLoading = MutableStateFlow(false)

    val peopleState = combine(
        people, isGetPeopleSearchLoading
    ) { people, isGetPeopleSearchLoading ->
        HomeScreenState(
            people = people,
            isGetPeopleSearchLoading = isGetPeopleSearchLoading,
        )
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeScreenState()
    )

    init {
        onEvent(HomeScreenEvents.OnGetPeopleSearch(request = GetPeopleSearchRequest(limit = 4)))
    }


    fun onEvent(event: HomeScreenEvents) {
        when(event) {
            is HomeScreenEvents.OnGetPeopleSearch -> {
                viewModelScope.launch {
                    peopleUseCase.getPeopleSearch(event.request).asResult().onEach { res ->
                        when(res){
                            ResultState.Completed -> isGetPeopleSearchLoading.update { false }
                            is ResultState.Error ->  Log.e(TAG, res.exception.toString())
                            ResultState.Loading -> isGetPeopleSearchLoading.update { true }
                            is ResultState.Success -> people.update { res.data.data ?: emptyList() }
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