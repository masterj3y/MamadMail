package masterj3y.github.mamadmail.features.auth.data

import com.skydoves.sandwich.ApiResponse
import masterj3y.github.mamadmail.features.auth.model.CreateAccountRequest
import masterj3y.github.mamadmail.features.auth.model.CreateAccountResponse
import masterj3y.github.mamadmail.features.auth.model.GetTokenRequest
import masterj3y.github.mamadmail.features.auth.model.GetTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("accounts")
    suspend fun createAccount(@Body request: CreateAccountRequest): ApiResponse<CreateAccountResponse>

    @POST("token")
    suspend fun getToken(@Body request: GetTokenRequest): ApiResponse<GetTokenResponse>
}