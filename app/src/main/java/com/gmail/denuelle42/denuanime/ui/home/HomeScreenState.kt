package com.gmail.denuelle42.denuanime.ui.home

import com.gmail.denuelle42.denuanime.data.remote.models.people.People

data class HomeScreenState(
    val people: List<People>? = null,

    val isGetPeopleSearchLoading: Boolean = false,
)