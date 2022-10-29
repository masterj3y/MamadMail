package masterj3y.github.mamadmail.features.inbox.data

import com.skydoves.sandwich.ApiResponse
import masterj3y.github.mamadmail.common.model.PagingResponse
import masterj3y.github.mamadmail.features.inbox.model.Message
import masterj3y.github.mamadmail.features.inbox.model.MessageMarkAsReadRequest
import masterj3y.github.mamadmail.features.message.model.MessageDetails
import retrofit2.http.*

interface MessageService {

    @GET("messages")
    suspend fun fetchMessages(@Query("page") page: Int): ApiResponse<PagingResponse<Message>>

    @DELETE("messages/{id}")
    suspend fun deleteMessage(@Path("id") id: String): ApiResponse<Any>

    @GET("messages/{id}")
    suspend fun fetchMessageAttachment(@Path("id") id: String): ApiResponse<MessageDetails>

    @Headers("Content-Type: application/merge-patch+json")
    @PATCH("messages/{id}")
    suspend fun markMessageAsRead(
        @Path("id") id: String,
        @Body requestBody: MessageMarkAsReadRequest
    ): ApiResponse<Any>
}