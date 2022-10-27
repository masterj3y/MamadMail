package masterj3y.github.mamadmail.features.profile.ui

import androidx.compose.runtime.Immutable
import masterj3y.github.mamadmail.common.extensions.ApiErrorResponse
import masterj3y.github.mamadmail.features.profile.model.Profile

@Immutable
data class ProfileUiState(
    val loadingProfile: Boolean,
    val profile: Profile?,
    val deletingAccount: Boolean,
    val apiErrorResponse: ApiErrorResponse?
) {

    companion object {
        val initial: ProfileUiState
            get() = ProfileUiState(
                loadingProfile = false,
                profile = null,
                deletingAccount = false,
                apiErrorResponse = null
            )
    }
}