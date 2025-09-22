package com.gmail.denuelle42.denuanime.data.remote.models.animecharacters


import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.ImageType

@Keep
data class Person(
    val mal_id: Int? = 0, // 357
    val url: String? = "", // https://myanimelist.net/people/357/Unshou_Ishizuka
    val images: ImageType? = null,
    val name: String? = "" // Ishizuka, Unshou
)