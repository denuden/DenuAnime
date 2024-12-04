package com.gmail.denuelle42.denuanime.domain.repositories.anime

import com.gmail.denuelle42.denuanime.data.repositories.anime.AnimeRepository
import com.gmail.denuelle42.denuanime.data.repositories.anime.request.GetAnimeSearchRequest
import com.gmail.denuelle42.denuanime.data.repositories.anime.request.GetTopAnimeRequest
import com.gmail.denuelle42.denuanime.data.repositories.anime.response.GetAnimeFullByIdResponse
import com.gmail.denuelle42.denuanime.data.repositories.anime.response.GetAnimeSearchResponse
import com.gmail.denuelle42.denuanime.data.repositories.anime.response.GetRecentEpisodesResponse
import com.gmail.denuelle42.denuanime.data.repositories.anime.response.GetTopAnimeResponse
import com.gmail.denuelle42.denuanime.di.modules.IoDispatcher
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ViewModelScoped
class AnimeUseCase @Inject constructor(
    private val animeRepository: AnimeRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    fun getTopAnime(request : GetTopAnimeRequest) : Flow<GetTopAnimeResponse> {
        return flow {
            val response = animeRepository.getTopAnime(request)
            emit(response)
        }.flowOn(ioDispatcher)
    }

    fun getAnimeSearch(request: GetAnimeSearchRequest) : Flow<GetAnimeSearchResponse> {
        return flow {
            val response = animeRepository.getAnimeSearch(request)
            emit(response)
        }.flowOn(ioDispatcher)
    }

    fun getRecentEpisodes() : Flow<GetRecentEpisodesResponse> {
        return flow {
            val response = animeRepository.getRecentEpisodes()
            emit(response)
        }.flowOn(ioDispatcher)
    }

    fun getAnimeFullById(id : Int) : Flow<GetAnimeFullByIdResponse> {
        return flow {
            val response = animeRepository.getAnimeFullById(id)
            emit(response)
        }.flowOn(ioDispatcher)
    }
}