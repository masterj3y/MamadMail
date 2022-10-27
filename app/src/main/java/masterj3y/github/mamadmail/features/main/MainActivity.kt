package masterj3y.github.mamadmail.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import masterj3y.github.mamadmail.common.extensions.rememberFlowWithLifecycle
import masterj3y.github.mamadmail.common.session.UserSessionManager
import masterj3y.github.mamadmail.features.auth.ui.AuthScreen
import masterj3y.github.mamadmail.ui.theme.MamadMailTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userSessionManager: UserSessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MamadMailTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val isUserAuthenticatedFlow =
                        rememberFlowWithLifecycle(userSessionManager.isUserAuthenticated)
                    val isUserAuthenticated by isUserAuthenticatedFlow.collectAsState(initial = null)

                    AnimatedVisibility(
                        visible = isUserAuthenticated == false,
                        enter = slideInHorizontally(),
                        exit = fadeOut()
                    ) {

                        AuthScreen()
                    }

                    AnimatedVisibility(
                        visible = isUserAuthenticated == true,
                        enter = slideInHorizontally(),
                        exit = fadeOut()
                    ) {
                        MainScreen()
                    }
                }
            }
        }
    }
}