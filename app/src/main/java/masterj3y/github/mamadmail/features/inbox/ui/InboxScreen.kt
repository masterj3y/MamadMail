package masterj3y.github.mamadmail.features.inbox.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwipeDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import masterj3y.github.mamadmail.features.inbox.model.Message

@Composable
fun InboxScreen(viewModel: InboxViewModel = viewModel()) {

    val messages = viewModel.messages.collectAsLazyPagingItems()

    val isRefreshing = remember {
        derivedStateOf { !messages.loadState.prepend.endOfPaginationReached }
    }

    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value),
        onRefresh = messages::refresh
    ) {

        Messages(
            messages = messages,
            isRefreshing = isRefreshing.value,
            onMessageClick = { },
            onMarkAsSeen = { viewModel.markAsSeen(it.id) },
            onDeleteMessage = { viewModel.delete(it.id) }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Messages(
    messages: LazyPagingItems<Message>,
    isRefreshing: Boolean,
    onMessageClick: (Message) -> Unit,
    onMarkAsSeen: (Message) -> Unit,
    onDeleteMessage: (Message) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {

        if (messages.itemCount > 0)
            items(items = messages, key = { it.id }) { message ->
                if (message != null)
                    MessageRow(
                        modifier = Modifier.animateItemPlacement(),
                        message = message,
                        onClick = onMessageClick,
                        onLongClick = onMarkAsSeen,
                        onSwipe = onDeleteMessage,
                    )
            }

        if (messages.itemCount == 0 && !isRefreshing)
            item {
                EmptyInbox()
            }
    }
}

@Composable
private fun EmptyInbox() {
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(50.dp),
                imageVector = Icons.Filled.SwipeDown,
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "No messages found\nSwipe down to refresh", textAlign = TextAlign.Center)
        }
    }
}