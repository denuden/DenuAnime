package com.gmail.denuelle42.denuanime.data.repositories.recommendations

import com.gmail.denuelle42.denuanime.data.repositories.recommendations.response.GetRecentAnimeRecommendationsResponse
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

@ViewModelScoped
class RecommendationsRepository @Inject constructor(
    private val recommendationsAPI: RecommendationsAPI
) {
    suspend fun getRecentAnimeRecommendations() : GetRecentAnimeRecommendationsResponse {
        val response = recommendationsAPI.getRecentAnimeRecommendations()

        if(response.code() != HttpURLConnection.HTTP_OK) {
            throw HttpException(response)
        }

        return response.body() ?: throw NullPointerException("Response data is empty")
    }


    suspend fun getRecentMangaRecommendations() : GetRecentAnimeRecommendationsResponse {
        val response = recommendationsAPI.getRecentMangaRecommendations()

        if(response.code() != HttpURLConnection.HTTP_OK) {
            throw HttpException(response)
        }

        return response.body() ?: throw NullPointerException("Response data is empty")
    }
}