package masterj3y.github.mamadmail.features.inbox.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import masterj3y.github.mamadmail.features.inbox.data.MessageRepository
import masterj3y.github.mamadmail.features.inbox.model.Message
import javax.inject.Inject

@HiltViewModel
class InboxViewModel @Inject constructor(
    private val repository: MessageRepository
) : ViewModel() {

    val messages: Flow<PagingData<Message>> = repository.messages

    fun markAsSeen(messageId: String) {
        viewModelScope.launch {
            repository.markMessageAsSeen(messageId)
        }
    }

    fun delete(messageId: String) {
        viewModelScope.launch {
            repository.deleteMessage(messageId)
        }
    }
}