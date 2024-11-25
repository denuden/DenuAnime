package com.gmail.denuelle42.denuanime.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.gmail.denuelle42.denuanime.ui.people.PeopleScreen
import com.gmail.denuelle42.denuanime.ui.people.PersonDetailsScreen

fun NavGraphBuilder.addPeopleNavGraph(
    navController : NavController
){
    navigation<RootGraphs.PeopleGraph>(startDestination = PeopleScreens.PeopleNavigation){
        composable<PeopleScreens.PeopleNavigation> {
            PeopleScreen(
                onPopBackStack = { navController.popBackStack() },
                onNavigate = { navController.navigate(it) }
            )
        }
        composable<PeopleScreens.PeopleDetailsNavigation> { backStackEntry ->
            val arguments = backStackEntry.toRoute<PeopleScreens.PeopleDetailsNavigation>()
            PersonDetailsScreen(
                onPopBackStack = { navController.popBackStack() },
                onNavigate = { navController.navigate(it) },
                id = arguments.id
            )
        }
    }
}