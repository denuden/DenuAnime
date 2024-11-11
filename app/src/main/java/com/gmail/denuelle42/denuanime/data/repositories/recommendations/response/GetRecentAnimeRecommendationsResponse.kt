package com.gmail.denuelle42.denuanime.data.repositories.recommendations.response

import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.Pagination
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails

@Keep
data class GetRecentAnimeRecommendationsResponse(
    val pagination: Pagination? = null,
    val `data` : List<AnimeDetails>? = null,
)
