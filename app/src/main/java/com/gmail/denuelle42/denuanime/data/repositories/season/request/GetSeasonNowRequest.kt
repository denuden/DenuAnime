package com.gmail.denuelle42.denuanime.data.repositories.season.request

import androidx.annotation.Keep

@Keep
data class GetSeasonNowRequest(
    val filter : String? = null, // tv, movie, ova, special, ona, music, cm, pv, tv_special
    val sfw : Boolean? = null,
    val unapproved  :Boolean? = null,
    val continuing : Boolean? = null,
    val page : Int? = null,
    val limit : Int? = null,
)