package com.gmail.denuelle42.denuanime.data.repositories.season.response

import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.Pagination
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails

@Keep
data class GetSeasonNowResponse(
    val `data`: List<AnimeDetails>? = null,
    val pagination: Pagination? = null
)
