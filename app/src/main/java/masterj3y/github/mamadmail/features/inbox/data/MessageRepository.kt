package masterj3y.github.mamadmail.features.inbox.data

import androidx.paging.PagingData
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.flow.Flow
import masterj3y.github.mamadmail.features.inbox.model.Message

interface MessageRepository {

    val messages: Flow<PagingData<Message>>

    suspend fun markMessageAsSeen(messageId: String)

    suspend fun deleteMessage(messageId: String): ApiResponse<Any>
}