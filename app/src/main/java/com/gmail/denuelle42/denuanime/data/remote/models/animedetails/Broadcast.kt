package com.gmail.denuelle42.denuanime.data.remote.models.animedetails


import androidx.annotation.Keep

@Keep
data class Broadcast(
    val day: String? = null, // Saturdays
    val time: String? = null, // 23:30
    val timezone: String? = null, // Asia/Tokyo
    val string: String? = null // Saturdays at 23:30 (JST)
)