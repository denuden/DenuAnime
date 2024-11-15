package com.gmail.denuelle42.denuanime.data.remote.models.episodes

import androidx.annotation.Keep

@Keep
data class Episode(
    val mal_id : Int? = null,
    val url : String? = null,
    val title : String? = null,
    val premium : Boolean? = null,
)