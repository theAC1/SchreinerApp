package com.brunner.lignacalc.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.brunner.lignacalc.ui.screens.HomeScreen
import com.brunner.lignacalc.ui.screens.calculators.*

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Gehrung : Screen("gehrung")
    object Schnittgeschwindigkeit : Screen("schnittgeschwindigkeit")
    object Zahnvorschub : Screen("zahnvorschub")
    object Kantenmaterial : Screen("kantenmaterial")
    object Plattenmaterial : Screen("plattenmaterial")
    object GoldenerSchnitt : Screen("goldener_schnitt")
    object Holzschwund : Screen("holzschwund")
    object Einheitenrechner : Screen("einheitenrechner")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToCalculator = { route ->
                    navController.navigate(route)
                }
            )
        }
        composable(Screen.Gehrung.route) {
            GehrungScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Schnittgeschwindigkeit.route) {
            SchnittgeschwindigkeitScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Zahnvorschub.route) {
            ZahnvorschubScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Kantenmaterial.route) {
            KantenmaterialScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Plattenmaterial.route) {
            PlattenmaterialScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.GoldenerSchnitt.route) {
            GoldenerSchnittScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Holzschwund.route) {
            HolzschwundScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Einheitenrechner.route) {
            EinheitenrechnerScreen(onBack = { navController.popBackStack() })
        }
    }
}
