package com.gmail.denuelle42.denuanime.data.repositories.genre

import com.gmail.denuelle42.denuanime.data.repositories.genre.response.GetAnimeGenresResponse
import com.gmail.denuelle42.denuanime.data.repositories.genre.response.GetMangaGenresResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreAPI {
    @GET("genres/anime")
    suspend fun getAnimeGenres(@Query("filter") params : String?): Response<GetAnimeGenresResponse>

    @GET("genres/manga")
    suspend fun getMangaGenres(@Query("filter") params  : String?): Response<GetMangaGenresResponse>
}