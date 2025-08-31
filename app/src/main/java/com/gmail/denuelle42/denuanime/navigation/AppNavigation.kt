package com.gmail.denuelle42.denuanime.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.gmail.denuelle42.denuanime.R

@Composable
fun AppNavigation(navController: NavHostController) {
   NavHost(navController = navController, startDestination = RootGraphs.MainGraph){
      addMainNavGraph(navController)
      addPeopleNavGraph(navController)

      //**==============NOTE
      /**
       * ADD AN INITIAL VALUE TO ROUTE WITH ARGUMENTS WHEN PASSING IT AS A START DESTINATION
       * See more at addAnimeNavGraph() file
       * Sample **
       *  navigation<RootGraphs.AnimeGraph>(startDestination = AnimeScreens.AnimeDetailsNavigation(id = 0)){}
       */
      addAnimeNavGraph(navController)
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
      context.getString(R.string.nav_peopledetailsnavigation) -> context.getString(R.string.screen_details)
      context.getString(R.string.nav_peopledetailsnavigation) -> context.getString(R.string.screen_details)
      context.getString(R.string.nav_animesearchnavigation) -> context.getString(R.string.screen_search_anime)
      context.getString(R.string.nav_animedetailsnavigation) -> context.getString(R.string.screen_details)
      else ->  context.getString(R.string.app_name)
   }
}