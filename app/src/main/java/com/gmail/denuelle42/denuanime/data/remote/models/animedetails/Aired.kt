package com.gmail.denuelle42.denuanime.data.remote.models.animedetails


import androidx.annotation.Keep

@Keep
data class Aired(
    val from: String? = null, // 2019-01-12T00:00:00+00:00
    val to: String? = null, // 2019-03-30T00:00:00+00:00
    val prop: Prop? = null,
    val string: String? = null // Jan 12, 2019 to Mar 30, 2019
)