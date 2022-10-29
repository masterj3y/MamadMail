package masterj3y.github.mamadmail.features.message.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MessageAttachment(
    @SerializedName("id")
    val id: String,
    @SerializedName("filename")
    val filename: String,
    @SerializedName("contentType")
    val contentType: String,
    @SerializedName("disposition")
    val disposition: String,
    @SerializedName("transferEncoding")
    val transferEncoding: String,
    @SerializedName("related")
    val related: Boolean,
    @SerializedName("size")
    val size: Int,
    @SerializedName("downloadUrl")
    val downloadUrl: String
)