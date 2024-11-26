package com.gmail.denuelle42.denuanime.data.repositories.people.response

import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.ImageType
import com.gmail.denuelle42.denuanime.data.remote.models.people.People

@Keep
data class GetPersonFullByIdResponse(
    val `data` : People? = null,
    val pictures :  List<ImageType>? = null
)
