package com.gmail.denuelle42.denuanime.data.remote.models.animedetails


import androidx.annotation.Keep

@Keep
data class Relation(
    val relation: String? = null, // Adaptation
    val entry: List<Entry?>? = null
)