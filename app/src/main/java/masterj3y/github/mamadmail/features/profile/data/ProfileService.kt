package masterj3y.github.mamadmail.features.profile.data

import com.skydoves.sandwich.ApiResponse
import masterj3y.github.mamadmail.features.profile.model.Profile
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService {

    @GET("me")
    suspend fun fetchProfile(): ApiResponse<Profile>

    @DELETE("accounts/{id}")
    suspend fun deleteAccount(@Path("id") accountId: String): ApiResponse<Any>
}