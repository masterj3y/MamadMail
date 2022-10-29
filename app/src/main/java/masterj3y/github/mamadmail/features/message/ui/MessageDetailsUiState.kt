package masterj3y.github.mamadmail.features.message.ui

import androidx.compose.runtime.Immutable
import masterj3y.github.mamadmail.common.extensions.ApiErrorResponse
import masterj3y.github.mamadmail.features.message.model.MessageDetails

@Immutable
data class MessageDetailsUiState(
    val loading: Boolean,
    val message: MessageDetails?,
    val deletingMessage: Boolean,
    val downloadingAttachments: Boolean,
    val apiErrorResponse: ApiErrorResponse?
) {

    companion object {
        val initial: MessageDetailsUiState
            get() = MessageDetailsUiState(
                loading = false,
                message = null,
                deletingMessage = false,
                downloadingAttachments = false,
                apiErrorResponse = null
            )
    }
}