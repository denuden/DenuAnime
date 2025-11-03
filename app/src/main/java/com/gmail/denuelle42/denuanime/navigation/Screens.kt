package com.gmail.denuelle42.denuanime.navigation

import com.gmail.denuelle42.denuanime.data.remote.models.SampleModel
import kotlinx.serialization.Serializable

/**
 * Main Destinations for nested graph, like a web link  E.G. auth/login, auth/register. main/home. main/profile
 * article/home, article/view
 */


/**
 * Added explanation or analogy on how this works
 *
 * RootGraphs are like the first part of path segments in a url
 * E.G. sample.com/maingraph ; /maingraph is the first path and thats our rootgraphs
 *
 * Inside those rootgraphs declared in each NavGraphBuilder are the other segments or pages that is associated with that rootgraph
 *
 * sample.com/maingraph/mainscreens.homenavigation
 * sample.com/maingraph/mainscreens.favoritesnavigation
 */

/**
 * ---------------------------
 *  Root Graphs & Screens
 * ---------------------------
 *
 * Think of this like website URL paths:
 *
 *    https://sample.com/main/home
 *    https://sample.com/anime/details/1
 *
 * Each "RootGraph" represents the first segment of the path (e.g. `/main`, `/anime`).
 * Inside each RootGraph are multiple "Screens" that represent the pages or sub-paths.
 *
 * Example:
 *   RootGraphs.MainGraph      → corresponds to "/main"
 *   MainScreens.HomeNavigation → corresponds to "/main/home"
 *   MainScreens.FavoritesNavigation → "/main/favorites"
 */


@Serializable
sealed class RootGraphs {
    @Serializable
    data object SampleGraph : RootGraphs()
    @Serializable
    data object MainGraph : RootGraphs()
    @Serializable
    data object AnimeGraph : RootGraphs()
    @Serializable
    data object PeopleGraph : RootGraphs()
    @Serializable
    data object CharacterGraph : RootGraphs()
}

/**
 *  General or shared type of all screens
 */
@Serializable
sealed interface NavigationScreens

@Serializable
sealed class SampleScreens : NavigationScreens {
    @Serializable
    data object SampleNavigation : SampleScreens()
    @Serializable
    data class SampleDetailsNavigation(val sampleModel: SampleModel) : SampleScreens()
}

@Serializable
sealed class MainScreens : NavigationScreens {
    @Serializable
    data object HomeNavigation : MainScreens()
    @Serializable
    data object FavoritesNavigation : MainScreens()
    @Serializable
    data object SettingsNavigation : MainScreens()
}

@Serializable
sealed class PeopleScreens : NavigationScreens {
    @Serializable
    data object PeopleNavigation : PeopleScreens()
    @Serializable
    data class PeopleDetailsNavigation(val id : Int) : PeopleScreens()
}

@Serializable
sealed class AnimeScreens : NavigationScreens {
    @Serializable
    data class AnimeDetailsNavigation(val id : Int) : AnimeScreens()

    @Serializable
    data object AnimeSearchNavigation : AnimeScreens()
}

@Serializable
sealed class CharacterScreens : NavigationScreens {
    @Serializable
    /**
     * @param id is the id of the anime, to get all characters in it
     */
    data class CharacterListNavigation(val id : Int) : CharacterScreens()
}