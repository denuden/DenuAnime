package com.gmail.denuelle42.denuanime

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.gmail.denuelle42.denuanime.navigation.RootGraphs
import com.gmail.denuelle42.denuanime.navigation.addMainNavGraph

@Composable
fun AppNavigation(navController: NavHostController) {
   NavHost(navController = navController, startDestination = RootGraphs.MainGraph){
      addMainNavGraph(navController)
   }
}