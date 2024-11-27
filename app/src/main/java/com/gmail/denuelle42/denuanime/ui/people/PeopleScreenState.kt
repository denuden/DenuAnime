package com.gmail.denuelle42.denuanime.ui.people

import com.gmail.denuelle42.denuanime.data.remote.models.people.People
import com.gmail.denuelle42.denuanime.data.remote.models.people.PeopleAnime
import com.gmail.denuelle42.denuanime.data.remote.models.people.Voices
import com.gmail.denuelle42.denuanime.data.repositories.people.response.GetPersonFullByIdResponse

data class PeopleScreenState(
    val peopleList : List<People>? = null,
    val isGetPeopleSearchLoading : Boolean = false, //exception to initial loading, because api call isn't fired automatically at launch

    val personDetails : GetPersonFullByIdResponse? = null,
    val isGetPersonByFullIdLoading : Boolean = true,
    val isGetPersonByIdLoading : Boolean = true,

    val personAnimeList : List<PeopleAnime>? = null,
    val isGetPersonAnimeLoading : Boolean = true,

    val personVoicesList : List<Voices>? = null,
    val isGetPersonVoicesLoading : Boolean = true,

    val personMangaList : List<Voices>? = null,
    val isGetPersonMangaLoading : Boolean = true,

    val personPicturesList : List<Voices>? = null,
    val isGetPersonPicturesLoading : Boolean = true,


    )