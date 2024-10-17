package com.gmail.denuelle42.denuanime.data.remote.models


import androidx.annotation.Keep

@Keep
data class ImageType(
    val jpg: BaseImages? = null,
    val webp: BaseImages? = null
)