package com.gmail.denuelle42.denuanime.ui.anime

sealed class AnimeEvents{
    data class OnGetAnimeFullById(val id : Int) : AnimeEvents()
    data class OnGetAnimeCharacters(val id : Int) : AnimeEvents()

    //Navigation
    data class OnNavigateToCharacterList(val id : Int) : AnimeEvents()
}
