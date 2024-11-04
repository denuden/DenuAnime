package com.gmail.denuelle42.denuanime.data.repositories.genre.response


import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.Genre

@Keep
data class GetAnimeGenresResponse(
    val `data`: List<Genre?>? = null
)