package com.gmail.denuelle42.denuanime.data.remote.models.animecharacters


import androidx.annotation.Keep

@Keep
data class AnimeCharacter(
    val character: Character? = Character(),
    val role: String? = "", // Main
    val favorites: Int? = 0, // 2248
    val voice_actors: List<VoiceActor>? = listOf()
)