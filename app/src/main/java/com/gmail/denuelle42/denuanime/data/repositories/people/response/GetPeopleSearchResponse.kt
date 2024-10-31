package com.gmail.denuelle42.denuanime.data.repositories.people.response

import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.Pagination
import com.gmail.denuelle42.denuanime.data.remote.models.people.People

@Keep
data class GetPeopleSearchResponse(
    val pagination: Pagination? = null,
    val `data`: List<People>? = null
)