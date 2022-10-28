package masterj3y.github.mamadmail.features.inbox.data

import com.skydoves.sandwich.ApiResponse
import masterj3y.github.mamadmail.common.model.PagingResponse
import masterj3y.github.mamadmail.features.inbox.model.Message
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MessageService {

    @GET("messages")
    suspend fun fetchMessages(@Query("page") page: Int): ApiResponse<PagingResponse<Message>>

    @DELETE("messages/{id}")
    suspend fun deleteMessage(@Path("id") id: String): ApiResponse<Any>
}