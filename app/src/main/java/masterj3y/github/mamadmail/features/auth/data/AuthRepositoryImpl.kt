package masterj3y.github.mamadmail.features.auth.data

import com.skydoves.sandwich.ApiResponse
import masterj3y.github.mamadmail.features.auth.model.CreateAccountRequest
import masterj3y.github.mamadmail.features.auth.model.CreateAccountResponse
import masterj3y.github.mamadmail.features.auth.model.GetTokenRequest
import masterj3y.github.mamadmail.features.auth.model.GetTokenResponse
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val service: AuthService
) : AuthRepository {

    override suspend fun createAccount(
        address: String,
        password: String
    ): ApiResponse<CreateAccountResponse> =
        service.createAccount(
            request = CreateAccountRequest(
                address = address,
                password = password
            )
        )

    override suspend fun getToken(
        address: String,
        password: String
    ): ApiResponse<GetTokenResponse> =
        service.getToken(
            request = GetTokenRequest(
                address = address,
                password = password
            )
        )
}