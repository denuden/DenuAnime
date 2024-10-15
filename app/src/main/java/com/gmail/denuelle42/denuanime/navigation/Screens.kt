package com.gmail.denuelle42.denuanime.navigation

import kotlinx.serialization.Serializable

/**
 * Main Destinations for nested graph, like a web link  E.G. auth/login, auth/register. main/home. main/profile
 * article/home, article/view
 */
sealed class RootGraphs {
    @Serializable
    data object MainGraph : RootGraphs()
}

/**
 *  General or shared type of all screens
 */
sealed interface NavigationScreens


sealed class MainScreens : NavigationScreens {
    @Serializable
    data object HomeNavigation : MainScreens()
    @Serializable
    data object FavoritesNavigation : MainScreens()
    @Serializable
    data object SettingsNavigation : MainScreens()
}