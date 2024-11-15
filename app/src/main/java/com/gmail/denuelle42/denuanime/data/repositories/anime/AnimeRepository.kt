package com.gmail.denuelle42.denuanime.data.repositories.anime

import com.gmail.denuelle42.denuanime.data.repositories.anime.request.GetAnimeSearchRequest
import com.gmail.denuelle42.denuanime.data.repositories.anime.request.GetTopAnimeRequest
import com.gmail.denuelle42.denuanime.data.repositories.anime.response.GetAnimeSearchResponse
import com.gmail.denuelle42.denuanime.data.repositories.anime.response.GetRecentEpisodesResponse
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

    suspend fun getAnimeSearch(request : GetAnimeSearchRequest) : GetAnimeSearchResponse {
        val map = mapOf(
            "unapproved" to request.unapproved,
            "page" to request.page,
            "limit" to request.limit,
            "q" to request.q,
            "type" to request.type,
            "score" to request.score,
            "min_score" to request.min_score,
            "max_score" to request.max_score,
            "status" to request.status,
            "rating" to request.rating,
            "sfw" to request.sfw,
            "genres" to request.genres,
            "genres_exclude" to request.genres_exclude,
            "order_by" to request.order_by,
            "sort" to request.sort,
            "letter" to request.letter,
            "producers" to request.producers,
            "start_date" to request.start_date,
            "end_date" to request.end_date,
        ).filterValues { it != null }.mapValues { it.value.toString() }

        val response = animeAPI.getAnimeSearch(map)

        if(response.code() != HttpURLConnection.HTTP_OK){
            throw HttpException(response)

        }

        return response.body() ?: throw NullPointerException("Response data is empty")
    }

    suspend fun getRecentEpisodes() : GetRecentEpisodesResponse {
        val response = animeAPI.getRecentEpisodes()

        if(response.code() != HttpURLConnection.HTTP_OK){
            throw HttpException(response)
        }

        return response.body() ?: throw NullPointerException("Response data is empty")
    }

}