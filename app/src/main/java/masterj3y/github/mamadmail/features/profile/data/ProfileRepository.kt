package masterj3y.github.mamadmail.features.profile.data

import com.skydoves.sandwich.ApiResponse
import masterj3y.github.mamadmail.features.profile.model.Profile

interface ProfileRepository {

    suspend fun fetchProfile(): ApiResponse<Profile>

    suspend fun deleteAccount(accountId: String): ApiResponse<Any>
}