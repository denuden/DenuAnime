package com.gmail.denuelle42.denuanime.ui.people

import com.gmail.denuelle42.denuanime.data.repositories.people.request.GetPeopleSearchRequest

sealed class PeopleScreenEvents {
    data class OnGetPeopleSearch(val request : GetPeopleSearchRequest) : PeopleScreenEvents()
    data class OnGetPersonFullById(val id : Int) : PeopleScreenEvents()
    data class OnGetPersonById(val id : Int) : PeopleScreenEvents()
    data class OnGetPersonAnime(val id : Int) : PeopleScreenEvents()
    data class OnGetPersonVoices(val id : Int) : PeopleScreenEvents()
    data class OnGetPersonManga(val id : Int) : PeopleScreenEvents()
    data class OnGetPersonPictures(val id : Int) : PeopleScreenEvents()

    //Navigation
    data class OnNavigateToPersonDetailsScreen(val id : Int) : PeopleScreenEvents()
}