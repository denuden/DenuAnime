package com.gmail.denuelle42.denuanime.data.repositories.genre

import com.gmail.denuelle42.denuanime.data.repositories.genre.request.GetAnimeGenresRequest
import com.gmail.denuelle42.denuanime.data.repositories.genre.response.GetAnimeGenresResponse
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

@ViewModelScoped
class GenreRepository @Inject constructor(
    private val genreAPI: GenreAPI
) {
    suspend fun getAnimeGenres(request: GetAnimeGenresRequest) : GetAnimeGenresResponse {
        val response = genreAPI.getAnimeGenres(request.filter.orEmpty())

        if(response.code() != HttpURLConnection.HTTP_OK) {
            throw HttpException(response)
        }

        return response.body() ?: throw NullPointerException("Response data is empty")
    }
}