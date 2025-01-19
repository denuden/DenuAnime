package com.gmail.denuelle42.denuanime.domain.repositories.recommendations

import com.gmail.denuelle42.denuanime.data.remote.models.animedetails.AnimeDetails
import com.gmail.denuelle42.denuanime.data.repositories.recommendations.RecommendationsRepository
import com.gmail.denuelle42.denuanime.di.modules.IoDispatcher
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ViewModelScoped
class RecommendationsUseCase @Inject constructor(
    private val recommendationsRepository: RecommendationsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
){
    fun getRecentAnimeRecommendations() : Flow<List<AnimeDetails>> {
        return flow {
            val response = recommendationsRepository.getRecentAnimeRecommendations()
            val listOfAnime = mutableListOf<AnimeDetails>()
            response.data?.onEach { data ->
                data.entry?.onEach { anime ->
                    listOfAnime.add(anime)
                }
        }
            emit(listOfAnime)
        }.flowOn(ioDispatcher)
    }

    fun getRecentMangaRecommendations() : Flow<List<AnimeDetails>> {
        return flow {
            val response = recommendationsRepository.getRecentMangaRecommendations()
            val listOfManga = mutableListOf<AnimeDetails>()
            response.data?.onEach { data ->
                data.entry?.onEach { anime ->
                    listOfManga.add(anime)
                }
            }
            emit(listOfManga)
        }.flowOn(ioDispatcher)
    }
}