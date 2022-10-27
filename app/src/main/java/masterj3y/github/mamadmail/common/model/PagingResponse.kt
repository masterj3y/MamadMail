package masterj3y.github.mamadmail.common.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PagingResponse<T>(
    @SerializedName("hydra:member")
    val items: List<T>,
    @SerializedName("hydra:totalItems")
    val itemsCount: Int
)