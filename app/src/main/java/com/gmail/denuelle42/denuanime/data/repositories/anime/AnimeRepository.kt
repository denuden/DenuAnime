package com.gmail.denuelle42.denuanime.data.repositories.anime

import com.gmail.denuelle42.denuanime.data.repositories.anime.request.GetTopAnimeRequest
import com.gmail.denuelle42.denuanime.data.repositories.anime.response.GetTopAnimeResponse
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

@ViewModelScoped
class AnimeRepository @Inject constructor(
    private val animeAPI: AnimeAPI,
) {
    suspend fun getTopAnime(request : GetTopAnimeRequest) : GetTopAnimeResponse {
        val map = mapOf(
            "type" to request.type,
            "filter" to request.filter,
            "rating" to request.rating,
            "sfw" to request.sfw,
            "page" to request.page,
            "limit" to request.limit
        ).filterValues { it != null }.mapValues { it.value.toString() }

        val response = animeAPI.getTopAnime(map)

        if (response.code() != HttpURLConnection.HTTP_OK) {
            throw HttpException(response)
        }

        return response.body() ?: throw NullPointerException("Response data is empty")
    }
}