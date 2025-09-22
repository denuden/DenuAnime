package com.gmail.denuelle42.denuanime.data.remote.models.animecharacters


import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.ImageType

@Keep
data class Character(
    val mal_id: Int? = null, // 3
    val url: String? = null, // https://myanimelist.net/character/3/Jet_Black
    val images: ImageType? = null,
    val name: String? = null // Black, Jet
)