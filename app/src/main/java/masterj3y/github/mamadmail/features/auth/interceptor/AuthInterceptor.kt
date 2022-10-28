package masterj3y.github.mamadmail.features.auth.interceptor

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import masterj3y.github.mamadmail.common.session.UserSessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val userSessionManager: UserSessionManager) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {

        val isUserAuthenticated = userSessionManager.isUserAuthenticated.firstOrNull() ?: false
        val userCredentials = userSessionManager.userCredentials.firstOrNull()

        var request = chain.request()

        if (isUserAuthenticated && userCredentials != null) {
            request = request.newBuilder()
                .header(
                    AUTHORIZATION_HEADER,
                    "Bearer ${userCredentials.accessToken}"
                )
                .build()

            val response = chain.proceed(request)

            if (response.code == 401)
                userSessionManager.logout()

            response
        } else
            chain.proceed(request)
    }

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
    }
}