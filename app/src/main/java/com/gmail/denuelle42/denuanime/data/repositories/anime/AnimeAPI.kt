package com.gmail.denuelle42.denuanime.data.repositories.anime

import com.gmail.denuelle42.denuanime.data.repositories.anime.response.GetTopAnimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface AnimeAPI {
    @GET("top/anime")
    suspend fun getTopAnime(@QueryMap params : Map<String, String>): Response<GetTopAnimeResponse>
}