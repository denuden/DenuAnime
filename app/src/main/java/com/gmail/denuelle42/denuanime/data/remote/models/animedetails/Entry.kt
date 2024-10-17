package com.gmail.denuelle42.denuanime.data.remote.models.animedetails


import androidx.annotation.Keep

@Keep
data class Entry(
    val mal_id: Int? = null, // 90125
    val type: String? = null, // manga
    val name: String? = null, // Kaguya-sama wa Kokurasetai: Tensai-tachi no Renai Zunousen
    val url: String? = null // https://myanimelist.net/manga/90125/Kaguya-sama_wa_Kokurasetai__Tensai-tachi_no_Renai_Zunousen
)