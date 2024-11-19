package com.gmail.denuelle42.denuanime.data.repositories.people

import com.gmail.denuelle42.denuanime.data.repositories.people.request.GetPeopleSearchRequest
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPeopleSearchResponse
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPersonAnimeResponse
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPersonByIdResponse
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPersonFullByIdResponse
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPersonMangaResponse
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPersonPicturesResponse
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPersonVoicesResponse
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

    suspend fun getPersonFullById(id : Int) : GetPersonFullByIdResponse {
        val response = peopleApi.getPersonFullById(id)

        if (response.code() != HttpURLConnection.HTTP_OK) {
            throw HttpException(response)
        }

        return response.body() ?: throw NullPointerException("Response data is empty")
    }

    suspend fun getPersonById(id : Int) : GetPersonByIdResponse {
        val response = peopleApi.getPersonById(id)

        if (response.code() != HttpURLConnection.HTTP_OK) {
            throw HttpException(response)
        }

        return response.body() ?: throw NullPointerException("Response data is empty")
    }


    suspend fun getPersonAnime(id : Int) : GetPersonAnimeResponse {
        val response = peopleApi.getPersonAnime(id)

        if (response.code() != HttpURLConnection.HTTP_OK) {
            throw HttpException(response)
        }

        return response.body() ?: throw NullPointerException("Response data is empty")
    }

    suspend fun getPersonVoices(id : Int) : GetPersonVoicesResponse {
        val response = peopleApi.getPersonVoices(id)

        if (response.code() != HttpURLConnection.HTTP_OK) {
            throw HttpException(response)
        }

        return response.body() ?: throw NullPointerException("Response data is empty")
    }

    suspend fun getPersonManga(id : Int) : GetPersonMangaResponse {
        val response = peopleApi.getPersonManga(id)

        if (response.code() != HttpURLConnection.HTTP_OK) {
            throw HttpException(response)
        }

        return response.body() ?: throw NullPointerException("Response data is empty")
    }

    suspend fun getPersonPictures(id : Int) : GetPersonPicturesResponse {
        val response = peopleApi.getPersonPictures(id)

        if (response.code() != HttpURLConnection.HTTP_OK) {
            throw HttpException(response)
        }

        return response.body() ?: throw NullPointerException("Response data is empty")
    }
}