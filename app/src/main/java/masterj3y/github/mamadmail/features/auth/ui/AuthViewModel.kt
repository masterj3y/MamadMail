package masterj3y.github.mamadmail.features.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import masterj3y.github.mamadmail.common.extensions.parsedError
import masterj3y.github.mamadmail.common.session.UserCredentials
import masterj3y.github.mamadmail.common.session.UserSessionManager
import masterj3y.github.mamadmail.features.auth.data.AuthRepository
import masterj3y.github.mamadmail.features.auth.model.Domain
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val domainsPagingSource: DomainsPagingSource,
    private val userSessionManager: UserSessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState.initial)
    val state = _state.asStateFlow()

    val domains = Pager(PagingConfig(10)) {
        domainsPagingSource
    }.flow

    fun createAccount(address: String, password: String) {

        _state.update { it.copy(authenticating = true) }

        viewModelScope.launch {
            repository.createAccount(address = address, password = password)
                .onSuccess {

                    Timber.d("account created successfully")

                    login(address = address, password = password)
                }
                .onError {
                    _state.update {
                        it.copy(
                            authenticating = false,
                            apiErrorResponse = errorBody?.parsedError()
                        )
                    }
                }
                .onException {
                    _state.update {
                        it.copy(
                            authenticating = false,
                            apiErrorResponse = exception.parsedError()
                        )
                    }
                }
        }
    }

    fun login(address: String, password: String) {

        Timber.d("logging in...")

        _state.update { it.copy(authenticating = true) }

        viewModelScope.launch {

            repository.getToken(address = address, password = password)
                .suspendOnSuccess {
                    val userCredentials = UserCredentials(data.token)
                    userSessionManager.setUserCredentials(userCredentials)

                    _state.update { it.copy(authenticating = false) }
                }
                .onError {
                    _state.update {
                        it.copy(
                            authenticating = false,
                            apiErrorResponse = errorBody?.parsedError()
                        )
                    }
                }
                .onException {
                    _state.update {
                        it.copy(
                            authenticating = false,
                            apiErrorResponse = exception.parsedError()
                        )
                    }
                }
        }
    }

    fun switchAuthenticationMode() {
        _state.update {
            it.copy(
                screenMode = when (it.screenMode) {
                    is AuthScreenMode.CreateAccount -> AuthScreenMode.Login
                    is AuthScreenMode.Login -> AuthScreenMode.CreateAccount
                }
            )
        }
    }

    fun changeSelectedDomain(domain: Domain?) {
        _state.update { it.copy(selectedDomain = domain) }
    }

    fun dismissError() {
        _state.update { it.copy(apiErrorResponse = null) }
    }
}