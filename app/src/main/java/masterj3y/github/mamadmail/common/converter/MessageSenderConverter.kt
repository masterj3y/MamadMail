package masterj3y.github.mamadmail.common.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import masterj3y.github.mamadmail.features.inbox.model.MessageSender

class MessageSenderConverter {

    @TypeConverter
    fun fromMessageSender(sender: MessageSender): String = Gson().toJson(sender)

    @TypeConverter
    fun toMessageSender(json: String): MessageSender =
        Gson().fromJson(json, MessageSender::class.java)
}