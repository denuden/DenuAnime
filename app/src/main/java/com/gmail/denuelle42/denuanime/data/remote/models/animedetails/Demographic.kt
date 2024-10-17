package com.gmail.denuelle42.denuanime.data.remote.models.animedetails


import androidx.annotation.Keep

@Keep
data class Demographic(
    val mal_id: Int? = null, // 42
    val type: String? = null, // anime
    val name: String? = null, // Seinen
    val url: String? = null // https://myanimelist.net/anime/genre/42/Seinen
)