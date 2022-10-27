package masterj3y.github.mamadmail.features.auth.data

import com.skydoves.sandwich.ApiResponse
import masterj3y.github.mamadmail.features.auth.model.CreateAccountResponse
import masterj3y.github.mamadmail.features.auth.model.GetTokenResponse

interface AuthRepository {

    suspend fun createAccount(address: String, password: String): ApiResponse<CreateAccountResponse>

    suspend fun getToken(address: String, password: String): ApiResponse<GetTokenResponse>
}