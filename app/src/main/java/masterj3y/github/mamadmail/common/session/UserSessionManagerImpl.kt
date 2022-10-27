package masterj3y.github.mamadmail.common.session

import android.content.Context
import android.os.Build
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import masterj3y.github.mamadmail.common.cryptography.CryptographyManager
import masterj3y.github.mamadmail.common.cryptography.CryptographyManagerImpl
import masterj3y.github.mamadmail.common.preferences.dataStore
import masterj3y.github.mamadmail.common.preferences.readString
import masterj3y.github.mamadmail.common.preferences.writeString
import timber.log.Timber
import javax.inject.Inject

class UserSessionManagerImpl @Inject constructor(
    @ApplicationContext context: Context
) : UserSessionManager {

    private val dataStore = context.dataStore

    private var cryptographyManager: CryptographyManager? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cryptographyManager = CryptographyManagerImpl()
        }
    }

    override val userCredentials: Flow<UserCredentials?> =
        dataStore.readString(USER_ACCESS_TOKEN)
            .map { accessToken ->
                cryptographyManager?.decrypt(USER_ACCESS_TOKEN_ALIAS) ?: accessToken
            }
            .map { accessToken ->
                Timber.tag(TAG).d("decrypted user access token: $accessToken")
                accessToken?.let { UserCredentials(it) }
            }

    override suspend fun setUserCredentials(credentials: UserCredentials) {
        Timber.tag(TAG).d("saving user credentials...")
        withContext(Dispatchers.IO) {
            val encryptedAccessToken =
                cryptographyManager?.encrypt(USER_ACCESS_TOKEN_ALIAS, credentials.accessToken)
                    ?: credentials.accessToken

            dataStore.writeString(USER_ACCESS_TOKEN, encryptedAccessToken)

            Timber.tag(TAG).d("encrypted user access token: $encryptedAccessToken")
        }
    }

    override val isUserAuthenticated: Flow<Boolean?> =
        userCredentials
            .map { it != null }
            .onEach { Timber.tag(TAG).d("isUserAuthenticated: $it") }

    override suspend fun logout() {
        withContext(Dispatchers.IO) {
            Timber.tag(TAG).d("clearing data store...")
            dataStore.edit { it.clear() }
        }
    }

    companion object {
        private const val TAG = "user-session-manager"
        private const val USER_ACCESS_TOKEN = "user-access-token"
        private const val USER_ACCESS_TOKEN_ALIAS = "user-access-token-alias"
    }
}