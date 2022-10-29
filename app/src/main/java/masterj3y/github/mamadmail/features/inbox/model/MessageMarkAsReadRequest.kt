package masterj3y.github.mamadmail.features.inbox.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MessageMarkAsReadRequest(
    @SerializedName("seen")
    val seen: Boolean = true
)