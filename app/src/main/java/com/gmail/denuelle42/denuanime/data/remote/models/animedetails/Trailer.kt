package com.gmail.denuelle42.denuanime.data.remote.models.animedetails


import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.BaseImages

@Keep
data class Trailer(
    val youtube_id: String? = "", // Ti2kJ-GYO68
    val url: String? = "", // https://www.youtube.com/watch?v=Ti2kJ-GYO68
    val embed_url: String? = "", // https://www.youtube.com/embed/Ti2kJ-GYO68?enablejsapi=1&wmode=opaque&autoplay=1
    val images: BaseImages? = BaseImages()
)