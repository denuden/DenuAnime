package com.gmail.denuelle42.denuanime.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.denuelle42.denuanime.domain.repositories.people.PeopleUseCase
import com.gmail.denuelle42.denuanime.utils.OneTimeEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val peopleUseCase: PeopleUseCase
) : ViewModel() {
    private val TAG = PeopleViewModel::class.java.simpleName

    private val _channel = Channel<OneTimeEvents>()
    val channel = _channel.receiveAsFlow()

    private val _stateFlow = MutableStateFlow<PeopleScreenState>(PeopleScreenState())
    val stateFlow = _stateFlow.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    // This collects the debounced queries
    @OptIn(FlowPreview::class)
    val debouncedQuery: Flow<String> = searchQuery
        .debounce(2000) // Wait 1000ms for user to stop typing
        .distinctUntilChanged() // Ignore if the new query is the same as the last
        .filter { it.isNotEmpty() } // Skip empty inputs

    fun onQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onEvent(event: PeopleScreenEvents) {
        when(event){
            else -> Unit
        }
    }

    private fun sendEvent(event: OneTimeEvents) {
        viewModelScope.launch {
            _channel.send(event)
        }
    }
}