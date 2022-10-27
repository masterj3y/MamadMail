package masterj3y.github.mamadmail.features.auth.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetTokenResponse(
    @SerializedName("token")
    val token: String
)