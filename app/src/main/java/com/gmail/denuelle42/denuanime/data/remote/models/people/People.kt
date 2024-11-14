package com.gmail.denuelle42.denuanime.data.remote.models.people

import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.ImageType

@Keep
data class People(
    val mal_id: Int? = null, // 1
    val url: String? = null, // https://myanimelist.net/people/1/Tomokazu_Seki
    val website_url: String? = null, // https://agrs.co.jp/
    val images: ImageType? = null,
    val name: String? = null, // Tomokazu Seki
    val given_name: String? = null, // 智一
    val family_name: String? = null, // 関
    val alternate_names: List<String?>? = null,
    val birthday: String? = null, // 1972-09-08T00:00:00+00:00
    val favorites: Int? = null, // 6171
    val about: String? = null // Hometown: Tokyo, JapanBlood type: ABTwitter: @seki0908Instagram: @sekitomokazuProfile: Atomic Monkey
)