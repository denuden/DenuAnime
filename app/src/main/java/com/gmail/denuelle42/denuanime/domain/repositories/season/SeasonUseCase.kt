package com.gmail.denuelle42.denuanime.domain.repositories.season

import com.gmail.denuelle42.denuanime.data.repositories.anime.response.RecentEpisodesList
import com.gmail.denuelle42.denuanime.data.repositories.season.SeasonRepository
import com.gmail.denuelle42.denuanime.data.repositories.season.request.GetSeasonNowRequest
import com.gmail.denuelle42.denuanime.data.repositories.season.request.GetSeasonUpcomingRequest
import com.gmail.denuelle42.denuanime.di.modules.IoDispatcher
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ViewModelScoped
class SeasonUseCase @Inject constructor(
    private val seasonRepository: SeasonRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    fun getSeasonNow(request : GetSeasonNowRequest) : Flow<List<RecentEpisodesList>>{
        return flow {
            val response = seasonRepository.getSeasonNow(request)
            val data : MutableList<RecentEpisodesList> = mutableListOf()
            response.data?.map {
                data.add(RecentEpisodesList(entry = it, episodes = null, region_locked = null))
            }
            emit(data.toList())
        }.flowOn(ioDispatcher)
    }

    fun getSeasonUpcoming(request : GetSeasonUpcomingRequest) : Flow<List<RecentEpisodesList>>{
        return flow {
            val response = seasonRepository.getSeasonUpcoming(request)
            val data : MutableList<RecentEpisodesList> = mutableListOf()
            response.data?.map {
                data.add(RecentEpisodesList(entry = it, episodes = null, region_locked = null))
            }
            emit(data)
        }.flowOn(ioDispatcher)
    }
}