package com.gmail.denuelle42.denuanime.ui.anime.search

sealed class AnimeSearchScreenEvents {
    object OnSearchAnime : AnimeSearchScreenEvents()
    data class OnChangeTypeFilter(val value : String) : AnimeSearchScreenEvents()
    data class OnChangeSearchQuery(val value : String) : AnimeSearchScreenEvents()

    object OnSetLoadingSearchAnime : AnimeSearchScreenEvents()
    data class OnSetInitialState(val value : AnimeSearchScreenState) : AnimeSearchScreenEvents()
}