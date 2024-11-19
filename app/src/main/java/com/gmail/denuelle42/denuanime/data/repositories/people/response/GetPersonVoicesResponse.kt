package com.gmail.denuelle42.denuanime.data.repositories.people.response

import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.people.Voices

@Keep
data class GetPersonVoicesResponse(
    val `data` : List<Voices>? = null
)
