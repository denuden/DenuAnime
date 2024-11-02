package com.gmail.denuelle42.denuanime.di.modules

import com.gmail.denuelle42.denuanime.data.repositories.anime.AnimeAPI
import com.gmail.denuelle42.denuanime.data.repositories.people.PeopleAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object APIModule {

    @Provides
    @Singleton
    fun providePeopleAPI(retrofit: Retrofit): PeopleAPI {
        return retrofit.create(PeopleAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideAnimeAPI(retrofit: Retrofit): AnimeAPI {
        return retrofit.create(AnimeAPI::class.java)
    }
}