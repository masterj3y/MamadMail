package masterj3y.github.mamadmail.common.extensions

import androidx.annotation.Keep
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody

@Keep
data class ApiErrorResponse(
    @SerializedName("hydra:title")
    val title: String,
    @SerializedName("hydra:description")
    val description: String
)

fun ResponseBody.parsedError(): ApiErrorResponse? {

    val jsonObject = Gson().fromJson(string(), JsonObject::class.java)

    return when {
        jsonObject.has("code").and(jsonObject.has("message")) ->
            ApiErrorResponse(
                title = "Error",
                description = jsonObject.get("message").asString
            )
        jsonObject.has("hydra:title").and(jsonObject.has("hydra:description")) ->
            ApiErrorResponse(
                title = jsonObject.get("hydra:title").asString,
                description = jsonObject.get("hydra:description").asString
            )
        else -> null
    }
}

fun Throwable.parsedError(): ApiErrorResponse =
    ApiErrorResponse(title = "Exception", description = message ?: "Unknown")