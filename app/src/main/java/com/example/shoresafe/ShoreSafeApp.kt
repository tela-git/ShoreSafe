package com.example.shoresafe

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shoresafe.ui.screens.HomeScreen
import com.example.shoresafe.ui.screens.SearchScreen

@Composable
fun ShoreSafeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Home.route
    ) {
        composable(
            route = AppScreen.Home.route
        ) {
            HomeScreen(
                navController = navController,
                modifier = modifier
                    .fillMaxSize()
            )
        }
        composable(
            route = AppScreen.Search.route
        ) {
            SearchScreen(
                navController = navController,
                modifier = modifier
                    .fillMaxSize()
            )
        }
    }
}

sealed class AppScreen(val route: String) {
    object Home: AppScreen("HomeScreen")
    object Search: AppScreen("SearchScreen")
}