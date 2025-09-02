package com.gmail.denuelle42.denuanime.ui.anime.search

sealed class AnimeSearchScreenEvents {
    object OnSearchAnime : AnimeSearchScreenEvents()
    data class OnChangeTypeFilter(val value : String) : AnimeSearchScreenEvents()
    data class OnChangeSearchQuery(val value : String) : AnimeSearchScreenEvents()
    data class OnChangeScoreFilter(val value : String) : AnimeSearchScreenEvents()
    data class OnChangeMinMaxScoreFilter(val minValue : String, val maxValue : String) : AnimeSearchScreenEvents()
    data class OnChangeStatusFilter(val value : String) : AnimeSearchScreenEvents()
    data class OnChangeRatingFilter(val value : String) : AnimeSearchScreenEvents()
    data class OnChangeSFWFilter(val value : String) : AnimeSearchScreenEvents()
    data class OnChangeGenreFilter(val value : String) : AnimeSearchScreenEvents()
    data class OnChangeOrderByFilter(val value : String) : AnimeSearchScreenEvents()
    data class OnChangeSortFilter(val value : String) : AnimeSearchScreenEvents()
    data class OnChangeStartDateFilter(val value : Long) : AnimeSearchScreenEvents()
    data class OnChangeEndDateFilter(val value : Long) : AnimeSearchScreenEvents()

    object OnSetLoadingSearchAnime : AnimeSearchScreenEvents()
    data class OnToggleScoreFilter(val value : Boolean) : AnimeSearchScreenEvents()
    data class OnSetInitialState(val value : AnimeSearchScreenState) : AnimeSearchScreenEvents()

    data class OnNavigateToAnimeDetails(val value : Int) : AnimeSearchScreenEvents()
}