package com.gmail.denuelle42.denuanime.data.repositories.anime.request

import androidx.annotation.Keep

@Keep
data class GetTopAnimeRequest(
    val type : String? = null, // tv, movie, ova, special, ona, music, cm, pv, tv_special
    val filter : String? = null, // airing, upcoming, bypopularity, favorite
    val rating : String? = null, // g, pg, pg13, r17, r, rx
    val sfw : Boolean? = null,
    val page : Int? = null,
    val limit : Int? = null,
)