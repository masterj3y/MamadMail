package masterj3y.github.mamadmail.features.inbox.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.skydoves.sandwich.getOrNull
import masterj3y.github.mamadmail.common.database.AppDatabase
import masterj3y.github.mamadmail.features.inbox.model.Message
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MessageRemoteMediator(
    private val database: AppDatabase,
    private val messageService: MessageService
) : RemoteMediator<Int, Message>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Message>
    ): MediatorResult {

        val messageDao = database.messageDao()

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {

                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )

                    lastItem.index
                }
            }

            val response = messageService.fetchMessages(
                page = loadKey ?: 1
            ).getOrNull()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    messageDao.clearAll()
                }
                messageDao.insertAll(response?.items ?: listOf())
            }

            MediatorResult.Success(
                endOfPaginationReached = response?.items?.isEmpty() == true
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}