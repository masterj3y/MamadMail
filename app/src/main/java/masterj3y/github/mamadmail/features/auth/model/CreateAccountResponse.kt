package masterj3y.github.mamadmail.features.auth.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.util.*

@Keep
data class CreateAccountResponse(
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
    val createdAt: Date,
    @SerializedName("retentionAt")
    val retentionAt: Date,
    @SerializedName("updatedAt")
    val updatedAt: Date
)