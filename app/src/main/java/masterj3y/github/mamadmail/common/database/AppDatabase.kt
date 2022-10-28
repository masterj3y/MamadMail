package masterj3y.github.mamadmail.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import masterj3y.github.mamadmail.common.converter.DateConverter
import masterj3y.github.mamadmail.common.converter.MessageReceiverConverter
import masterj3y.github.mamadmail.common.converter.MessageSenderConverter
import masterj3y.github.mamadmail.common.database.dao.MessageDao
import masterj3y.github.mamadmail.features.inbox.model.Message

@Database(
    entities = [Message::class],
    version = 1
)
@TypeConverters(
    DateConverter::class,
    MessageSenderConverter::class,
    MessageReceiverConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao
}