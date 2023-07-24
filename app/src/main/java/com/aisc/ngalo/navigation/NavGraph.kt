package com.aisc.ngalo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aisc.ngalo.AdminOUserActivity
import com.aisc.ngalo.MainActivity
import com.aisc.ngalo.SplashScreen
import com.aisc.ngalo.models.BikeHireModel

@Composable
fun NavGraph (navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route)
    {
        composable(route = Screens.SplashScreen.route){
            SplashScreen(navController)
        }
        composable(route = Screens.MainActivity.route){
            MainActivity(navController)
        }
        composable(route = Screens.HireBike.route){
            BikeHireModel(navController)
        }
        composable(route = Screens.RepairBike.route){
            MainActivity(navController)
        }
        composable(route = Screens.BuyBike.route){
            MainActivity(navController)
        }
        composable(route = Screens.SignUp.route){
            MainActivity(navController)
        }

    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }