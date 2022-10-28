package masterj3y.github.mamadmail.common.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import masterj3y.github.mamadmail.features.inbox.model.Message

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<Message>)

    @Query("SELECT * FROM message")
    fun pagingSource(): PagingSource<Int, Message>

    @Query("DELETE FROM message")
    suspend fun clearAll()

    @Query("UPDATE message SET seen = 1 WHERE id = :id")
    suspend fun markAsSeen(id: String)

    @Query("DELETE FROM message WHERE id = :id")
    suspend fun deleteById(id: String)
}