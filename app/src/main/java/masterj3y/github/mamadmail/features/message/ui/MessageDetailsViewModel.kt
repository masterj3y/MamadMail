package masterj3y.github.mamadmail.features.message.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import masterj3y.github.mamadmail.common.downloader.AndroidDownloader
import masterj3y.github.mamadmail.common.downloader.Downloader
import masterj3y.github.mamadmail.common.downloader.saver.AndroidFileSaver
import masterj3y.github.mamadmail.common.extensions.ApiErrorResponse
import masterj3y.github.mamadmail.common.extensions.parsedError
import masterj3y.github.mamadmail.common.session.UserSessionManager
import masterj3y.github.mamadmail.di.NetworkModule
import masterj3y.github.mamadmail.features.inbox.data.MessageRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MessageDetailsViewModel @Inject constructor(
    @ApplicationContext context: Context,
    userSessionManager: UserSessionManager,
    private val repository: MessageRepository
) : ViewModel() {

    private var downloader: Downloader? = null

    private val _state = MutableStateFlow(MessageDetailsUiState.initial)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<MessageDetailsUiEffect>()
    val effect = _effect.asSharedFlow()

    private var messageId: String? = null
    private val isAlreadyLoaded: Boolean
        get() = messageId != null

    init {
        viewModelScope.launch {
            val userCredentials = userSessionManager.userCredentials.firstOrNull()
            if (userCredentials != null)
                downloader = AndroidDownloader(
                    cachePath = context.cacheDir.path + "/attachments",
                    fileSaver = AndroidFileSaver(context.contentResolver),
                    userCredentials = userCredentials
                )
        }
    }

    fun loadMessageDetails(messageId: String) {
        if (isAlreadyLoaded) return

        this.messageId = messageId

        _state.update {
            it.copy(loading = true)
        }

        viewModelScope.launch {
            repository.fetchMessageDetail(messageId).onSuccess {
                _state.update {
                    it.copy(loading = false, message = data)
                }
            }.onError {
                _state.update {
                    it.copy(loading = false, apiErrorResponse = errorBody?.parsedError())
                }
            }.onException {
                _state.update {
                    it.copy(loading = false, apiErrorResponse = exception.parsedError())
                }
            }
        }
    }

    fun deleteMessage() {
        if (messageId == null) return

        _state.update {
            it.copy(deletingMessage = true)
        }

        viewModelScope.launch {
            repository.deleteMessage(messageId!!).onSuccess {
                _state.update { it.copy(deletingMessage = false) }
                emitEffect(MessageDetailsUiEffect.MessageDeleted)
            }.onError {
                _state.update {
                    it.copy(
                        deletingMessage = false, apiErrorResponse = errorBody?.parsedError()
                    )
                }
            }.onException {
                _state.update {
                    it.copy(deletingMessage = false, apiErrorResponse = exception.parsedError())
                }
            }
        }
    }

    fun downloadAttachments() {

        if (state.value.message == null || state.value.downloadingAttachments || downloader == null) {
            _state.update {
                it.copy(
                    apiErrorResponse = ApiErrorResponse(
                        title = "Error",
                        description = "Initializing Downloader failed"
                    )
                )
            }
            return
        }

        _state.update {
            it.copy(downloadingAttachments = true)
        }

        viewModelScope.launch {
            state.value.message!!.attachments.forEach { attachment ->
                try {
                    downloader!!.download(
                        NetworkModule.BASE_URL + attachment.downloadUrl.replaceFirst(
                            "/",
                            ""
                        )
                    ).collect()
                } catch (e: Exception) {
                    Timber.tag("download-attachments").d(e)
                }
            }
            _state.update {
                it.copy(downloadingAttachments = false)
            }
            emitEffect(MessageDetailsUiEffect.AttachmentsDownloaded())
        }

    }

    fun dismissError() {
        _state.update { it.copy(apiErrorResponse = null) }
    }

    private fun emitEffect(effect: MessageDetailsUiEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}