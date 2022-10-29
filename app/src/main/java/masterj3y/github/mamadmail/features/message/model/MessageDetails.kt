package masterj3y.github.mamadmail.features.message.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import masterj3y.github.mamadmail.features.inbox.model.MessageReceiver
import masterj3y.github.mamadmail.features.inbox.model.MessageSender
import java.util.*

@Keep
data class MessageDetails(
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
    @SerializedName("cc")
    val cc: List<String>,
    @SerializedName("bcc")
    val bcc: List<String>,
    @SerializedName("subject")
    val subject: String,
    @SerializedName("seen")
    val seen: Boolean,
    @SerializedName("flagged")
    val flagged: Boolean,
    @SerializedName("isDeleted")
    val isDeleted: Boolean,
    @SerializedName("verifications")
    val verifications: List<String>,
    @SerializedName("retention")
    val retention: Boolean,
    @SerializedName("retentionDate")
    val retentionDate: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("html")
    val html: List<String>,
    @SerializedName("hasAttachments")
    val hasAttachments: Boolean,
    @SerializedName("attachments")
    val attachments: List<MessageAttachment>,
    @SerializedName("size")
    val size: Int,
    @SerializedName("downloadUrl")
    val downloadUrl: String,
    @SerializedName("createdAt")
    val createdAt: Date,
    @SerializedName("updatedAt")
    val updatedAt: Date
)