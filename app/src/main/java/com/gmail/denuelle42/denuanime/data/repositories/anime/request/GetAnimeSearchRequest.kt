package com.gmail.denuelle42.denuanime.data.repositories.anime.request

import androidx.annotation.Keep

@Keep
data class GetAnimeSearchRequest(
    val unapproved : Boolean? = null,
    val page : Int? = null,
    val limit : Int? = null,
    val q : String? = null,
    val type : String? = null, // tv, movie, ova, special, ona, music, cm, pv, tv_special
    val score : Double? = null,
    val min_score : Double? = null,
    val max_score : Double? = null,
    val status : String? = null, // airing, complete, upcoming,
    val rating : String? = null, // g, pg, pg13, r17, r, rx
    val sfw : String? = null, //true or false
    val genres : String? = null, // Comma Separated ID's  1,2,3
    val genres_exclude : String? = null, // Comma Separated ID's  1,2,3
    val order_by : String? = null, // mal_id, title, start_date, end_date, score, episodes, scored_by, rank, popularity, members, favorites
    val sort : String? = null, // asc, desc
    val letter : String? = null,
    val producers : String? = null, // Comma Separated ID's  1,2,3
    val start_date : String? = null, // YYYY-MM-DD 2022, 2005-05, 2005-01-01
    val end_date : String? = null, // YYYY-MM-DD 2022, 2005-05, 2005-01-01
)
