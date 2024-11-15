package com.gmail.denuelle42.denuanime.data.repositories.anime.response

import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.Pagination
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.data.remote.models.episodes.Episode

@Keep
data class GetRecentEpisodesResponse(
    val pagination: Pagination? = null,
    val `data`: List<RecentEpisodesList>? = null
)

@Keep
data class RecentEpisodesList(
    val entry: AnimeDetails? = null,
    val episodes: List<Episode>? = null,
    val region_locked: Boolean? = null // false
)