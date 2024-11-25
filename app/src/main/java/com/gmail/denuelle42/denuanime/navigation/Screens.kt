package com.gmail.denuelle42.denuanime.navigation

import com.gmail.denuelle42.denuanime.data.remote.models.SampleModel
import kotlinx.serialization.Serializable

/**
 * Main Destinations for nested graph, like a web link  E.G. auth/login, auth/register. main/home. main/profile
 * article/home, article/view
 */
sealed class RootGraphs {
    @Serializable
    data object SampleGraph : RootGraphs()
    @Serializable
    data object MainGraph : RootGraphs()
    @Serializable
    data object PeopleGraph : RootGraphs()
}

/**
 *  General or shared type of all screens
 */
sealed interface NavigationScreens

sealed class SampleScreens : NavigationScreens {
    @Serializable
    data object SampleNavigation : SampleScreens()
    @Serializable
    data class SampleDetailsNavigation(val sampleModel: SampleModel) : SampleScreens()
}

sealed class MainScreens : NavigationScreens {
    @Serializable
    data object HomeNavigation : MainScreens()
    @Serializable
    data object FavoritesNavigation : MainScreens()
    @Serializable
    data object SettingsNavigation : MainScreens()
}

sealed class PeopleScreens : NavigationScreens {
    @Serializable
    data object PeopleNavigation : PeopleScreens()
    @Serializable
    data class PeopleDetailsNavigation(val id : Int) : PeopleScreens()
}