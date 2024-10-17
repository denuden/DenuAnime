package com.gmail.denuelle42.denuanime.data.remote.models.animedetails


import androidx.annotation.Keep

@Keep
data class Producer(
    val mal_id: Int? = null, // 17
    val type: String? = null, // anime
    val name: String? = null, // Aniplex
    val url: String? = null // https://myanimelist.net/anime/producer/17/Aniplex
)