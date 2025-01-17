package com.gmail.denuelle42.denuanime.ui.home

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Genre
import com.gmail.denuelle42.denuanime.data.repositories.anime.request.GetAnimeSearchRequest
import com.gmail.denuelle42.denuanime.data.repositories.anime.request.GetTopAnimeRequest
import com.gmail.denuelle42.denuanime.data.repositories.genre.request.GetAnimeGenresRequest
import com.gmail.denuelle42.denuanime.data.repositories.people.request.GetPeopleSearchRequest
import com.gmail.denuelle42.denuanime.domain.repositories.anime.AnimeUseCase
import com.gmail.denuelle42.denuanime.domain.repositories.genre.GenreUseCase
import com.gmail.denuelle42.denuanime.domain.repositories.people.PeopleUseCase
import com.gmail.denuelle42.denuanime.domain.repositories.recommendations.RecommendationsUseCase
import com.gmail.denuelle42.denuanime.domain.repositories.season.SeasonUseCase
import com.gmail.denuelle42.denuanime.navigation.AnimeScreens
import com.gmail.denuelle42.denuanime.navigation.PeopleScreens
import com.gmail.denuelle42.denuanime.utils.OneTimeEvents
import com.gmail.denuelle42.denuanime.utils.ResultState
import com.gmail.denuelle42.denuanime.utils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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
    private val seasonUseCase: SeasonUseCase,
    private val recommendationsUseCase: RecommendationsUseCase
) : ViewModel() {
    private val TAG = HomeViewModel::class.java.simpleName

    private val _channel = Channel<OneTimeEvents>()
    val channel = _channel.receiveAsFlow()

    private val _homeScreenState = MutableStateFlow<HomeScreenState>(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()

    private val _peopleState = MutableStateFlow<HomeScreenState>(HomeScreenState())
    val peopleState = _peopleState.asStateFlow()

    //Holder for pagination in valus of Recommendations Secion
    var currentStartPage = mutableIntStateOf(0)
        private set

    //Updates Page of recommendations/resets List
    fun updateCurrentStartPage(value : Int){
        currentStartPage.intValue = value
    }

    //Formats Filter dropodown type for API Request
    private fun formatType(type : String) : String {
       return when (type) {
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
    }

    //Formats Filter dropodown secondary type for API Request
    private fun formatRating(rating : String) : String {
        return when (rating) {
            "All" -> ""
            "G" -> "g"
            "PG" -> "pg"
            "PG-13" -> "pg13"
            "R-17+" -> "r17"
            "R-Mild Nudity" -> "r"
            "Rx-Hentai" -> "rx"
            else -> ""
        }
    }

    //value holders to make calls across different API to persists its settings
    private val selectedGenre = mutableIntStateOf(-1)
    private val selectedType = mutableStateOf("All")
    private val selectedRating = mutableStateOf("All")
    private val selectedEpisodesAndSeasonTab = mutableIntStateOf(0)

    fun getSelectedEpisodesAndSeasonTab() : Int {
        return selectedEpisodesAndSeasonTab.intValue
    }
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
                    filter="bypopularity",
                    type = formatType(selectedType.value),
                    rating = formatRating(selectedRating.value)
                )
            )
        )
        onEvent(
            HomeScreenEvents.OnGetAnimeGenres(
                request = GetAnimeGenresRequest(filter = "genres")
            )
        )
        //refer to OnGetTopPeopleSearch for more initializations after initial batch of api request
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
                            is ResultState.Success -> {
                                _peopleState.update {
                                    it.copy(topPeopleList = res.data.data)
                                }

                                //================ Only Call this because of API Rate Limiting
                                delay(3000L)
                                onEvent(HomeScreenEvents.OnGetAnimeRecommendations)
                                onEvent(HomeScreenEvents.OnGetRecentEpisodes)
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
                            is ResultState.Success -> {
                                _homeScreenState.update {
                                    it.copy(animeList = res.data.data)
                                }
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

                                onEvent(HomeScreenEvents.OnSelectAnimeGenre(Genre(mal_id = -1, name = "Top")))
                            }
                        }
                    }.collect()
                }
            }

            is HomeScreenEvents.OnChangeAnimeFilters -> {
                selectedRating.value = event.rating
                selectedType.value  = event.type

                //Recall Get Anime
                if (selectedGenre.value == -1) {
                    onEvent(
                        HomeScreenEvents.OnGetTopAnime(
                            GetTopAnimeRequest(
                                filter = "bypopularity",
                                type = formatType(event.type),
                                rating = formatRating(event.rating)
                            )
                        )
                    )
                }
                if (selectedGenre.value == -2) {
                    onEvent(
                        HomeScreenEvents.OnGetAnimeSearch(
                            GetAnimeSearchRequest(
                                status = "upcoming",
                                order_by = "popularity",
                                type = formatType(event.type),
                                rating = formatRating(event.rating)
                            )
                        )
                    )
                }
                if (selectedGenre.value > 0) {
                    val formattedGenres = _homeScreenState.value.animeGenres.orEmpty().filter { it.isSelected }
                        .joinToString(separator = ",") { it.mal_id.toString() }
                    onEvent(HomeScreenEvents.OnGetAnimeSearch(GetAnimeSearchRequest(
                        genres = formattedGenres,
                        type = formatType(event.type),
                        rating = formatRating(event.rating))
                    ))
                }
            }
            is HomeScreenEvents.OnSelectAnimeGenre -> {
                _homeScreenState.update {
                    val reset = it.animeGenres?.map { genre ->
                        if (event.genre.mal_id!! < 0){ //if selected genre is top or upcoming
                            if(genre.mal_id!! != event.genre.mal_id){ // check every genre that is not top equal to selected one
                                genre.copy(isSelected =  false) // so i can reset everything else those to false
                            } else {
                                genre.copy(isSelected = true)
                            }
                        } else {
                            if(genre.mal_id!! == event.genre.mal_id ){
                                genre.copy(isSelected = !genre.isSelected)
                            } else if(genre.mal_id!! < 0) {
                                genre.copy(isSelected = false)
                            } else {
                                genre
                            }
                        }
                    }
                    it.copy(
                        animeGenres =  reset
                    )
                }

                //update selected genre for other events that needs access to this
                selectedGenre.intValue =  event.genre.mal_id!!

                if (event.genre.mal_id == -1) {
                    onEvent(HomeScreenEvents.OnGetTopAnime(GetTopAnimeRequest(
                        filter = "bypopularity",
                        type = formatType(selectedType.value),
                        rating = formatRating(selectedRating.value)
                    )))
                }
                if (event.genre.mal_id == -2) {
                    onEvent(
                        HomeScreenEvents.OnGetAnimeSearch(
                            GetAnimeSearchRequest(
                                status = "upcoming",
                                order_by = "popularity",
                                type = formatType(selectedType.value),
                                rating = formatRating(selectedRating.value)
                            )
                        )
                    )
                }
                if (event.genre.mal_id!! > 0) {
                    val formattedGenres = _homeScreenState.value.animeGenres.orEmpty().filter { it.isSelected }
                        .joinToString(separator = ",") { it.mal_id.toString() }
                    onEvent(HomeScreenEvents.OnGetAnimeSearch(GetAnimeSearchRequest(
                        genres = formattedGenres,
                        type = formatType(selectedType.value),
                        rating = formatRating(selectedRating.value)
                    )))
                }
            }
            is HomeScreenEvents.OnGetAnimeRecommendations -> {
                viewModelScope.launch {
                    recommendationsUseCase.getRecentAnimeRecommendations().asResult().onEach { res ->
                        when(res){
                            ResultState.Completed -> _homeScreenState.update { it.copy(isGetRecentRecommendationsLoading = false) }
                            is ResultState.Error ->  Log.e(TAG, res.exception.toString())
                            ResultState.Loading ->  _homeScreenState.update { it.copy(isGetRecentRecommendationsLoading = true) }
                            is ResultState.Success -> _homeScreenState.update {
                                Log.d(TAG, res.data.subList(0, 7).toString())
                                it.copy(
                                recommendationsList = res.data,
                                recommendationsShown = res.data.subList(0, 7)
                            )}
                        }
                    }.collect()
                }
            }
            is HomeScreenEvents.OnGetMangaRecommendations -> {
                viewModelScope.launch {
                    recommendationsUseCase.getRecentMangaRecommendations().asResult().onEach { res ->
                        when(res){
                            ResultState.Completed -> _homeScreenState.update { it.copy(isGetRecentRecommendationsLoading = false) }
                            is ResultState.Error ->  Log.e(TAG, res.exception.toString())
                            ResultState.Loading ->  _homeScreenState.update { it.copy(isGetRecentRecommendationsLoading = true) }
                            is ResultState.Success -> _homeScreenState.update {
                                Log.d(TAG, res.data.subList(0, 7).toString())
                                it.copy(
                                    recommendationsList = res.data,
                                    recommendationsShown = res.data.subList(0, 7)
                                )}
                        }
                    }.collect()
                }
            }
            is HomeScreenEvents.OnSelectNextAnimeRecommendations -> {
                if(_homeScreenState.value.recommendationsList?.isNotEmpty() == true){ //if not empty
                    _homeScreenState.update {
                        /**
                         *189 currentPage
                         * 189  + 7 = 196 > 200 ? false
                         * 196 = startpage
                         * 196 + 7 = 203 > 200 ? true
                         * 200 = endpage
                         * 196 - 200 shown
                         */
                        val startPage = if( (event.page + 7) > it.recommendationsList!!.size) it.recommendationsList.size else event.page + 7
                        val endPage = if((startPage + 7) > it.recommendationsList.size) it.recommendationsList.size else startPage + 7

                        it.copy(recommendationsShown = it.recommendationsList.subList(startPage, endPage))
                    }
                } else {
                    sendEvent(OneTimeEvents.ShowToast("No more pages left"))
                }
            }
            is HomeScreenEvents.OnSelectPreviousAnimeRecommendations -> {
                if(_homeScreenState.value.recommendationsList?.isNotEmpty() == true){
                    _homeScreenState.update {
                        val startPage = if( (event.page - 7) < 0) 0 else event.page - 7
                        it.copy(recommendationsShown = it.recommendationsList!!.subList(startPage, event.page))
                    }
                } else {
                    sendEvent(OneTimeEvents.ShowToast("No more pages left"))
                }
            }
            is HomeScreenEvents.OnGetRecentEpisodes -> {
                selectedEpisodesAndSeasonTab.intValue = 0
                viewModelScope.launch {
                    animeUseCase.getRecentEpisodes().asResult().onEach { res ->
                        when(res) {
                            ResultState.Completed -> _homeScreenState.update { it.copy(isEpisodesAndSeasonsLoading = false) }
                            is ResultState.Error -> Log.e(TAG, res.exception.toString())
                            ResultState.Loading -> _homeScreenState.update { it.copy(isEpisodesAndSeasonsLoading = true) }
                            is ResultState.Success -> _homeScreenState.update {
                                it.copy(episodesAndSeasonsList = res.data.data.orEmpty())
                            }
                        }
                    }.collect()
                }
            }
            is HomeScreenEvents.OnGetSeasonNow -> {
                selectedEpisodesAndSeasonTab.intValue = 1
                viewModelScope.launch {
                    seasonUseCase.getSeasonNow(event.request).asResult().onEach { res ->
                        when(res) {
                            ResultState.Completed -> _homeScreenState.update { it.copy(isEpisodesAndSeasonsLoading = false) }
                            is ResultState.Error -> Log.e(TAG, res.exception.toString())
                            ResultState.Loading -> _homeScreenState.update { it.copy(isEpisodesAndSeasonsLoading = true) }
                            is ResultState.Success -> _homeScreenState.update {
                                it.copy(episodesAndSeasonsList = res.data.orEmpty())
                            }
                        }
                    }.collect()
                }
            }
            is HomeScreenEvents.OnGetSeasonUpcoming -> {
                selectedEpisodesAndSeasonTab.intValue = 2
                viewModelScope.launch {
                    seasonUseCase.getSeasonUpcoming(event.request).asResult().onEach { res ->
                        when(res) {
                            ResultState.Completed -> _homeScreenState.update { it.copy(isEpisodesAndSeasonsLoading = false) }
                            is ResultState.Error -> Log.e(TAG, res.exception.toString())
                            ResultState.Loading -> _homeScreenState.update { it.copy(isEpisodesAndSeasonsLoading = true) }
                            is ResultState.Success -> _homeScreenState.update {
                                it.copy(episodesAndSeasonsList = res.data.orEmpty())
                            }
                        }
                    }.collect()
                }
            }
            is HomeScreenEvents.OnNavigateToSeeMorePeople -> {
                sendEvent(OneTimeEvents.OnNavigate(PeopleScreens.PeopleNavigation))
            }
            is HomeScreenEvents.OnNavigateToPersonDetails -> {
                sendEvent(OneTimeEvents.OnNavigate(PeopleScreens.PeopleDetailsNavigation(event.id)))
            }
            is HomeScreenEvents.OnNavigateToAnimeDetails -> {
                sendEvent(OneTimeEvents.OnNavigate(AnimeScreens.AnimeDetailsNavigation(event.id)))
            }
        }
    }

    private fun sendEvent(event: OneTimeEvents) {
        viewModelScope.launch {
            _channel.send(event)
        }
    }
}