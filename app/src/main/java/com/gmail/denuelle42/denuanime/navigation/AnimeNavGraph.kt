package com.gmail.denuelle42.denuanime.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation

fun NavGraphBuilder.addAnimeNavGraph(
    navController: NavController
){
    navigation<RootGraphs.AnimeGraph>(startDestination = AnimeScreens.AnimeDetailsNavigation){

    }
}