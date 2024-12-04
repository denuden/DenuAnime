package com.gmail.denuelle42.denuanime.data.repositories.anime

import com.gmail.denuelle42.denuanime.data.repositories.anime.response.GetAnimeFullByIdResponse
import com.gmail.denuelle42.denuanime.data.repositories.anime.response.GetAnimeSearchResponse
import com.gmail.denuelle42.denuanime.data.repositories.anime.response.GetRecentEpisodesResponse
import com.gmail.denuelle42.denuanime.data.repositories.anime.response.GetTopAnimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface AnimeAPI {
    @GET("top/anime")
    suspend fun getTopAnime(@QueryMap params : Map<String, String>): Response<GetTopAnimeResponse>

    @GET("anime")
    suspend fun getAnimeSearch(@QueryMap params : Map<String, String>) : Response<GetAnimeSearchResponse>

    @GET("watch/episodes")
    suspend fun getRecentEpisodes() : Response<GetRecentEpisodesResponse>

    @GET("anime/{id}/full")
    suspend fun getAnimeFullById(@Path("id") id : Int) : Response<GetAnimeFullByIdResponse>
}