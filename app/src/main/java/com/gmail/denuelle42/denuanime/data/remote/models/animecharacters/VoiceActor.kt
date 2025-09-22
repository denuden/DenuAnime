package com.gmail.denuelle42.denuanime.data.remote.models.animecharacters


import androidx.annotation.Keep

@Keep
data class VoiceActor(
    val person: Person? = Person(),
    val language: String? = "" // Japanese
)