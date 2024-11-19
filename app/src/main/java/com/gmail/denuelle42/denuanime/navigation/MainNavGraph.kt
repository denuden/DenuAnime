package com.gmail.denuelle42.denuanime.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.gmail.denuelle42.denuanime.ui.favorites.FavoritesScreen
import com.gmail.denuelle42.denuanime.ui.home.HomeScreen

fun NavGraphBuilder.addMainNavGraph(navController: NavController){
    navigation<RootGraphs.MainGraph>(startDestination = MainScreens.HomeNavigation){
        composable<MainScreens.HomeNavigation> {
            HomeScreen(
                onPopBackStack = { navController.popBackStack() },
                onNavigate = { navController.navigate(it) }
            )
        }

        composable<MainScreens.FavoritesNavigation> {
            FavoritesScreen(
                onPopBackStack = { navController.popBackStack() },
                onNavigate = { navController.navigate(it) }
            )
        }
    }
}