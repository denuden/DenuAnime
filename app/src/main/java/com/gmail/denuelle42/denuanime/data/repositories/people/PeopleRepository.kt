package com.gmail.denuelle42.denuanime.data.repositories.people

import com.gmail.denuelle42.denuanime.data.repositories.people.request.GetPeopleSearchRequest
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPeopleSearchResponse
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

@ViewModelScoped
class PeopleRepository @Inject constructor(
    private val peopleApi: PeopleAPI,
) {
    suspend fun getPeopleSearch(request: GetPeopleSearchRequest): GetPeopleSearchResponse {
        val map = mapOf(
            "page" to request.page,
            "limit" to request.limit,
            "q" to request.q,
            "order_by" to request.order_by,
            "sort" to request.sort,
            "letter" to request.letter
        ).filterValues { it != null }.mapValues { it.value.toString() }

        val response = peopleApi.getPeopleSearch(map)

        if (response.code() != HttpURLConnection.HTTP_OK) {
            throw HttpException(response)
        }

        return response.body() ?: throw NullPointerException("Response data is empty")
    }
}