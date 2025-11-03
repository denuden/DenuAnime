package com.gmail.denuelle42.denuanime.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.gmail.denuelle42.denuanime.R

/**
 * 🧭 Summary: Role of RootGraphs in Navigation
 *
 * Without RootGraphs:
 * -------------------
 * You can directly define all composable destinations inside the NavHost.
 *
 * Example (traditional approach):
 * NavHost {
 *     composable<MainScreens.HomeNavigation>()
 *     composable<AnimeScreens.AnimeDetailsNavigation>()
 *     composable<CharacterScreens.CharacterListNavigation>()
 * }
 *
 * This works perfectly fine — each screen is just registered under the main NavHost.
 *
 *
 * With RootGraphs:
 * ----------------
 * RootGraphs act as logical or hierarchical containers for related screens.
 * They let you group screens into modular subgraphs, similar to subfolders in a file structure.
 *
 * Example (with RootGraphs):
 * NavHost {
 *     navigation<RootGraphs.MainGraph> {
 *         composable<MainScreens.HomeNavigation>()
 *         composable<MainScreens.FavoritesNavigation>()
 *     }
 *
 *     navigation<RootGraphs.AnimeGraph> {
 *         composable<AnimeScreens.AnimeSearchNavigation>()
 *         composable<AnimeScreens.AnimeDetailsNavigation>()
 *     }
 * }
 *
 * 💡 Analogy:
 * If your routes were URLs, RootGraphs represent the first path segment:
 *
 *   sample.com/main/home         → RootGraphs.MainGraph → MainScreens.HomeNavigation
 *   sample.com/anime/details     → RootGraphs.AnimeGraph → AnimeScreens.AnimeDetailsNavigation
 *
 * 🧩 Purpose of RootGraphs:
 * - Organize related screens into feature-specific navigation modules.
 * - Provide cleaner separation when you have multiple functional sections (Main, Anime, People, etc.).
 * - Make your navigation scalable, readable, and modular for large apps.
 *
 * TL;DR:
 * RootGraphs are optional but useful. Without them, all screens live directly in the NavHost.
 * With them, you get structure: NavHost → RootGraph → Composables.
 *
 *
 * 🗂️ Visual Diagram:
 *
 * Traditional Navigation (Flat Structure)
 * ---------------------------------------
 * NavHost
 * ├── HomeScreen
 * ├── AnimeDetailsScreen
 * └── CharacterListScreen
 *
 *
 * Navigation with RootGraphs (Hierarchical)
 * -----------------------------------------
 * NavHost
 * ├── RootGraphs.MainGraph
 * │    ├── MainScreens.HomeNavigation
 * │    └── MainScreens.FavoritesNavigation
 * │
 * ├── RootGraphs.AnimeGraph
 * │    ├── AnimeScreens.AnimeSearchNavigation
 * │    └── AnimeScreens.AnimeDetailsNavigation
 * │
 * └── RootGraphs.CharacterGraph
 *      └── CharacterScreens.CharacterListNavigation
 *
 *
 * 🧭 In short:
 * RootGraphs = Subgraphs that group related destinations
 * NavHost = The entry point that contains all RootGraphs
 * Composables = The actual screens inside each graph
 */

@Composable
fun AppNavigation(navController: NavHostController) {
   NavHost(navController = navController, startDestination = RootGraphs.MainGraph){
      addMainNavGraph(navController)
      addPeopleNavGraph(navController)

      //**==============NOTE
      /**
       * ADD AN INITIAL VALUE TO ARGUMENTS TO ROUTE WITH ARGUMENTS WHEN PASSING IT AS A START DESTINATION
       * See more at addAnimeNavGraph() file
       * Sample **
       *  navigation<RootGraphs.AnimeGraph>(startDestination = AnimeScreens.AnimeDetailsNavigation(id = 0)){}
       */
      addAnimeNavGraph(navController)
      addCharacterNavGraph(navController)
   }
}


/**
 * Gets proper top app bar title
 * based on current nvigation
 */
fun getTopBarTitle(currentRoute : String, context : Context) : String{
   //Get route name as package  and get the last one the get the class name
   val route = currentRoute.substringAfterLast(".")

   //check if route has "/" means it has arguments, remove it so we can verify route itself
   val cleaned = if(route.contains("/")) route.substringBeforeLast("/") else route

   //check route with specific route under items in sealed class NavigationScreens (not RootGraphs)
   return when(cleaned){
      context.getString(R.string.nav_peoplenavigation) -> context.getString(R.string.screen_search_people)
      context.getString(R.string.nav_peopledetailsnavigation) -> context.getString(R.string.screen_person_details)
      context.getString(R.string.nav_animesearchnavigation) -> context.getString(R.string.screen_search_anime)
      context.getString(R.string.nav_animedetailsnavigation) -> context.getString(R.string.screen_anime_details)
      context.getString(R.string.nav_characterslistnavigation) -> context.getString(R.string.screen_character_list)
      else ->  context.getString(R.string.app_name)
   }
}