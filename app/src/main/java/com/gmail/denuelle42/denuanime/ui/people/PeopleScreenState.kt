package com.gmail.denuelle42.denuanime.ui.people

import com.gmail.denuelle42.denuanime.data.remote.models.people.People
import com.gmail.denuelle42.denuanime.data.remote.models.people.PeopleAnime
import com.gmail.denuelle42.denuanime.data.remote.models.people.Voices

data class PeopleScreenState(
    val peopleList : List<People>? = null,
    val isGetPeopleSearchLoading : Boolean = false,

    val personDetails : People? = null,
    val isGetPersonByFullIdLoading : Boolean = false,
    val isGetPersonByIdLoading : Boolean = false,

    val personAnimeList : List<PeopleAnime>? = null,
    val isGetPersonAnimeLoading : Boolean = false,

    val personVoicesList : List<Voices>? = null,
    val isGetPersonVoicesLoading : Boolean = false,

    val personMangaList : List<Voices>? = null,
    val isGetPersonMangaLoading : Boolean = false,

    val personPicturesList : List<Voices>? = null,
    val isGetPersonPicturesLoading : Boolean = false,


    )