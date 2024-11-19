package com.gmail.denuelle42.denuanime.data.remote.models.people

import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails


@Keep
data class PeopleAnime(
    val position : String? = null,
    val anime : AnimeDetails? = null,
)