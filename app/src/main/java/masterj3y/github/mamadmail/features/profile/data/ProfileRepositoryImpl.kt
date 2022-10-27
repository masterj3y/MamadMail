package masterj3y.github.mamadmail.features.profile.data

import com.skydoves.sandwich.ApiResponse
import masterj3y.github.mamadmail.features.profile.model.Profile
import javax.inject.Inject

class ProfileRepositoryImpl
@Inject constructor(private val service: ProfileService) : ProfileRepository {

    override suspend fun fetchProfile(): ApiResponse<Profile> = service.fetchProfile()

    override suspend fun deleteAccount(accountId: String): ApiResponse<Any> =
        service.deleteAccount(accountId)
}