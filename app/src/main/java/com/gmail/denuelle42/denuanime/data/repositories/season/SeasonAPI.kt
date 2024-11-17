package com.gmail.denuelle42.denuanime.data.repositories.season

import com.gmail.denuelle42.denuanime.data.repositories.season.response.GetSeasonNowResponse
import com.gmail.denuelle42.denuanime.data.repositories.season.response.GetSeasonUpcomingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SeasonAPI {
    @GET("seasons/now")
    suspend fun getSeasonNow(@QueryMap params : Map<String, String>) : Response<GetSeasonNowResponse>

    @GET("seasons/upcoming")
    suspend fun getSeasonUpcoming(@QueryMap params : Map<String, String>) : Response<GetSeasonUpcomingResponse>
}