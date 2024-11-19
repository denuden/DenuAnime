package com.gmail.denuelle42.denuanime.data.repositories.people.response

import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.people.PeopleAnime

@Keep
data class GetPersonMangaResponse(
    val `data` : List<PeopleAnime>? = null
)
