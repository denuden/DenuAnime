package com.gmail.denuelle42.denuanime

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.gmail.denuelle42.denuanime.navigation.RootGraphs
import com.gmail.denuelle42.denuanime.navigation.addMainNavGraph
import com.gmail.denuelle42.denuanime.navigation.addPeopleNavGraph

@Composable
fun AppNavigation(navController: NavHostController) {
   NavHost(navController = navController, startDestination = RootGraphs.MainGraph){
      addMainNavGraph(navController)
      addPeopleNavGraph(navController)
   }
}


/**
 * Gets proper top app bar title
 * based on current nvigation
 */
fun getTopBarTitle(currentRoute : String) : String{
   //Get route name as package  and get the last one the get the class name
   val route = currentRoute.substringAfterLast(".")

   //check if route has "/" means it has arguments, remove it so we can verify route itself
   val cleaned = if(route.contains("/")) route.substringBeforeLast("/") else route

   //check route with specific route under items in sealed class NavigationScreens (not RootGraphs)
   return when(cleaned){
      "PeopleNavigation" -> "Search People"
      "PeopleDetailsNavigation" -> "Details"
      else -> "DenuAnime"
   }
}