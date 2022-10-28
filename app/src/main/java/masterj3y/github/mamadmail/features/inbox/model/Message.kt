package masterj3y.github.mamadmail.features.inbox.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Keep
@Immutable
@Entity(
    tableName = "message",
    indices = [
        Index(value = ["id"], unique = true)
    ]
)
data class Message(
    @PrimaryKey(autoGenerate = true)
    val index: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("accountId")
    val accountId: String,
    @SerializedName("msgid")
    val msgid: String,
    @SerializedName("from")
    val from: MessageSender,
    @SerializedName("to")
    val to: List<MessageReceiver>,
    @SerializedName("subject")
    val subject: String,
    @SerializedName("intro")
    val intro: String?,
    @SerializedName("seen")
    val seen: Boolean,
    @SerializedName("isDeleted")
    val isDeleted: Boolean,
    @SerializedName("hasAttachments")
    val hasAttachments: Boolean,
    @SerializedName("size")
    val size: Int,
    @SerializedName("downloadUrl")
    val downloadUrl: String,
    @SerializedName("createdAt")
    val createdAt: Date,
    @SerializedName("updatedAt")
    val updatedAt: Date
)