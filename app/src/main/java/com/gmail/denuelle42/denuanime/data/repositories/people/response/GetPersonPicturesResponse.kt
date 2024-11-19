package com.gmail.denuelle42.denuanime.data.repositories.people.response

import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.ImageType

@Keep
data class GetPersonPicturesResponse(
    val `data` : List<ImageType>? = null
)
