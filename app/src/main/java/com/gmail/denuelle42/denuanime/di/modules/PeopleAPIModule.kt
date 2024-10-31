package com.gmail.denuelle42.denuanime.di.modules

import com.gmail.denuelle42.denuanime.data.repositories.people.PeopleAPI
import com.gmail.denuelle42.denuanime.data.repositories.people.PeopleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

//@Module
//@InstallIn(ViewModelComponent::class)
//object PeopleAPIModule {
//
//    @Provides
//    @ViewModelScoped
//    fun providePeopleRepository(peopleAPI: PeopleAPI): PeopleRepository {
//        return PeopleRepository(peopleAPI)
//    }
//}
//
