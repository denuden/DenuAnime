package com.gmail.denuelle42.denuanime.data.remote.models.animedetails


import androidx.annotation.Keep

@Keep
data class Theme(
    val mal_id: Int? = null, // 23
    val type: String? = null, // anime
    val name: String? = null, // School
    val url: String? = null // https://myanimelist.net/anime/genre/23/School
)