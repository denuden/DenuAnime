package com.gmail.denuelle42.denuanime.data.repositories.anime.response


import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails

@Keep
data class GetAnimeFullById(
    val animeDetails: AnimeDetails? = AnimeDetails()
)