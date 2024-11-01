package com.gmail.denuelle42.denuanime.ui.home

import com.gmail.denuelle42.denuanime.data.repositories.people.request.GetPeopleSearchRequest

sealed class HomeScreenEvents {
    data class OnGetTopPeopleSearch(val request: GetPeopleSearchRequest) :  HomeScreenEvents()
}