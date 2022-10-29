package masterj3y.github.mamadmail.features.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import masterj3y.github.mamadmail.features.auth.ui.AuthScreen
import masterj3y.github.mamadmail.features.home.HomeScreen
import masterj3y.github.mamadmail.features.message.ui.MessageDetailsScreen

@Composable
fun MainScreen(isUserAuthenticated: Boolean) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (isUserAuthenticated) Route.Home.ROUTE else Route.Authentication.ROUTE
    ) {
        composable(Route.Home.ROUTE) {
            HomeScreen(navigateToMessageDetails = { messageId ->
                navController.navigate(Route.MessageDetails.ROUTE + "/" + messageId) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            )
        }
        composable(Route.Authentication.ROUTE) { AuthScreen() }
        composable(Route.MessageDetails.ROUTE + "/{${Route.MessageDetails.MESSAGE_ID_KEY}}") {
            MessageDetailsScreen(
                messageId = it.arguments?.getString(Route.MessageDetails.MESSAGE_ID_KEY),
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed interface Route {

    object Home : Route {
        const val ROUTE: String = "home"
    }

    object Authentication : Route {
        const val ROUTE: String = "authentication"
    }

    object MessageDetails : Route {
        const val MESSAGE_ID_KEY = "msg-id-key"
        const val ROUTE: String = "message-details"
    }
}