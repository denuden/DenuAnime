package com.gmail.denuelle42.denuanime.data.remote.models.animedetails


import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.SimpleDate

@Keep
data class Prop(
    val from: SimpleDate? = null,
    val to: SimpleDate? = null
)