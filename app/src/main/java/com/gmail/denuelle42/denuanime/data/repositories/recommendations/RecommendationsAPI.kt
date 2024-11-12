package com.gmail.denuelle42.denuanime.data.repositories.recommendations

import com.gmail.denuelle42.denuanime.data.repositories.recommendations.response.GetRecentAnimeRecommendationsResponse
import retrofit2.Response
import retrofit2.http.GET

interface RecommendationsAPI {
    @GET("recommendations/anime")
    suspend fun getRecentAnimeRecommendations() : Response<GetRecentAnimeRecommendationsResponse>

    @GET("recommendations/manga")
    suspend fun getRecentMangaRecommendations() : Response<GetRecentAnimeRecommendationsResponse>
}