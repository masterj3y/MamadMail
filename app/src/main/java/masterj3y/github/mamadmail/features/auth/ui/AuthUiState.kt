package masterj3y.github.mamadmail.features.auth.ui

import androidx.compose.runtime.Immutable
import masterj3y.github.mamadmail.common.extensions.ApiErrorResponse
import masterj3y.github.mamadmail.features.auth.model.Domain

@Immutable
data class AuthUiState(
    val screenMode: AuthScreenMode,
    val authenticating: Boolean,
    val selectedDomain: Domain?,
    val apiErrorResponse: ApiErrorResponse?
) {

    companion object {
        val initial: AuthUiState
            get() = AuthUiState(
                screenMode = AuthScreenMode.CreateAccount,
                authenticating = false,
                selectedDomain = null,
                apiErrorResponse = null
            )
    }
}

sealed interface AuthScreenMode {
    object CreateAccount : AuthScreenMode
    object Login : AuthScreenMode
}