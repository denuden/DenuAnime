package com.gmail.denuelle42.denuanime.data.remote.models.animedetails


import androidx.annotation.Keep
import com.gmail.denuelle42.denuanime.data.remote.models.ImageType

@Keep
data class AnimeDetails(
    val mal_id: Int? = 0, // 37999
    val url: String? = "", // https://myanimelist.net/anime/37999/Kaguya-sama_wa_Kokurasetai__Tensai-tachi_no_Renai_Zunousen
    val images: ImageType? = ImageType(),
    val trailer: Trailer? = Trailer(),
    val approved: Boolean? = false, // true
    val titles: List<Title>? = listOf(),
    val title: String? = "", // Kaguya-sama wa Kokurasetai: Tensai-tachi no Renai Zunousen
    val title_english: String? = "", // Kaguya-sama: Love is War
    val title_japanese: String? = "", // かぐや様は告らせたい～天才たちの恋愛頭脳戦～
    val title_synonyms: List<String>? = listOf(),
    val type: String? = "", // TV
    val source: String? = "", // Manga
    val episodes: Int? = 0, // 12
    val status : String? = "", // Finished Airing
    val airing: Boolean? = false, // false
    val aired: Aired? = Aired(),
    val duration: String? = "", // 25 min per ep
    val rating: String? = "", // PG-13 - Teens 13 or older
    val score: Double? = 0.0, // 8.4
    val scored_by: Int? = 0, // 1125321
    val rank: Int? = 0, // 193
    val popularity: Int? = 0, // 51
    val members: Int? = 0, // 1791268
    val favorites: Int? = 0, // 40242
    val synopsis: String? = "", // At the renowned Shuchiin Academy, Miyuki Shirogane and Kaguya Shinomiya are the student body's top representatives. Ranked the top student in the nation and respected by peers and mentors alike, Miyuki serves as the student council president. Alongside him, the vice president Kaguya—eldest daughter of the wealthy Shinomiya family—excels in every field imaginable. They are the envy of the entire student body, regarded as the perfect couple.However, despite both having already developed feelings for the other, neither are willing to admit them. The first to confess loses, will be looked down upon, and will be considered the lesser. With their honor and pride at stake, Miyuki and Kaguya are both equally determined to be the one to emerge victorious on the battlefield of love![Written by MAL Rewrite]
    val background: String? = "",
    val season: String? = "", // winter
    val year: Int? = 0, // 2019
    val broadcast: Broadcast? = Broadcast(),
    val producers: List<Producer>? = listOf(),
    val licensors: List<Licensor>? = listOf(),
    val studios: List<Studio>? = listOf(),
    val genres: List<Genre>? = listOf(),
    val explicit_genres: List<Any?>? = listOf(),
    val themes: List<Genre>? = listOf(),
    val demographics: List<Genre>? = listOf(),
    val relations: List<Relation>? = listOf(),
    val theme: ThemeSong? = ThemeSong(),
    val `external`: List<External>? = listOf(),
    val streaming: List<Streaming>? = listOf()
)