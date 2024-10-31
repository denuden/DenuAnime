package com.gmail.denuelle42.denuanime.data.repositories.people.request

import androidx.annotation.Keep

@Keep
data class GetPeopleSearchRequest(
    val page : Int? = null,
    val limit : Int? = null,
    val q :String? = null,
    val order_by : String? = null, // mal_id, name, birthday, favorites
    val sort : String? = null, // asc, desc
    val letter : String? = null
)
