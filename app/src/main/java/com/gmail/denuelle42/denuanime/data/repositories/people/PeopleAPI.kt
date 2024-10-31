package com.gmail.denuelle42.denuanime.data.repositories.people

import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPeopleSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface PeopleAPI {
    @GET("people")
    suspend fun getPeopleSearch(@QueryMap params : Map<String, String>) : Response<GetPeopleSearchResponse>
}