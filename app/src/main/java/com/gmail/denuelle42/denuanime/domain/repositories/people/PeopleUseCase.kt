package com.gmail.denuelle42.denuanime.domain.repositories.people

import com.gmail.denuelle42.denuanime.data.repositories.people.PeopleRepository
import com.gmail.denuelle42.denuanime.data.repositories.people.request.GetPeopleSearchRequest
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPeopleSearchResponse
import com.gmail.denuelle42.denuanime.di.modules.IoDispatcher
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
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
}