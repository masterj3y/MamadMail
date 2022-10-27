package masterj3y.github.mamadmail.features.auth.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName

@Keep
@Immutable
data class Domain(
    @SerializedName("id")
    val id: String,
    @SerializedName("domain")
    val domain: String,
    @SerializedName("isActive")
    val isActive: Boolean,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)