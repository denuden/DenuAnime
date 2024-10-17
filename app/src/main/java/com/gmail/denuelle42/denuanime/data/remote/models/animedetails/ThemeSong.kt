package com.gmail.denuelle42.denuanime.data.remote.models.animedetails


import androidx.annotation.Keep

@Keep
data class ThemeSong(
    val openings: List<String?>? = null,
    val endings: List<String?>? = null
)