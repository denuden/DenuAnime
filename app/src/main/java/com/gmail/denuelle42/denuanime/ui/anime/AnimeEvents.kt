package com.gmail.denuelle42.denuanime.ui.anime

sealed class AnimeEvents{
    data class OnGetAnimeFullById(val id : Int) : AnimeEvents()
}
