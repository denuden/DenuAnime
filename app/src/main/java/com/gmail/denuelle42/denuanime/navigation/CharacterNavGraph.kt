package com.gmail.denuelle42.denuanime.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.gmail.denuelle42.denuanime.ui.anime.characters.CharacterListScreen

fun NavGraphBuilder.addCharacterNavGraph(
    navController: NavController
){
    //**==============NOTE
    /**
     * ADD AN INITIAL VALUE TO ROUTE WITH ARGUMENTS WHEN PASSING IT AS A START DESTINATION
     */
    navigation<RootGraphs.CharacterGraph>(startDestination = CharacterScreens.CharacterListNavigation(id = 0)){
        composable<CharacterScreens.CharacterListNavigation> { backStackEntry ->
            val arguments = backStackEntry.toRoute<CharacterScreens.CharacterListNavigation>()

            CharacterListScreen(
                onNavigate = { navController.navigate(it) },
                onPopBackStack = { navController.popBackStack() },
                id =  arguments.id
            )
        }
    }
}