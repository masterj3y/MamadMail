package masterj3y.github.mamadmail.features.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import masterj3y.github.mamadmail.features.auth.ui.AuthScreen
import masterj3y.github.mamadmail.features.home.HomeScreen

@Composable
fun MainScreen(isUserAuthenticated: Boolean) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (isUserAuthenticated) Route.Home.route else Route.Authentication.route
    ) {
        composable(Route.Home.route) { HomeScreen() }
        composable(Route.Authentication.route) { AuthScreen() }
    }
}

sealed interface Route {

    object Home : Route {
        const val route: String = "home"
    }

    object Authentication : Route {
        const val route: String = "authentication"
    }
}