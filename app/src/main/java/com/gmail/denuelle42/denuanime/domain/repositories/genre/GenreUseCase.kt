package com.gmail.denuelle42.denuanime.domain.repositories.genre

import com.gmail.denuelle42.denuanime.data.repositories.genre.GenreRepository
import com.gmail.denuelle42.denuanime.data.repositories.genre.request.GetAnimeGenresRequest
import com.gmail.denuelle42.denuanime.data.repositories.genre.request.GetMangaGenresRequest
import com.gmail.denuelle42.denuanime.data.repositories.genre.response.GetAnimeGenresResponse
import com.gmail.denuelle42.denuanime.data.repositories.genre.response.GetMangaGenresResponse
import com.gmail.denuelle42.denuanime.di.modules.IoDispatcher
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ViewModelScoped
class GenreUseCase @Inject constructor(
    private val genreRepository: GenreRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
){
    fun getAnimeGenres(request : GetAnimeGenresRequest) : Flow<GetAnimeGenresResponse> {
        return flow {
            val response = genreRepository.getAnimeGenres(request)
            emit(response)
        }.flowOn(ioDispatcher)
    }

    fun getMangaGenres(request : GetMangaGenresRequest) : Flow<GetMangaGenresResponse> {
        return flow {
            val response = genreRepository.getMangaGenres(request)
            emit(response)
        }.flowOn(ioDispatcher)
    }
}