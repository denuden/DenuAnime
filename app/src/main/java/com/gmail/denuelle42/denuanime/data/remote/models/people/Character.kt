package com.gmail.denuelle42.denuanime.data.remote.models.people

import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.ImageType

@Keep
data class Character(
    val mal_id : Int? = null,
    val url : String? = null,
    val images : ImageType? = null,
    val name : String? = null
)