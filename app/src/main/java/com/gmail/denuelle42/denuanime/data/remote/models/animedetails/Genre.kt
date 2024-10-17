package com.gmail.denuelle42.denuanime.data.remote.models.animedetails


import androidx.annotation.Keep

@Keep
data class Genre(
    val mal_id: Int? = null, // 4
    val type: String? = null, // anime
    val name: String? = null, // Comedy
    val url: String? = null // https://myanimelist.net/anime/genre/4/Comedy
)