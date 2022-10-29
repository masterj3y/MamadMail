package masterj3y.github.mamadmail.features.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import masterj3y.github.mamadmail.common.extensions.parsedError
import masterj3y.github.mamadmail.common.session.UserSessionManager
import masterj3y.github.mamadmail.features.profile.data.ProfileRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val userSessionManager: UserSessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileUiState.initial)
    val state = _state.asStateFlow()

    init {
        userSessionManager.isUserAuthenticated
            .filterNotNull()
            .onEach { isUserAuthenticated ->
                if (isUserAuthenticated)
                    loadProfile()
            }
            .launchIn(viewModelScope)
    }

    private fun loadProfile() {
        viewModelScope.launch {
            repository.fetchProfile()
                .onSuccess {
                    _state.update { it.copy(loadingProfile = false, profile = data) }
                }
                .onError {
                    _state.update {
                        it.copy(
                            loadingProfile = false,
                            apiErrorResponse = errorBody?.parsedError()
                        )
                    }
                }
                .onException {
                    _state.update {
                        it.copy(
                            loadingProfile = false,
                            apiErrorResponse = exception.parsedError()
                        )
                    }
                }
        }
    }

    fun deleteAccount() {
        _state.update {
            it.copy(deletingAccount = true)
        }

        viewModelScope.launch {
            state.value.profile?.id?.let { accountId ->
                repository.deleteAccount(accountId)
                    .onSuccess {
                        _state.update {
                            it.copy(
                                deletingAccount = false
                            )
                        }
                        logout()
                    }
                    .onError {
                        _state.update {
                            it.copy(
                                deletingAccount = false,
                                apiErrorResponse = errorBody?.parsedError()
                            )
                        }
                    }
                    .onException {
                        _state.update {
                            it.copy(
                                deletingAccount = false,
                                apiErrorResponse = exception.parsedError()
                            )
                        }
                    }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userSessionManager.logout()
        }
    }

    fun dismissError() {
        _state.update { it.copy(apiErrorResponse = null) }
    }
}