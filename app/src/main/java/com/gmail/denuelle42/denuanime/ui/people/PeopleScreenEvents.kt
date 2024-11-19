package com.gmail.denuelle42.denuanime.ui.people

import com.gmail.denuelle42.denuanime.data.repositories.people.request.GetPeopleSearchRequest

sealed class PeopleScreenEvents {
    data class GetPeopleSearch(val request : GetPeopleSearchRequest) : PeopleScreenEvents()
    data class GetPersonFullById(val id : Int) : PeopleScreenEvents()
    data class GetPersonById(val id : Int) : PeopleScreenEvents()
    data class GetPersonAnime(val id : Int) : PeopleScreenEvents()
    data class GetPersonVoices(val id : Int) : PeopleScreenEvents()
    data class GetPersonManga(val id : Int) : PeopleScreenEvents()
    data class GetPersonPictures(val id : Int) : PeopleScreenEvents()

}