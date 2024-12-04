package com.gmail.denuelle42.denuanime.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.gmail.denuelle42.denuanime.ui.anime.AnimeDetailsScreen

fun NavGraphBuilder.addAnimeNavGraph(
    navController: NavController
){
    //**==============NOTE
    /**
     * ADD AN INITIAL VALUE TO ROUTE WITH ARGUMENTS WHEN PASSING IT AS A START DESTINATION
     */
    navigation<RootGraphs.AnimeGraph>(startDestination = AnimeScreens.AnimeDetailsNavigation(id = 0)){
        composable<AnimeScreens.AnimeDetailsNavigation> { backStackEntry ->
            val arguments = backStackEntry.toRoute<AnimeScreens.AnimeDetailsNavigation>()

            AnimeDetailsScreen(
                onNavigate = { navController.navigate(it) },
                onPopBackStack = { navController.popBackStack() },
                id =  arguments.id
            )
        }
    }
}