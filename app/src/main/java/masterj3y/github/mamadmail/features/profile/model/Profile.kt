package masterj3y.github.mamadmail.features.profile.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Profile(
    @SerializedName("id")
    val id: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("quota")
    val quota: Int,
    @SerializedName("used")
    val used: Int,
    @SerializedName("isDisabled")
    val isDisabled: Boolean,
    @SerializedName("isDeleted")
    val isDeleted: Boolean,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)