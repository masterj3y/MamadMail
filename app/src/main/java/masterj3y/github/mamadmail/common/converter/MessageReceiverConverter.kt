package masterj3y.github.mamadmail.common.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import masterj3y.github.mamadmail.features.inbox.model.MessageReceiver

class MessageReceiverConverter {

    @TypeConverter
    fun fromMessageSender(receiver: List<MessageReceiver>): String = Gson().toJson(receiver)

    @TypeConverter
    fun toMessageSender(json: String): List<MessageReceiver> =
        Gson().fromJson(
            json,
            object : TypeToken<List<MessageReceiver>>() {}.type
        )

}