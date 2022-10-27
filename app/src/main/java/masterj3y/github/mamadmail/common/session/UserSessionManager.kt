package masterj3y.github.mamadmail.common.session

import kotlinx.coroutines.flow.Flow

interface UserSessionManager {

    val userCredentials: Flow<UserCredentials?>

    suspend fun setUserCredentials(credentials: UserCredentials)

    val isUserAuthenticated: Flow<Boolean?>

    suspend fun logout()
}