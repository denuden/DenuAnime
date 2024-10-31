package com.gmail.denuelle42.denuanime.data.remote.models


import androidx.annotation.Keep

@Keep
data class Pagination(
    val last_visible_page: Int? = null, // 2615
    val has_next_page: Boolean? = null, // true
    val current_page: Int? = null, // 1
    val items: Items? = null
)

@Keep
data class Items(
    val count: Int? = null, // 25
    val total: Int? = null, // 65363
    val per_page: Int? = null // 25
)