package com.gmail.denuelle42.denuanime.ui.anime.search

sealed class AnimeSearchScreenEvents {
    data class OnChangeTypeFilter(val value : String) : AnimeSearchScreenEvents()
}