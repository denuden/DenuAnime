package com.gmail.denuelle42.denuanime.data.remote.models.animedetails


import androidx.annotation.Keep

@Keep
data class Studio(
    val mal_id: Int? = null, // 56
    val type: String? = null, // anime
    val name: String? = null, // A-1 Pictures
    val url: String? = null // https://myanimelist.net/anime/producer/56/A-1_Pictures
)