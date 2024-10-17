package com.gmail.denuelle42.denuanime.data.remote.models


import androidx.annotation.Keep

@Keep
data class BaseImages(
    val image_url: String? = null, // https://img.youtube.com/vi/Ti2kJ-GYO68/default.jpg
    val small_image_url: String? = null, // https://img.youtube.com/vi/Ti2kJ-GYO68/sddefault.jpg
    val medium_image_url: String? = null, // https://img.youtube.com/vi/Ti2kJ-GYO68/mqdefault.jpg
    val large_image_url: String? = null, // https://img.youtube.com/vi/Ti2kJ-GYO68/hqdefault.jpg
    val maximum_image_url: String? = null // https://img.youtube.com/vi/Ti2kJ-GYO68/maxresdefault.jpg
)