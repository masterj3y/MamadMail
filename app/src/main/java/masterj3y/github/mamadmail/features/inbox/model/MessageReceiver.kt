package masterj3y.github.mamadmail.features.inbox.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MessageReceiver(
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: String
)