package com.gmail.denuelle42.denuanime.domain.repositories.recommendations

import com.gmail.denuelle42.denuanime.data.repositories.recommendations.RecommendationsRepository
import com.gmail.denuelle42.denuanime.data.repositories.recommendations.response.GetRecentAnimeRecommendationsResponse
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
    fun getRecentAnimeRecommendations() : Flow<GetRecentAnimeRecommendationsResponse> {
        return flow {
            val response = recommendationsRepository.getRecentAnimeRecommendations()
            emit(response)
        }.flowOn(ioDispatcher)
    }
}