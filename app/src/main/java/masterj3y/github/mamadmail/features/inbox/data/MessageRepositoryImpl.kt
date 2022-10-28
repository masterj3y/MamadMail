package masterj3y.github.mamadmail.features.inbox.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.Flow
import masterj3y.github.mamadmail.common.database.AppDatabase
import masterj3y.github.mamadmail.common.database.dao.MessageDao
import masterj3y.github.mamadmail.features.inbox.model.Message
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val service: MessageService,
    private val database: AppDatabase
) : MessageRepository {

    private val messageDao: MessageDao
        get() = database.messageDao()

    @OptIn(ExperimentalPagingApi::class)
    private val messagePaging = Pager(
        config = PagingConfig(10),
        remoteMediator = MessageRemoteMediator(database, service)
    ) {
        database.messageDao().pagingSource()
    }.flow

    override val messages: Flow<PagingData<Message>> = messagePaging

    override suspend fun markMessageAsSeen(messageId: String) = messageDao.markAsSeen(messageId)

    override suspend fun deleteMessage(messageId: String): ApiResponse<Any> {
        return service.deleteMessage(messageId)
            .suspendOnSuccess {
                messageDao.deleteById(messageId)
            }
    }
}