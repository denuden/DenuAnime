package com.gmail.denuelle42.denuanime.ui.anime.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.denuelle42.denuanime.data.remote.error.ErrorModel
import com.gmail.denuelle42.denuanime.data.repositories.anime.request.GetAnimeSearchRequest
import com.gmail.denuelle42.denuanime.data.repositories.genre.request.GetAnimeGenresRequest
import com.gmail.denuelle42.denuanime.domain.repositories.anime.AnimeUseCase
import com.gmail.denuelle42.denuanime.domain.repositories.genre.GenreUseCase
import com.gmail.denuelle42.denuanime.navigation.AnimeScreens
import com.gmail.denuelle42.denuanime.utils.OneTimeEvents
import com.gmail.denuelle42.denuanime.utils.ResultState
import com.gmail.denuelle42.denuanime.utils.asResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AnimeSearchScreenViewModel @Inject constructor(
    private val animeUseCase: AnimeUseCase,
    private val genreUseCase: GenreUseCase
) : ViewModel(){
    private val TAG = AnimeSearchScreenViewModel::class.java.simpleName

    private var initialState: AnimeSearchScreenState? = null

    private val _stateFlow = MutableStateFlow<AnimeSearchScreenState>(AnimeSearchScreenState())
    val stateFlow = _stateFlow.asStateFlow()

    private val _channel = Channel<OneTimeEvents>()
    val channel = _channel.receiveAsFlow()

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

    private fun formatRatingFilter(rating : String) : String {
        return when (rating) {
            "" -> ""
            "G - All Ages"-> "g"
            "PG - Children" -> "pg"
            "PG-13 - Teens 13 or older" -> "pg13"
            "R - 17+ (violence & profanity)"  -> "r17"
            "R+ - Mild Nudity" -> "r"
            "Rx - Hentai" -> "rx"
            else -> ""
        }
    }

    private fun formatOrderByFilter(rating : String) : String {
        return when (rating) {
            "" -> ""
            "Title"-> "title"
            "Released Date" -> "start_date"
            "End Date" -> "end_date"
            "Episode Count"  -> "episodes"
            "Score" -> "score"
            "Rank" -> "rank"
            "Popularity" -> "popularity"
            "Favorites" -> "favorites"
            else -> ""
        }
    }


    init {
        viewModelScope.launch {
            genreUseCase.getAnimeGenres(GetAnimeGenresRequest()).asResult().onEach { res ->
                when(res) {
                    ResultState.Completed -> _stateFlow.update {it.copy(isGetGenreLoading = false)}
                    is ResultState.Error -> onError(res.exception)
                    ResultState.Loading -> _stateFlow.update {it.copy(isGetGenreLoading = true)}
                    is ResultState.Success -> _stateFlow.update { it.copy(genreList = res.data.data) }
                }
            }.collect()
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
                _stateFlow.update {
                    //retain genre list since it came from API but reset isSelected value
                    val genreList = _stateFlow.value.genreList?.map {
                        it.copy(isSelected = false)
                    }
                    AnimeSearchScreenState(
                        genreList = genreList
                    )
                }
            }
            is AnimeSearchScreenEvents.OnChangeTypeFilter -> {
                _stateFlow.update {
                    it.copy(typeFilter = event.value)
                }
            }
            is AnimeSearchScreenEvents.OnChangeScoreFilter -> {
                _stateFlow.update {
                    it.copy(scoreFilter = event.value)
                }
            }
            is AnimeSearchScreenEvents.OnToggleScoreFilter -> {
                _stateFlow.update {
                    it.copy(toggleScoreFilter = event.value)
                }
            }
            is AnimeSearchScreenEvents.OnChangeMinMaxScoreFilter -> {
                _stateFlow.update {
                    it.copy(minScoreFilter = event.minValue, maxScoreFilter = event.maxValue)
                }
            }
            is AnimeSearchScreenEvents.OnChangeStatusFilter -> {
                _stateFlow.update {
                    it.copy(statusFilter = event.value)
                }
            }
            is AnimeSearchScreenEvents.OnChangeRatingFilter -> {
                _stateFlow.update {
                    it.copy(ratingFilter = event.value)
                }
            }
            is AnimeSearchScreenEvents.OnChangeSFWFilter -> {
                _stateFlow.update {
                    it.copy(sfwFilter = event.value)
                }
            }
            is AnimeSearchScreenEvents.OnChangeGenreFilter -> {
                _stateFlow.update { currentState ->
                    val updatedGenreList = currentState.genreList?.map { genreItem ->
                        if (genreItem.mal_id.toString() == event.value) {
                            //set isSelected to true.
                            genreItem.copy(isSelected = !genreItem.isSelected)
                        } else {
                            // keep them as they are.
                            genreItem
                        }
                    }
                    currentState.copy(genreList = updatedGenreList)
                }
            }
            is AnimeSearchScreenEvents.OnChangeOrderByFilter -> {
                _stateFlow.update {
                    it.copy(orderByFilter = event.value)
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
                        q = _stateFlow.value.searchQuery,
                        score = _stateFlow.value.scoreFilter?.ifEmpty { null }?.toDouble(),
                        max_score = _stateFlow.value.maxScoreFilter?.ifEmpty{ null }?.toDouble(),
                        min_score = _stateFlow.value.minScoreFilter?.ifEmpty{ null }?.toDouble(),
                        status = _stateFlow.value.statusFilter?.lowercase(),
                        rating = formatRatingFilter(_stateFlow.value.ratingFilter.orEmpty()),
                        sfw = _stateFlow.value.sfwFilter,
                        genres = _stateFlow.value.genreList?.filter { it.isSelected }
                            ?.joinToString(",") { it.mal_id.toString() },
                        order_by = formatOrderByFilter(_stateFlow.value.orderByFilter.orEmpty()),
                    )
                    animeUseCase.getAnimeSearch(request).asResult().onEach { res ->
                        when(res) {
                            ResultState.Completed -> _stateFlow.update {it.copy(isGetAnimeSearchLoading = false)}
                            is ResultState.Error -> onError(res.exception)
                            ResultState.Loading -> _stateFlow.update {it.copy(isGetAnimeSearchLoading = true)}
                            is ResultState.Success -> _stateFlow.update { it.copy(animeList = res.data.data) }
                        }
                    }.collect()
                }
            }

            is AnimeSearchScreenEvents.OnNavigateToAnimeDetails -> {
                sendEvent(OneTimeEvents.OnNavigate(AnimeScreens.AnimeDetailsNavigation(event.value)))
            }
        }
    }

    private fun onError(e : Throwable?){
        when (e) {
            is HttpException -> {
                val errorBody = e.response()?.errorBody()
                val gson = Gson()
                val type = object : TypeToken<ErrorModel>() {}.type
                val errorResponse: ErrorModel? = gson.fromJson(errorBody?.charStream(), type)

                //if this is not null, then there is a message regarding bad request of params
                if (errorResponse?.messages != null){
                    sendEvent(OneTimeEvents.ShowInputError(errorResponse.messages))
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