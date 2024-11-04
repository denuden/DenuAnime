package com.gmail.denuelle42.denuanime.data.repositories.genre.request

import androidx.annotation.Keep

@Keep
data class GetAnimeGenresRequest(
    val filter : String? = null
)
