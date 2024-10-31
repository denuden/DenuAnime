package com.gmail.denuelle42.denuanime.data.repositories.people

import com.gmail.denuelle42.denuanime.data.repositories.people.request.GetPeopleSearchRequest
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPeopleSearchResponse
import com.gmail.denuelle42.denuanime.utils.toMap
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

@ViewModelScoped
class PeopleRepository @Inject constructor(
    private val peopleApi: PeopleAPI,
) {
    suspend fun getPeopleSearch(request: GetPeopleSearchRequest): GetPeopleSearchResponse {
        val response = peopleApi.getPeopleSearch(request.toMap() as Map<String, Any>)


        if (response.code() != HttpURLConnection.HTTP_OK) {
            throw HttpException(response)
        }

        return response.body() ?: throw NullPointerException("Response data is empty")
    }
}