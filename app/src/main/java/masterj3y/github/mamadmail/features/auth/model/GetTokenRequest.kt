package masterj3y.github.mamadmail.features.auth.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetTokenRequest(
    @SerializedName("address")
    val address: String,
    @SerializedName("password")
    val password: String
)