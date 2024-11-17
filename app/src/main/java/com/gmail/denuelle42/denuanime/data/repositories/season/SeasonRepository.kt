package com.gmail.denuelle42.denuanime.data.repositories.season

import com.gmail.denuelle42.denuanime.data.repositories.season.request.GetSeasonNowRequest
import com.gmail.denuelle42.denuanime.data.repositories.season.request.GetSeasonUpcomingRequest
import com.gmail.denuelle42.denuanime.data.repositories.season.response.GetSeasonNowResponse
import com.gmail.denuelle42.denuanime.data.repositories.season.response.GetSeasonUpcomingResponse
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

@ViewModelScoped
class SeasonRepository @Inject constructor(
    private val api: SeasonAPI
) {
    suspend fun getSeasonNow(request : GetSeasonNowRequest) : GetSeasonNowResponse {
        val params = mapOf(
            "filter" to request.filter,
            "sfw" to request.sfw,
            "unapproved" to request.unapproved,
            "continuing" to request.continuing,
            "page" to request.page,
            "limit" to request.limit
        ).filterValues { it != null }.mapValues { it.value.toString() }

        val response = api.getSeasonNow(params)

        if(response.code() != HttpURLConnection.HTTP_OK){
            throw HttpException(response)
        }

        return response.body() ?: throw NullPointerException("Response body is empty")
    }

    suspend fun getSeasonUpcoming(request : GetSeasonUpcomingRequest) : GetSeasonUpcomingResponse {
        val params = mapOf(
            "filter" to request.filter,
            "sfw" to request.sfw,
            "unapproved" to request.unapproved,
            "continuing" to request.continuing,
            "page" to request.page,
            "limit" to request.limit
        ).filterValues { it != null }.mapValues { it.value.toString() }

        val response = api.getSeasonUpcoming(params)

        if(response.code() != HttpURLConnection.HTTP_OK){
            throw HttpException(response)
        }

        return response.body() ?: throw NullPointerException("Response body is empty")
    }
}