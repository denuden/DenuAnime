package com.gmail.denuelle42.denuanime.domain.repositories.people

import com.gmail.denuelle42.denuanime.data.repositories.people.PeopleRepository
import com.gmail.denuelle42.denuanime.data.repositories.people.request.GetPeopleSearchRequest
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPeopleSearchResponse
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPersonAnimeResponse
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPersonByIdResponse
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPersonFullByIdResponse
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPersonMangaResponse
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPersonPicturesResponse
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPersonVoicesResponse
import com.gmail.denuelle42.denuanime.di.modules.IoDispatcher
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ViewModelScoped
class PeopleUseCase @Inject constructor(
    private val peopleRepository: PeopleRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    fun getPeopleSearch(request : GetPeopleSearchRequest) : Flow<GetPeopleSearchResponse> {
        return flow<GetPeopleSearchResponse> {
            val response = peopleRepository.getPeopleSearch(request)
            emit(response)
        }.flowOn(ioDispatcher)
    }

    fun getPersonFullById(id : Int) : Flow<GetPersonFullByIdResponse>{
        return flow {
            // Run both repository calls concurrently using coroutine context
            val (response, picturesResponse) = coroutineScope {
                val responseDeferred = async { peopleRepository.getPersonFullById(id) }
                val picturesDeferred = async { peopleRepository.getPersonPictures(id) }
                Pair(responseDeferred.await(), picturesDeferred.await())
            }

            // Combine the results
            val formatted = if (picturesResponse.data?.isNotEmpty() == true) {
                response.copy(pictures = picturesResponse.data)
            } else {
                response
            }
            emit(formatted)
        }.flowOn(ioDispatcher)
    }
    fun getPersonById(id : Int) : Flow<GetPersonByIdResponse>{
        return flow {
            val response = peopleRepository.getPersonById(id)
            emit(response)
        }.flowOn(ioDispatcher)
    }
    fun getPersonAnime(id : Int) : Flow<GetPersonAnimeResponse>{
        return flow {
            val response = peopleRepository.getPersonAnime(id)
            emit(response)
        }.flowOn(ioDispatcher)
    }
    fun getPersonVoices(id : Int) : Flow<GetPersonVoicesResponse>{
        return flow {
            val response = peopleRepository.getPersonVoices(id)
            emit(response)
        }.flowOn(ioDispatcher)
    }
    fun getPersonManga(id : Int) : Flow<GetPersonMangaResponse>{
        return flow {
            val response = peopleRepository.getPersonManga(id)
            emit(response)
        }.flowOn(ioDispatcher)
    }
    fun getPersonPictures(id : Int) : Flow<GetPersonPicturesResponse>{
        return flow {
            val response = peopleRepository.getPersonPictures(id)
            emit(response)
        }.flowOn(ioDispatcher)
    }
}