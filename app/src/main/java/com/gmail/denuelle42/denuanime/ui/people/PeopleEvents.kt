package com.gmail.denuelle42.denuanime.ui.people

import com.gmail.denuelle42.denuanime.data.repositories.people.request.GetPeopleSearchRequest

sealed class PeopleEvents {
    data class OnGetPeopleSearch(val request : GetPeopleSearchRequest) : PeopleEvents()
    data class OnGetPersonFullById(val id : Int) : PeopleEvents()
    data class OnGetPersonById(val id : Int) : PeopleEvents()
    data class OnGetPersonAnime(val id : Int) : PeopleEvents()
    data class OnGetPersonVoices(val id : Int) : PeopleEvents()
    data class OnGetPersonManga(val id : Int) : PeopleEvents()
    data class OnGetPersonPictures(val id : Int) : PeopleEvents()

    data class OnSearchQueryChanged(val query : String) : PeopleEvents()

    //Navigation
    data class OnNavigateToPersonDetailsScreen(val id : Int) : PeopleEvents()
}