package com.gmail.denuelle42.denuanime.data.repositories.people

import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPeopleSearchResponse
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPersonAnimeResponse
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPersonByIdResponse
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPersonFullByIdResponse
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPersonMangaResponse
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPersonPicturesResponse
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPersonVoicesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface PeopleAPI {
    @GET("people")
    suspend fun getPeopleSearch(@QueryMap params : Map<String, String>) : Response<GetPeopleSearchResponse>

    @GET("people/{id}/full")
    suspend fun getPersonFullById(@Path("id") id : Int) : Response<GetPersonFullByIdResponse>

    @GET("people/{id}")
    suspend fun getPersonById(@Path("id") id : Int) : Response<GetPersonByIdResponse>

    @GET("people/{id}/anime")
    suspend fun getPersonAnime(@Path("id") id : Int) : Response<GetPersonAnimeResponse>

    @GET("people/{id}/voices")
    suspend fun getPersonVoices(@Path("id") id : Int) : Response<GetPersonVoicesResponse>

    @GET("people/{id}/manga")
    suspend fun getPersonManga(@Path("id") id : Int) : Response<GetPersonMangaResponse>

    @GET("people/{id}/pictures")
    suspend fun getPersonPictures(@Path("id") id : Int) : Response<GetPersonPicturesResponse>


}