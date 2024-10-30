package com.gmail.denuelle42.denuanime.data.repositories.people

import com.gmail.denuelle42.denuanime.data.remote.models.people.People
import com.gmail.denuelle42.denuanime.data.repositories.people.request.GetPeopleSearch
import com.gmail.denuelle42.denuanime.di.scopes.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

class PeopleRepository @Inject constructor(
    private val peopleApi: PeopleAPI,
) {
   suspend fun getPeopleSearch(request : GetPeopleSearch): People {
            val response = peopleApi.getPeopleSearch(request)

            if (response.code() != HttpURLConnection.HTTP_OK) {
                throw HttpException(response)
            }

            return response.body() ?: throw  NullPointerException("Response data is empty")
        }
    }
}